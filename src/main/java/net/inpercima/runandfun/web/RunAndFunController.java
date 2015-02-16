package net.inpercima.runandfun.web;

import static net.inpercima.runandfun.constants.AppConstants.LOGGED_IN;

import javax.servlet.http.HttpSession;

import net.inpercima.runandfun.model.AppState;
import net.inpercima.runandfun.service.HelperService;
import net.inpercima.runandfun.service.RunAndFunService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Marcel Jänicke
 * @since 02.02.2015
 */
@RestController
public class RunAndFunController {

    @Autowired
    private RunAndFunService runAndFunService;

    @Autowired
    private HelperService helperService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @ResponseBody
    public String login(HttpSession session, @RequestParam(value = "code", required = true) String code) {
        String accessToken = runAndFunService.getAccessToken(code);
        session.setAttribute(LOGGED_IN, accessToken != null);
        return "redirect:/";
    }

    @RequestMapping(value = "/state", method = RequestMethod.GET)
    @ResponseBody
    public AppState state(HttpSession session) {
        return runAndFunService.getAppState(session);
    }

}
