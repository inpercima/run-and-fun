package net.inpercima.runandfun.web;

import java.util.List;

import javax.servlet.http.HttpSession;

import net.inpercima.runandfun.constants.AppConstants;
import net.inpercima.runandfun.model.AppState;
import net.inpercima.runandfun.model.RunkeeperActivities;
import net.inpercima.runandfun.model.RunkeeperItem;
import net.inpercima.runandfun.service.RunAndFunService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Marcel Jänicke
 * @since 02.02.2015
 */
@RestController
public class RestApiController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestApiController.class);

    @Autowired
    private RunAndFunService runAndFunService;

    @RequestMapping(value = "/state", method = RequestMethod.GET)
    public AppState state(final HttpSession session) {
        return runAndFunService.getAppState(session);
    }

    @RequestMapping(value = "/activities", method = RequestMethod.GET)
    public RunkeeperActivities activities(final HttpSession session) {
        final String accessToken = (String) session.getAttribute(AppConstants.SESSION_ACCESS_TOKEN);
        if (accessToken == null) {
            return new RunkeeperActivities();
        }
        final RunkeeperActivities activities = runAndFunService.getActivities(accessToken);
        LOGGER.debug("found {} activities", activities.getSize());
        return activities;
    }

    @RequestMapping(value = "/runs", method = RequestMethod.GET)
    public List<RunkeeperItem> runs(final HttpSession session) {
        return activities(session).getRuns();
    }

    @RequestMapping(value = "/rides", method = RequestMethod.GET)
    public List<RunkeeperItem> rides(final HttpSession session) {
        return activities(session).getRides();
    }

}
