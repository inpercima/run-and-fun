package net.inpercima.runandfun.service;

import static net.inpercima.runandfun.constants.AppConstants.SESSION_ACCESS_TOKEN;
import static net.inpercima.runandfun.constants.RunkeeperApiConstants.ACTIVITIES_APP;
import static net.inpercima.runandfun.constants.RunkeeperApiConstants.ACTIVITIES_URL_WITH_PAGE_SIZE;
import static net.inpercima.runandfun.constants.RunkeeperApiConstants.ACTIVITIES_URL_WITH_PAGE_SIZE_ONE;
import static net.inpercima.runandfun.constants.RunkeeperApiConstants.DE_AUTHORIZATION_URL;
import static net.inpercima.runandfun.constants.RunkeeperApiConstants.PROFILE_APP;
import static net.inpercima.runandfun.constants.RunkeeperApiConstants.PROFILE_URL;
import static net.inpercima.runandfun.constants.RunkeeperApiConstants.TOKEN_URL;
import static net.inpercima.runandfun.constants.RunkeeperApiConstants.USER_APP;
import static net.inpercima.runandfun.constants.RunkeeperApiConstants.USER_URL;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpSession;

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
import org.springframework.data.domain.Pageable;
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

    @Override
    public RunkeeperActivities getActivities(final String accessToken) {
        // first get one item only to get full size
        final HttpEntity<RunkeeperActivities> activitiesForSize = helperService.getForObject(
                ACTIVITIES_URL_WITH_PAGE_SIZE_ONE, ACTIVITIES_APP, accessToken, RunkeeperActivities.class);
        // after that get all activities
        return helperService.getForObject(ACTIVITIES_URL_WITH_PAGE_SIZE + activitiesForSize.getBody().getSize(),
                ACTIVITIES_APP, accessToken, RunkeeperActivities.class).getBody();
    }

    @Override
    public void indexActivities(final Iterable<RunkeeperItem> runkeeperItems) {
        final Collection<Activity> activities = new ArrayList<>();
        for (final RunkeeperItem item : runkeeperItems) {
            final Activity activity = new Activity(item.getId(), item.getType(), item.getDate(), item.getDistance(),
                    item.getFormattedDuration());
            LOGGER.debug("prepare {}", activity);
            activities.add(activity);
        }
        if (!activities.isEmpty()) {
            repository.save(activities);
        }
    }

    @Override
    public Page<Activity> listActivities(final Pageable pageable, final String types, final LocalDate minDate,
            final LocalDate maxDate, final Float minDistance, final Float maxDistance, final String query) {
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
        return elasticsearchTemplate.queryForPage(
                new NativeSearchQueryBuilder().withPageable(pageable).withQuery(queryBuilder).build(), Activity.class);
    }

    private void addFulltextQuery(final BoolQueryBuilder queryBuilder, final String query) {
        queryBuilder.must(QueryBuilders.termQuery("_all", query.trim()));
    }

    private void addDateQuery(final BoolQueryBuilder queryBuilder, final LocalDate minDate, final LocalDate maxDate) {
        final RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery(Activity.FIELD_DATE);
        if (minDate != null) {
            rangeQuery.gte(minDate);
        }
        if (maxDate != null) {
            rangeQuery.lte(maxDate);
        }
        queryBuilder.must(rangeQuery);
    }

    private void addDistanceQuery(final BoolQueryBuilder queryBuilder, final Float minDistance, final Float maxDistance) {
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
    public AppState getAppState(final HttpSession session) {
        final AppState state = new AppState();
        state.setAccessToken((String) session.getAttribute(SESSION_ACCESS_TOKEN));
        state.setClientId(helperService.getClientId());
        state.setRedirectUri(helperService.getRedirectUri());
        if (state.getAccessToken() != null) {
            final RunkeeperProfile profile = getProfile(state.getAccessToken());
            state.setUsername(profile.getName());
        }
        return state;
    }

    @Override
    public void setAccessTokenToSession(final HttpSession session, final String accessToken) {
        session.setAttribute(SESSION_ACCESS_TOKEN, accessToken);
    }

    @Override
    public void logout(final HttpSession session) {
        session.removeAttribute(SESSION_ACCESS_TOKEN);
    }

    @Override
    public void deAuthorize(final HttpSession session) {
        helperService.postForObject(DE_AUTHORIZATION_URL, (String) session.getAttribute(SESSION_ACCESS_TOKEN));
    }

}
