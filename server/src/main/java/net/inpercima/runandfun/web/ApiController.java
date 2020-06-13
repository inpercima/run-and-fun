package net.inpercima.runandfun.web;

import java.time.LocalDate;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.data.domain.Page;
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
import net.inpercima.runandfun.runkeeper.model.RunkeeperFriendItem;
import net.inpercima.runandfun.runkeeper.model.RunkeeperProfile;
import net.inpercima.runandfun.service.RunAndFunService;

/**
 * @author Marcel Jänicke
 * @author Sebastian Peters
 * @since 02.02.2015
 */
@RestController
@Slf4j
public class ApiController {

    @Inject
    private RunAndFunService runAndFunService;

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
        return runAndFunService.listActivities(pageable, type, minDate, maxDate, minDistance, maxDistance, query);
    }

    @GetMapping(value = "/activities/index")
    public String indexActivities(final HttpSession session) {
        return String.valueOf(
                runAndFunService.indexActivities((String) session.getAttribute(AppConstants.SESSION_ACCESS_TOKEN)));
    }

    @GetMapping(value = "/listActivitiesByType")
    public Page<AppActivity> listActivitiesByType(@RequestParam(required = false) final String type) {
        log.debug("listActivitiesByType '{}'", type);
        return runAndFunService.listAllActivitiesByType(type);
    }

    @GetMapping(value = "/friends")
    public List<RunkeeperFriendItem> listFriends(final HttpSession session) {
        return runAndFunService.getFriends((String) session.getAttribute(AppConstants.SESSION_ACCESS_TOKEN));
    }

    @GetMapping(value = "/profile")
    public RunkeeperProfile profile(final HttpSession session) {
        return runAndFunService.getProfile((String) session.getAttribute(AppConstants.SESSION_ACCESS_TOKEN));
    }
}
