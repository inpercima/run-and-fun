package net.inpercima.runandfun.web;

import java.util.ArrayList;
import java.util.List;

import net.inpercima.runandfun.model.AppState;
import net.inpercima.runandfun.model.RunkeeperProfile;
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
    public List<RunkeeperProfile> login(@RequestParam(value = "code", required = true) String code) {
        List<RunkeeperProfile> list = new ArrayList<>();
        String accessToken = runAndFunService.getAccessToken(code);
        list.add(runAndFunService.getProfileData(accessToken));
        return list;
    }

    @RequestMapping(value = "/state", method = RequestMethod.GET)
    @ResponseBody
    public AppState state() {
        AppState state = new AppState();
        state.setLoggedIn(true);
        return state;
    }

}
