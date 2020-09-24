package net.inpercima.runandfun.service;

import static net.inpercima.runandfun.runkeeper.constants.RunkeeperConstants.ACTIVITIES_MEDIA;
import static net.inpercima.runandfun.runkeeper.constants.RunkeeperConstants.ACTIVITIES_URL_PAGE_SIZE_ONE;
import static net.inpercima.runandfun.runkeeper.constants.RunkeeperConstants.ACTIVITIES_URL_SPECIFIED_PAGE_SIZE_NO_EARLIER_THAN;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.inpercima.restapi.service.RestApiService;
import net.inpercima.runandfun.app.model.AppActivity;
import net.inpercima.runandfun.runkeeper.model.RunkeeperActivities;
import net.inpercima.runandfun.runkeeper.model.RunkeeperActivityItem;

/**
 * @author Marcel Jänicke
 * @author Sebastian Peters
 * @since 26.01.2015
 */
@NoArgsConstructor
@Service
@Slf4j
public class ActivitiesService {

    // initial release in 2008 according to http://en.wikipedia.org/wiki/RunKeeper
    private static final LocalDate INITIAL_RELEASE_OF_RUNKEEPER = LocalDate.of(2008, 01, 01);

    @Inject
    private AuthService authService;

    @Inject
    private RestApiService restApiService;

    @Inject
    private ActivityRepository repository;

    @Inject
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    public int indexActivities(final String accessToken) {
        final Collection<AppActivity> activities = new ArrayList<>();
        final String username = authService.getAppState(accessToken).getUsername();
        listActivities(accessToken, calculateFetchDate()).stream().filter(item -> !repository.existsById(item.getId()))
                .forEach(item -> addActivity(item, username, activities));
        log.info("new activities: {}", activities.size());
        if (!activities.isEmpty()) {
            repository.saveAll(activities);
        }
        return activities.size();
    }

    /**
     * List activities live from runkeeper with an accessToken and a date. The full
     * size will be determined every time but with the given date only the last
     * items will be collected with a max. of the full size.
     *
     * @param accessToken
     * @param from
     * @return list of activity items
     */
    private List<RunkeeperActivityItem> listActivities(final String accessToken, final LocalDate from) {
        log.debug("list activities for token {} until {}", accessToken, from);

        // get one item only to get full size
        int pageSize = restApiService
                .getForObject(ACTIVITIES_URL_PAGE_SIZE_ONE, ACTIVITIES_MEDIA, accessToken, RunkeeperActivities.class)
                .getBody().getSize();

        // list new activities from given date with max. full size
        return restApiService.getForObject(
                String.format(ACTIVITIES_URL_SPECIFIED_PAGE_SIZE_NO_EARLIER_THAN, pageSize,
                        from.format(DateTimeFormatter.ISO_LOCAL_DATE)),
                ACTIVITIES_MEDIA, accessToken, RunkeeperActivities.class).getBody().getItemsAsList();
    }

    private LocalDate calculateFetchDate() {
        final AppActivity activity = getLastActivity();
        return activity == null ? INITIAL_RELEASE_OF_RUNKEEPER : activity.getDate().toLocalDate();
    }

    private void addActivity(final RunkeeperActivityItem item, final String username,
            final Collection<AppActivity> activities) {
        final AppActivity activity = new AppActivity(item.getId(), username, item.getType(), item.getDate(),
                item.getDistance(), item.getDuration());
        log.debug("prepare {}", activity);
        activities.add(activity);
    }

    /**
     * Get last activity from app repository.
     *
     * @return last activity
     */
    public AppActivity getLastActivity() {
        return repository.findTopByOrderByDateDesc();
    }

    /**
     * Count activities from app repository.
     *
     * @return count
     */
    public Long countActivities() {
        return repository.count();
    }

    /**
     * List activites from app repository.
     *
     * @param pageable
     * @param types
     * @param minDate
     * @param maxDate
     * @param minDistance
     * @param maxDistance
     * @param query
     * @return
     */
    public SearchHits<AppActivity> listActivities(final Pageable pageable, final String types, final LocalDate minDate,
            final LocalDate maxDate, final Float minDistance, final Float maxDistance, final String query) {
        final BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        if (!Strings.isNullOrEmpty(types)) {
            final BoolQueryBuilder typesQuery = QueryBuilders.boolQuery();
            for (final String type : Splitter.on(',').split(types)) {
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
        log.info("{}", queryBuilder);
        return elasticsearchRestTemplate.search(
                new NativeSearchQueryBuilder().withPageable(pageable).withQuery(queryBuilder).build(),
                AppActivity.class, IndexCoordinates.of("activity"));
    }

    private static void addFulltextQuery(final BoolQueryBuilder queryBuilder, final String query) {
        queryBuilder.must(QueryBuilders.termQuery("_all", query.trim()));
    }

    private static void addDateQuery(final BoolQueryBuilder queryBuilder, final LocalDate minDate,
            final LocalDate maxDate) {
        final RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery(AppActivity.FIELD_DATE);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'");
        if (minDate != null) {
            LocalDateTime minDateTime = minDate.atStartOfDay();
            rangeQuery.gte(minDateTime.format(formatter));
        }
        if (maxDate != null) {
            LocalDateTime maxDateTime = maxDate.atStartOfDay();
            rangeQuery.lte(maxDateTime.format(formatter));
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
}
