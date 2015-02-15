package net.inpercima.runandfun.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import net.inpercima.runandfun.model.AppState;
import net.inpercima.runandfun.model.RunkeeperProfile;
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

    private final static String LOGGED_IN = "loggedIn";

    @Autowired
    private RunAndFunService runAndFunService;

    @Autowired
    private HelperService helperService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @ResponseBody
    public List<RunkeeperProfile> login(HttpSession session, @RequestParam(value = "code", required = true) String code) {
        List<RunkeeperProfile> list = new ArrayList<>();
        String accessToken = runAndFunService.getAccessToken(code);
        if (accessToken != null) {
            session.setAttribute(LOGGED_IN, true);
        }
        list.add(runAndFunService.getProfileData(accessToken));
        return list;
    }

    @RequestMapping(value = "/state", method = RequestMethod.GET)
    @ResponseBody
    public AppState state(HttpSession session) {
        AppState state = new AppState();
        boolean isLoggedIn = session.getAttribute(LOGGED_IN) != null
                && (Boolean) session.getAttribute(LOGGED_IN) == true;
        state.setLoggedIn(isLoggedIn);
        state.setClientId(helperService.getClientId());
        state.setRedirectUri(helperService.getRedirectUri());
        return state;
    }

}
