package net.inpercima.runandfun.web;

import static net.inpercima.runandfun.constants.AppConstants.SESSION_ACCESS_TOKEN;

import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.inpercima.runandfun.constants.AppConstants;
import net.inpercima.runandfun.model.Activity;
import net.inpercima.runandfun.model.AppState;
import net.inpercima.runandfun.model.RunkeeperFriendItem;
import net.inpercima.runandfun.service.RunAndFunService;

/**
 * @author Marcel Jänicke
 * @author Sebastian Peters
 * @since 02.02.2015
 */
@RestController
public class RestApiController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestApiController.class);

    @Autowired
    private RunAndFunService runAndFunService;

    @RequestMapping(value = "/state", method = RequestMethod.GET)
    public AppState state(final HttpSession session) {
        AppState appState = (AppState) session.getAttribute(AppConstants.SESSION_APP_STATE);
        if (appState == null || appState.getUsername() == null) {
            appState = runAndFunService.getAppState((String) session.getAttribute(SESSION_ACCESS_TOKEN));
            session.setAttribute(AppConstants.SESSION_APP_STATE, appState);
        }
        return appState;
    }

    @RequestMapping(value = "/listActivitiesByType", method = RequestMethod.GET)
    public Page<Activity> listActivitiesByType(@RequestParam(required = false) final String type) {
        LOGGER.debug("listActivitiesByType '{}'", type);
        return runAndFunService.listAllActivitiesByType(type);
    }

    @RequestMapping(value = "/indexActivities")
    public String indexActivities(final HttpSession session) {
        final String accessToken = (String) session.getAttribute(AppConstants.SESSION_ACCESS_TOKEN);
        return String.valueOf(runAndFunService.indexActivities(accessToken));
    }

    @RequestMapping(value = "/listActivities", method = RequestMethod.GET)
    public Page<Activity> listActivities(
            @PageableDefault(direction = Direction.DESC, sort = "date") final Pageable pageable,
            @RequestParam(required = false) final String type,
            @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE) final LocalDate minDate,
            @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE) final LocalDate maxDate,
            @RequestParam(required = false) final Float minDistance,
            @RequestParam(required = false) final Float maxDistance,
            @RequestParam(required = false) final String query) {
        LOGGER.debug("listActivities of type '{}' for date between {} - {}, distance between {} - {}, {}", type,
                minDate, maxDate, minDistance, maxDistance, pageable);
        return runAndFunService.listActivities(pageable, type, minDate, maxDate, minDistance, maxDistance, query);
    }

    @RequestMapping(value = "/listFriends", method = RequestMethod.GET)
    public List<RunkeeperFriendItem> listFriends(final HttpSession session) {
        final String accessToken = (String) session.getAttribute(AppConstants.SESSION_ACCESS_TOKEN);
        return runAndFunService.getFriends(accessToken);
    }

}
