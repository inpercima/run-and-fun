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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import com.google.common.primitives.Doubles;

/**
 * @author Marcel Jänicke
 * @author Sebastian Peters
 * @since 26.01.2015
 */
@Service
public class RunAndFunServiceImpl implements RunAndFunService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RunAndFunServiceImpl.class);

    private static final double DISTANCE_RANGE_DELTA = 0.01;

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
        for (final RunkeeperItem runkeeperItem : runkeeperItems) {
            final Activity activity = new Activity(runkeeperItem.getId(), runkeeperItem.getDate(),
                    runkeeperItem.getDistance(), runkeeperItem.getFormattedDuration());
            LOGGER.debug("prepare {}", activity);
            activities.add(activity);
        }
        if (!activities.isEmpty()) {
            repository.save(activities);
        }
    }

    @Override
    public Page<Activity> listActivities(final String query, final Pageable pageable) {
        Page<Activity> result;
        if (!query.isEmpty()) {
            final Iterable<String> values = Splitter.on(CharMatcher.WHITESPACE).splitToList(query.toLowerCase());
            result = elasticsearchTemplate.queryForPage(new NativeSearchQueryBuilder().withPageable(pageable)
                    .withQuery(createFulltextSearchQueryBuilder(values)).build(), Activity.class);
        } else {
            result = repository.findAll(pageable);
        }
        return result;
    }

    private BoolQueryBuilder createFulltextSearchQueryBuilder(final Iterable<String> values) {
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        for (final String value : values) {
            final Double distance = Doubles.tryParse(value);
            if (distance != null) {
                queryBuilder = queryBuilder.should(QueryBuilders.rangeQuery("distance")
                        .gte(distance - DISTANCE_RANGE_DELTA).lte(distance + DISTANCE_RANGE_DELTA));
            }
            queryBuilder.should(QueryBuilders.termQuery("duration", value));
        }
        LOGGER.info("{}", queryBuilder);
        return queryBuilder;
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
        helperService.postForObject(DE_AUTHORIZATION_URL, (String) session.getAttribute(SESSION_ACCESS_TOKEN));
        session.removeAttribute(SESSION_ACCESS_TOKEN);
    }

}
