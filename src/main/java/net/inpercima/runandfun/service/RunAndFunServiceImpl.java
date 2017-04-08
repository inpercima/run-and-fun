package net.inpercima.runandfun.service;

import static net.inpercima.runandfun.runkeeper.constants.RunkeeperConstants.ACTIVITIES_MEDIA;
import static net.inpercima.runandfun.runkeeper.constants.RunkeeperConstants.ACTIVITIES_URL_PAGE_SIZE_ONE;
import static net.inpercima.runandfun.runkeeper.constants.RunkeeperConstants.ACTIVITIES_URL_SPECIFIED;
import static net.inpercima.runandfun.runkeeper.constants.RunkeeperConstants.DE_AUTHORIZATION_URL;
import static net.inpercima.runandfun.runkeeper.constants.RunkeeperConstants.FRIENDS_MEDIA;
import static net.inpercima.runandfun.runkeeper.constants.RunkeeperConstants.FRIENDS_URL_PAGE_SIZE_ONE;
import static net.inpercima.runandfun.runkeeper.constants.RunkeeperConstants.FRIENDS_URL_SPECIFIED_PAGE_SIZE;
import static net.inpercima.runandfun.runkeeper.constants.RunkeeperConstants.PROFILE_MEDIA;
import static net.inpercima.runandfun.runkeeper.constants.RunkeeperConstants.PROFILE_URL;
import static net.inpercima.runandfun.runkeeper.constants.RunkeeperConstants.TOKEN_URL;
import static net.inpercima.runandfun.runkeeper.constants.RunkeeperConstants.USER_MEDIA;
import static net.inpercima.runandfun.runkeeper.constants.RunkeeperConstants.USER_URL;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;

import net.inpercima.restapi.service.RestApiService;
import net.inpercima.runandfun.app.constants.AppConstants;
import net.inpercima.runandfun.app.model.AppActivity;
import net.inpercima.runandfun.app.model.AppState;
import net.inpercima.runandfun.runkeeper.model.RunkeeperActivities;
import net.inpercima.runandfun.runkeeper.model.RunkeeperActivityItem;
import net.inpercima.runandfun.runkeeper.model.RunkeeperFriendItem;
import net.inpercima.runandfun.runkeeper.model.RunkeeperFriends;
import net.inpercima.runandfun.runkeeper.model.RunkeeperProfile;
import net.inpercima.runandfun.runkeeper.model.RunkeeperToken;
import net.inpercima.runandfun.runkeeper.model.RunkeeperUser;

/**
 * @author Marcel Jänicke
 * @author Sebastian Peters
 * @since 26.01.2015
 */
