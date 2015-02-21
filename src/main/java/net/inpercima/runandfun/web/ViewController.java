package net.inpercima.runandfun.web;

import javax.servlet.http.HttpSession;

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

    @RequestMapping(value = "/verify", method = RequestMethod.GET)
    public String verify(HttpSession session, @RequestParam(value = "code", required = true) String code) {
        runAndFunService.setAccessTokenToSession(session, runAndFunService.getAccessToken(code));
        return "redirect:/";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpSession session) {
        runAndFunService.logout(session);
        return "redirect:/";
    }

}
