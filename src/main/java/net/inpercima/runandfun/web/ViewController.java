package net.inpercima.runandfun.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import net.inpercima.runandfun.constants.AppConstants;
import net.inpercima.runandfun.model.RunkeeperItem;
import net.inpercima.runandfun.service.RunAndFunService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Marcel Jänicke
 * @since 17.02.2015
 */
@Controller
public class ViewController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ViewController.class);

    private static final String REDIRECT_TO_BASE = "redirect:/";

    @Autowired
    private RunAndFunService runAndFunService;

    @RequestMapping(value = "/verify")
    public String verify(final HttpSession session, @RequestParam(value = "code", required = true) final String code) {
        runAndFunService.setAccessTokenToSession(session, runAndFunService.getAccessToken(code));
        return REDIRECT_TO_BASE;
    }

    @RequestMapping(value = "/indexActivities")
    public String indexActivities(final HttpSession session) {
        final String accessToken = (String) session.getAttribute(AppConstants.SESSION_ACCESS_TOKEN);
        // TODO SEPE: need filters for activity type, for now index just runs
        final List<RunkeeperItem> activities = accessToken == null ? new ArrayList<>() : runAndFunService
                .getActivities(accessToken).getRuns();
        LOGGER.info("found {} activities", activities.size());
        runAndFunService.indexActivities(activities);
        return REDIRECT_TO_BASE;
    }

    @RequestMapping(value = "/logout")
    public String logout(final HttpSession session) {
        runAndFunService.logout(session);
        return REDIRECT_TO_BASE;
    }

}
