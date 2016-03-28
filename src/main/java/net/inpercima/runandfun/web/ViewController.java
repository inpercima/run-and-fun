package net.inpercima.runandfun.web;

import static net.inpercima.runandfun.constants.AppConstants.SESSION_ACCESS_TOKEN;

import javax.servlet.http.HttpSession;

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

    private static final String ACCESS_DENIED = "access_denied";

    @Autowired
    private RunAndFunService runAndFunService;

    @RequestMapping(value = "/verify")
    public String verify(final HttpSession session, @RequestParam(value = "code", required = false) final String code,
            @RequestParam(value = "error", required = false) final String error) {
        if (!ACCESS_DENIED.equals(error)) {
            session.setAttribute(SESSION_ACCESS_TOKEN, runAndFunService.getAccessToken(code));
        } else {
            LOGGER.warn(error);
        }
        return REDIRECT_TO_BASE;
    }

    @RequestMapping(value = "/logout")
    public String logout(final HttpSession session) {
        session.invalidate();
        return REDIRECT_TO_BASE;
    }

}
