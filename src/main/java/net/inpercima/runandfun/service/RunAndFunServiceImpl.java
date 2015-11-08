package net.inpercima.runandfun.service;

import static net.inpercima.runandfun.constants.RunkeeperApiConstants.ACTIVITIES_APP;
import static net.inpercima.runandfun.constants.RunkeeperApiConstants.ACTIVITIES_URL_NO_EARLIER_THAN;
import static net.inpercima.runandfun.constants.RunkeeperApiConstants.ACTIVITIES_URL_WITH_PAGE_SIZE_ONE;
import static net.inpercima.runandfun.constants.RunkeeperApiConstants.DE_AUTHORIZATION_URL;
import static net.inpercima.runandfun.constants.RunkeeperApiConstants.PROFILE_APP;
import static net.inpercima.runandfun.constants.RunkeeperApiConstants.PROFILE_URL;
import static net.inpercima.runandfun.constants.RunkeeperApiConstants.TOKEN_URL;
import static net.inpercima.runandfun.constants.RunkeeperApiConstants.USER_APP;
import static net.inpercima.runandfun.constants.RunkeeperApiConstants.USER_URL;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import net.inpercima.runandfun.constants.RunkeeperApiConstants;
import net.inpercima.runandfun.model.Activity;
import net.inpercima.runandfun.model.AppState;
import net.inpercima.runandfun.model.RunkeeperActivities;
import net.inpercima.runandfun.model.RunkeeperItem;
import net.inpercima.runandfun.model.RunkeeperProfile;
import net.inpercima.runandfun.model.RunkeeperToken;
import net.inpercima.runandfun.model.RunkeeperUser;

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
    private HelperService helperService;

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
    public RunAndFunServiceImpl(final HelperService helperService) {
        this.helperService = helperService;
    }

    @Override
    public String getAccessToken(final String code) {
        return helperService.postForObject(TOKEN_URL, code, RunkeeperToken.class).getAccessToken();
    }

    @Override
    public RunkeeperUser getUser(final String accessToken) {
        return helperService.getForObject(USER_URL, USER_APP, accessToken, RunkeeperUser.class).getBody();
    }

    @Override
    public RunkeeperProfile getProfile(final String accessToken) {
        LOGGER.debug("get profile for token {}", accessToken);
        return helperService.getForObject(PROFILE_URL, PROFILE_APP, accessToken, RunkeeperProfile.class).getBody();
    }

    private RunkeeperActivities getActivities(final String accessToken, final LocalDate from) {
        LOGGER.debug("getActivities until {}", from);

        int pageSize = RunkeeperApiConstants.DEFAULT_PAGE_SIZE;
        if (from.until(LocalDate.now(), ChronoUnit.DAYS) > pageSize) {
            // get one item only to get full size
            final HttpEntity<RunkeeperActivities> activitiesForSize = helperService.getForObject(ACTIVITIES_URL_WITH_PAGE_SIZE_ONE,
                    ACTIVITIES_APP, accessToken, RunkeeperActivities.class);
            pageSize = activitiesForSize.getBody().getSize();
        }

        // get new activities
        return helperService.getForObject(
                String.format(ACTIVITIES_URL_NO_EARLIER_THAN, from.format(DateTimeFormatter.ISO_LOCAL_DATE), pageSize), ACTIVITIES_APP,
                accessToken, RunkeeperActivities.class).getBody();
    }

    @Override
    public void indexActivities(final String accessToken) {
        final Collection<Activity> activities = new ArrayList<>();
        for (final RunkeeperItem item : getActivities(accessToken, calculateFetchDate()).getItemsAsList()) {
            final Activity activity = new Activity(item.getId(), item.getType(), item.getDate(), item.getDistance(),
                    item.getFormattedDuration());
            LOGGER.debug("prepare {}", activity);
            activities.add(activity);
        }
        LOGGER.info("new activities: {}", activities.size());
        if (!activities.isEmpty()) {
            repository.save(activities);
        }
    }

    private LocalDate calculateFetchDate() {
        final Pageable pageable = new PageRequest(0, 1, Direction.DESC, Activity.FIELD_DATE);
        final Iterator<Activity> lastFetchedActivity = repository.findAll(pageable).getContent().iterator();
        return lastFetchedActivity.hasNext() ? lastFetchedActivity.next().getDate().toInstant().atZone(ZoneId.systemDefault())
                .toLocalDate() : INITIAL_RELEASE_OF_RUNKEEPER;
    }

    @Override
    public Page<Activity> listAllActivitiesByType(String type) {
        final int count = type != null ? repository.countByType(type) : (int) repository.count();
        final Pageable pageable = new PageRequest(0, count);
        return repository.findAllByTypeOrderByDateDesc(type, pageable);
    }

    @Override
    public Page<Activity> listActivities(final Pageable pageable, final String types, final LocalDate minDate, final LocalDate maxDate,
            final Float minDistance, final Float maxDistance, final String query) {
        final BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        if (!Strings.isNullOrEmpty(types)) {
            final BoolQueryBuilder typesQuery = QueryBuilders.boolQuery();
            for (final String type : Splitter.on(',').split(types.toLowerCase())) {
                typesQuery.should(QueryBuilders.termQuery(Activity.FIELD_TYPE, type));
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
        return elasticsearchTemplate.queryForPage(new NativeSearchQueryBuilder().withPageable(pageable).withQuery(queryBuilder).build(),
                Activity.class);
    }

    private static void addFulltextQuery(final BoolQueryBuilder queryBuilder, final String query) {
        queryBuilder.must(QueryBuilders.termQuery("_all", query.trim()));
    }

    private static void addDateQuery(final BoolQueryBuilder queryBuilder, final LocalDate minDate, final LocalDate maxDate) {
        final RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery(Activity.FIELD_DATE);
        if (minDate != null) {
            rangeQuery.gte(minDate);
        }
        if (maxDate != null) {
            rangeQuery.lte(maxDate);
        }
        queryBuilder.must(rangeQuery);
    }

    private static void addDistanceQuery(final BoolQueryBuilder queryBuilder, final Float minDistance, final Float maxDistance) {
        final RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery(Activity.FIELD_DISTANCE);
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
        state.setClientId(helperService.getClientId());
        state.setRedirectUri(helperService.getRedirectUri());
        if (state.getAccessToken() != null) {
            final RunkeeperProfile profile = getProfile(state.getAccessToken());
            state.setFullName(profile.getName());
            state.setUsername(profile.getUsername());
        }
        return state;
    }

    @Override
    public void deAuthorize(final String accessToken) {
        helperService.postForObject(DE_AUTHORIZATION_URL, accessToken);
    }

}
