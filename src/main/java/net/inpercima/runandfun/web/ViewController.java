package net.inpercima.runandfun.web;

import static net.inpercima.runandfun.constants.AppConstants.LOGGED_IN;

import javax.servlet.http.HttpSession;

import net.inpercima.runandfun.service.HelperService;
import net.inpercima.runandfun.service.RunAndFunService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Marcel Jänicke
 * @since 17.02.2015
 */
@Controller
public class ViewController {

    @Autowired
    private RunAndFunService runAndFunService;

    @Autowired
    private HelperService helperService;

    @RequestMapping(value = "/verify", method = RequestMethod.GET)
    public String login(HttpSession session, @RequestParam(value = "code", required = true) String code) {
        String accessToken = runAndFunService.getAccessToken(code);
        session.setAttribute(LOGGED_IN, accessToken != null);
        session.setAttribute("accessToken", accessToken);
        return "redirect:/";
    }

}
