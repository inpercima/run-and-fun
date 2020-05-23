package net.inpercima.runandfun.web;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import net.inpercima.runandfun.app.constants.AppConstants;
import net.inpercima.runandfun.app.model.AppState;
import net.inpercima.runandfun.service.RunAndFunService;

/**
 * @author Marcel Jänicke
 */
@RestController
@Slf4j
public class AuthController {

    @Inject
    private RunAndFunService runAndFunService;

    @GetMapping(value = "/state")
    public AppState state(final HttpSession session) {
        AppState appState = (AppState) session.getAttribute(AppConstants.SESSION_APP_STATE);
        if (appState == null || appState.getUsername() == null) {
            appState = runAndFunService.getAppState((String) session.getAttribute(AppConstants.SESSION_ACCESS_TOKEN));
            session.setAttribute(AppConstants.SESSION_APP_STATE, appState);
        }
        return appState;
    }

    @GetMapping(value = "/verify")
    public String verify(final HttpSession session, @RequestParam(value = "code", required = false) final String code,
            @RequestParam(value = "error", required = false) final String error) {
        if (!"access_denied".equals(error)) {
            session.setAttribute(AppConstants.SESSION_ACCESS_TOKEN, runAndFunService.getAccessToken(code));
        } else {
            log.warn(error);
        }
        return StringUtils.EMPTY;
    }

    @GetMapping(value = "/logout")
    public void logout(final HttpSession session) {
        session.invalidate();
    }
}
