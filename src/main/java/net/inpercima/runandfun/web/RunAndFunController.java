package net.inpercima.runandfun.web;

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

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @ResponseBody
    public String login(@RequestParam(value = "code", required = true) String code) {
        String accessToken = runAndFunService.getAccessToken(code);
        return runAndFunService.getUserData(accessToken).concat(runAndFunService.getProfileData(accessToken));
    }
}
