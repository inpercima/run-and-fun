package net.inpercima.runandfun.web;

import javax.servlet.http.HttpSession;

import net.inpercima.runandfun.model.AppState;
import net.inpercima.runandfun.model.RunkeeperActivities;
import net.inpercima.runandfun.service.RunAndFunService;

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

    @Autowired
    private RunAndFunService runAndFunService;

    @RequestMapping(value = "/state", method = RequestMethod.GET)
    public AppState state(HttpSession session) {
        return runAndFunService.getAppState(session);
    }

    @RequestMapping(value = "/activities", method = RequestMethod.GET)
    public RunkeeperActivities activities(HttpSession session) {
        return runAndFunService.getActivities((String) session.getAttribute("accessToken"));
    }

}