@Service
public class RunAndFunServiceImpl implements RunAndFunService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RunAndFunServiceImpl.class);

    // initial release in 2008 according to http://en.wikipedia.org/wiki/RunKeeper
    private static final LocalDate INITIAL_RELEASE_OF_RUNKEEPER = LocalDate.of(2008, 01, 01);

    @Autowired
    private RestApiService restApiService;

    @Autowired
    private ActivityRepository repository;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    /**
     * Default constructor
     */
    public RunAndFunServiceImpl() {
    }

    /**
     * Constructor for unit testing
     *
     * @param helperService
     */
    public RunAndFunServiceImpl(final RestApiService restApiService) {
        this.restApiService = restApiService;
    }

    @Override
    public String getAccessToken(final String code) {
        return restApiService.postForObject(TOKEN_URL, code, RunkeeperToken.class).getAccessToken();
    }

    @Override
    public RunkeeperUser getUser(final String accessToken) {
        return restApiService.getForObject(USER_URL, USER_MEDIA, accessToken, RunkeeperUser.class).getBody();
    }

    @Override
    public RunkeeperProfile getProfile(final String accessToken) {
        LOGGER.debug("get profile for token {}", accessToken);
        return restApiService.getForObject(PROFILE_URL, PROFILE_MEDIA, accessToken, RunkeeperProfile.class).getBody();
    }

    private RunkeeperActivities getActivities(final String accessToken, final LocalDate from) {
        LOGGER.debug("getActivities until {}", from);

        int pageSize = AppConstants.DEFAULT_PAGE_SIZE;
        if (from.until(LocalDate.now(), ChronoUnit.DAYS) > pageSize) {
            // get one item only to get full size
            final HttpEntity<RunkeeperActivities> activitiesForSize = restApiService.getForObject(
                    ACTIVITIES_URL_PAGE_SIZE_ONE, ACTIVITIES_MEDIA, accessToken, RunkeeperActivities.class);
            pageSize = activitiesForSize.getBody().getSize();
        }

        // get new activities
        return restApiService.getForObject(
                String.format(ACTIVITIES_URL_SPECIFIED, pageSize, from.format(DateTimeFormatter.ISO_LOCAL_DATE)),
                ACTIVITIES_MEDIA, accessToken, RunkeeperActivities.class).getBody();
    }

    @Override
    public List<RunkeeperFriendItem> getFriends(String accessToken) {
        LOGGER.debug("get friends for token {}", accessToken);
        final HttpEntity<RunkeeperFriends> friendsForSize = restApiService.getForObject(FRIENDS_URL_PAGE_SIZE_ONE,
                FRIENDS_MEDIA, accessToken, RunkeeperFriends.class);
        final int pageSize = friendsForSize.getBody().getSize();

        return restApiService.getForObject(String.format(FRIENDS_URL_SPECIFIED_PAGE_SIZE, pageSize), FRIENDS_MEDIA,
                accessToken, RunkeeperFriends.class).getBody().getItemsAsList();
    }

    @Override
    public int indexActivities(final String accessToken) {
        final Collection<AppActivity> activities = new ArrayList<>();
        final String username = getAppState(accessToken).getUsername();
        getActivities(accessToken, calculateFetchDate()).getItemsAsList().stream()
                .filter(item -> !repository.exists(item.getId()))
                .forEach(item -> addActivity(item, username, activities));
        LOGGER.info("new activities: {}", activities.size());
        if (!activities.isEmpty()) {
            repository.save(activities);
        }
        return activities.size();
    }

    private void addActivity(final RunkeeperActivityItem item, final String username,
            final Collection<AppActivity> activities) {
        final AppActivity activity = new AppActivity(item.getId(), username, item.getType(), item.getDate(),
                item.getDistance(), item.getFormattedDuration());
        LOGGER.debug("prepare {}", activity);
        activities.add(activity);
    }

    private LocalDate calculateFetchDate() {
        final Pageable pageable = new PageRequest(0, 1, Direction.DESC, AppActivity.FIELD_DATE);
        final Iterator<AppActivity> lastFetchedActivity = repository.findAll(pageable).getContent().iterator();
        return lastFetchedActivity.hasNext()
                ? lastFetchedActivity.next().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                : INITIAL_RELEASE_OF_RUNKEEPER;
    }

    @Override
    public Page<AppActivity> listAllActivitiesByType(String type) {
        final int count = type != null ? repository.countByType(type) : (int) repository.count();
        final Pageable pageable = new PageRequest(0, count);
        return repository.findAllByTypeOrderByDateDesc(type, pageable);
    }

    @Override
    public Page<AppActivity> listActivities(final Pageable pageable, final String types, final LocalDate minDate,
            final LocalDate maxDate, final Float minDistance, final Float maxDistance, final String query) {
        final BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        if (!Strings.isNullOrEmpty(types)) {
            final BoolQueryBuilder typesQuery = QueryBuilders.boolQuery();
            for (final String type : Splitter.on(',').split(types.toLowerCase())) {
                typesQuery.should(QueryBuilders.termQuery(AppActivity.FIELD_TYPE, type));
            }
            queryBuilder.must(typesQuery);
        }
        if (minDate != null || maxDate != null) {
            addDateQuery(queryBuilder, minDate, maxDate);
        }
        if (minDistance != null || maxDistance != null) {
            addDistanceQuery(queryBuilder, minDistance, maxDistance);
        }
        if (!Strings.isNullOrEmpty(query)) {
            addFulltextQuery(queryBuilder, query);
        }
        if (!queryBuilder.hasClauses()) {
            queryBuilder.must(QueryBuilders.matchAllQuery());
        }
        LOGGER.info("{}", queryBuilder);
        return elasticsearchTemplate.queryForPage(
                new NativeSearchQueryBuilder().withPageable(pageable).withQuery(queryBuilder).build(),
                AppActivity.class);
    }

    private static void addFulltextQuery(final BoolQueryBuilder queryBuilder, final String query) {
        queryBuilder.must(QueryBuilders.termQuery("_all", query.trim()));
    }

    private static void addDateQuery(final BoolQueryBuilder queryBuilder, final LocalDate minDate,
            final LocalDate maxDate) {
        final RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery(AppActivity.FIELD_DATE);
        if (minDate != null) {
            rangeQuery.gte(minDate);
        }
        if (maxDate != null) {
            rangeQuery.lte(maxDate);
        }
        queryBuilder.must(rangeQuery);
    }

    private static void addDistanceQuery(final BoolQueryBuilder queryBuilder, final Float minDistance,
            final Float maxDistance) {
        final RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery(AppActivity.FIELD_DISTANCE);
        if (minDistance != null) {
            rangeQuery.gte(minDistance);
        }
        if (maxDistance != null) {
            rangeQuery.lte(maxDistance);
        }
        queryBuilder.must(rangeQuery);
    }

    @Override
    public AppState getAppState(final String accessToken) {
        final AppState state = new AppState();
        state.setAccessToken(accessToken);
        state.setClientId(restApiService.getClientId());
        state.setRedirectUri(restApiService.getRedirectUri());
        if (state.getAccessToken() != null) {
            final RunkeeperProfile profile = getProfile(state.getAccessToken());
            state.setFullName(profile.getName());
            state.setUsername(profile.getUsername());
        }
        return state;
    }

    @Override
    public void deAuthorize(final String accessToken) {
        restApiService.postForObject(DE_AUTHORIZATION_URL, accessToken);
    }

}
