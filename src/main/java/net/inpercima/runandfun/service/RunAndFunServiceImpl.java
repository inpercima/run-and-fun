package net.inpercima.runandfun.service;

import static net.inpercima.runandfun.constants.AppConstants.SESSION_ACCESS_TOKEN;
import static net.inpercima.runandfun.constants.RunkeeperApiConstants.ACTIVITIES_APP;
import static net.inpercima.runandfun.constants.RunkeeperApiConstants.ACTIVITIES_URL;
import static net.inpercima.runandfun.constants.RunkeeperApiConstants.DE_AUTHORIZATION_URL;
import static net.inpercima.runandfun.constants.RunkeeperApiConstants.PAGE_SIZE;
import static net.inpercima.runandfun.constants.RunkeeperApiConstants.PROFILE_APP;
import static net.inpercima.runandfun.constants.RunkeeperApiConstants.PROFILE_URL;
import static net.inpercima.runandfun.constants.RunkeeperApiConstants.TOKEN_URL;
import static net.inpercima.runandfun.constants.RunkeeperApiConstants.USER_APP;
import static net.inpercima.runandfun.constants.RunkeeperApiConstants.USER_URL;

import javax.servlet.http.HttpSession;

import net.inpercima.runandfun.model.AppState;
import net.inpercima.runandfun.model.RunkeeperActivities;
import net.inpercima.runandfun.model.RunkeeperProfile;
import net.inpercima.runandfun.model.RunkeeperToken;
import net.inpercima.runandfun.model.RunkeeperUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;

/**
 * @author Marcel Jänicke
 * @since 26.01.2015
 */
@Service
public class RunAndFunServiceImpl implements RunAndFunService {

    @Autowired
    private HelperService helperService;

    public HelperService getHelperService() {
        return helperService;
    }

    @Override
    public String getAccessToken(final String code) {
        RunkeeperToken token = helperService.postForObject(TOKEN_URL, code, RunkeeperToken.class);
        return token.getAccessToken();
    }

    @Override
    public RunkeeperUser getUser(final String accessToken) {
        HttpEntity<RunkeeperUser> user = helperService.getForObject(USER_URL, USER_APP, accessToken,
                RunkeeperUser.class);
        return user.getBody();
    }

    @Override
    public RunkeeperProfile getProfile(final String accessToken) {
        HttpEntity<RunkeeperProfile> profile = helperService.getForObject(PROFILE_URL, PROFILE_APP, accessToken,
                RunkeeperProfile.class);
        return profile.getBody();
    }

    @Override
    public RunkeeperActivities getActivities(final String accessToken) {
        // first get one item only to get full size
        HttpEntity<RunkeeperActivities> activitiesForSize = helperService.getForObject(ACTIVITIES_URL + PAGE_SIZE + 1,
                ACTIVITIES_APP, accessToken, RunkeeperActivities.class);
        // after that get all activities
        HttpEntity<RunkeeperActivities> activities = helperService.getForObject(ACTIVITIES_URL + PAGE_SIZE
                + activitiesForSize.getBody().getSize(), ACTIVITIES_APP, accessToken, RunkeeperActivities.class);
        return activities.getBody();
    }

    @Override
    public AppState getAppState(HttpSession session) {
        AppState state = new AppState();
        state.setAccessToken((String) session.getAttribute(SESSION_ACCESS_TOKEN));
        state.setClientId(helperService.getClientId());
        state.setRedirectUri(helperService.getRedirectUri());
        if (state.getAccessToken() != null) {
            RunkeeperProfile profile = getProfile(state.getAccessToken());
            state.setUsername(profile.getName());
        }
        return state;
    }

    @Override
    public void setAccessTokenToSession(HttpSession session, final String accessToken) {
        session.setAttribute(SESSION_ACCESS_TOKEN, accessToken != null ? accessToken : null);
    }

    @Override
    public void logout(HttpSession session) {
        helperService.postForObject(DE_AUTHORIZATION_URL, (String) session.getAttribute(SESSION_ACCESS_TOKEN));
        session.removeAttribute(SESSION_ACCESS_TOKEN);
    }

}
