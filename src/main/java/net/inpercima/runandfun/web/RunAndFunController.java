package net.inpercima.runandfun.web;

import net.inpercima.runandfun.service.RunAndFunService;
import net.inpercima.runandfun.service.RunAndFunServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(RunAndFunController.class);

    RunAndFunService runAndFun = new RunAndFunServiceImpl();

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @ResponseBody
    public String login(@RequestParam(value = "code", required = true) String code) {
        runAndFun.loadProperties();
        String accessToken = runAndFun.getAccessToken(code);
        LOGGER.debug(accessToken);
        return runAndFun.getUserData(accessToken).concat(runAndFun.getProfileData(accessToken));
    }
}
