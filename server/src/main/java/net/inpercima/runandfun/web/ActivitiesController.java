package net.inpercima.runandfun.web;

import java.time.LocalDate;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import net.inpercima.runandfun.app.constants.AppConstants;
import net.inpercima.runandfun.app.model.AppActivity;
import net.inpercima.runandfun.service.ActivitiesService;

/**
 * @author Marcel Jänicke
 * @author Sebastian Peters
 * @since 02.02.2015
 */
@RestController
@Slf4j
public class ActivitiesController {

    @Inject
    private ActivitiesService activitiesService;

    @GetMapping(value = "/activities")
    public SearchHits<AppActivity> listActivities(
            @PageableDefault(direction = Direction.DESC, sort = "date") final Pageable pageable,
            @RequestParam(required = false) final String type,
            @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE) final LocalDate minDate,
            @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE) final LocalDate maxDate,
            @RequestParam(required = false) final Float minDistance,
            @RequestParam(required = false) final Float maxDistance,
            @RequestParam(required = false) final String query) {
        log.debug("listActivities of type '{}' for date between {} - {}, distance between {} - {}, {}", type, minDate,
                maxDate, minDistance, maxDistance, pageable);
        return activitiesService.listActivities(pageable, type, minDate, maxDate, minDistance, maxDistance, query);
    }

    @GetMapping(value = "/activities/index")
    public String indexActivities(final HttpSession session) {
        return String.valueOf(
                activitiesService.indexActivities((String) session.getAttribute(AppConstants.SESSION_ACCESS_TOKEN)));
    }

    @GetMapping(value = "/activities/last")
    public AppActivity getLastActivity() {
        return activitiesService.getLastActivity();
    }
}
