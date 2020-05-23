package net.inpercima.runandfun.web;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.common.net.HttpHeaders;

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

    private static final String HEADER_ACCESS_TOKEN = "x-accessToken";

    @Inject
    private RunAndFunService runAndFunService;

    @GetMapping(value = "/verify")
    public String verify(final HttpServletResponse response, final HttpSession session,
            @RequestParam(required = false) final String code, @RequestParam(required = false) final String error) {
        String accessToken = null;
        if (!"access_denied".equals(error)) {
            accessToken = runAndFunService.getAccessToken(code);
            session.setAttribute(AppConstants.SESSION_ACCESS_TOKEN, accessToken);
        } else {
            log.warn(error);
        }
        if (accessToken != null) {
            response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HEADER_ACCESS_TOKEN);
            response.setHeader(HEADER_ACCESS_TOKEN, accessToken);
        }
        return StringUtils.EMPTY;
    }

    @GetMapping(value = "/state")
    public AppState state(final HttpSession session) {
        AppState appState = (AppState) session.getAttribute(AppConstants.SESSION_APP_STATE);
        if (appState == null || appState.getUsername() == null) {
            appState = runAndFunService.getAppState((String) session.getAttribute(AppConstants.SESSION_ACCESS_TOKEN));
            session.setAttribute(AppConstants.SESSION_APP_STATE, appState);
        }
        return appState;
    }

    @GetMapping(value = "/logout")
    public void logout(final HttpSession session) {
        log.info("invalidate session with Id '{}'", session.getId());
        session.invalidate();
    }
}
