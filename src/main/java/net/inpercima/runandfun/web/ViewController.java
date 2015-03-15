package net.inpercima.runandfun.web;

import static net.inpercima.runandfun.constants.AppConstants.SESSION_ACCESS_TOKEN;

import javax.servlet.http.HttpSession;

import net.inpercima.runandfun.constants.AppConstants;
import net.inpercima.runandfun.service.RunAndFunService;

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

    private static final String REDIRECT_TO_BASE = "redirect:/";

    @Autowired
    private RunAndFunService runAndFunService;

    @RequestMapping(value = "/verify")
    public String verify(final HttpSession session, @RequestParam(value = "code", required = true) final String code) {
        session.setAttribute(SESSION_ACCESS_TOKEN, runAndFunService.getAccessToken(code));
        return REDIRECT_TO_BASE;
    }

    @RequestMapping(value = "/indexActivities")
    public String indexActivities(final HttpSession session) {
        final String accessToken = (String) session.getAttribute(AppConstants.SESSION_ACCESS_TOKEN);
        runAndFunService.indexActivities(accessToken);
        return REDIRECT_TO_BASE;
    }

    @RequestMapping(value = "/logout")
    public String logout(final HttpSession session) {
        session.removeAttribute(SESSION_ACCESS_TOKEN);
        return REDIRECT_TO_BASE;
    }

}
