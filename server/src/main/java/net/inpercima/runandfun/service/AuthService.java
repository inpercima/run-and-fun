package net.inpercima.runandfun.service;

import static net.inpercima.runandfun.runkeeper.constants.RunkeeperConstants.DE_AUTHORIZATION_URL;
import static net.inpercima.runandfun.runkeeper.constants.RunkeeperConstants.TOKEN_URL;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import lombok.NoArgsConstructor;
import net.inpercima.restapi.service.RestApiService;
import net.inpercima.runandfun.app.model.AppState;
import net.inpercima.runandfun.runkeeper.model.RunkeeperProfile;
import net.inpercima.runandfun.runkeeper.model.RunkeeperToken;

/**
 * @author Marcel Jänicke
 * @author Sebastian Peters
 * @since 26.01.2015
 */
@NoArgsConstructor
@Service
public class AuthService {

    @Inject
    private FeatureService featureService;

    @Inject
    private RestApiService restApiService;

    public String getAccessToken(final String code) {
        return restApiService.postForObject(TOKEN_URL, code, RunkeeperToken.class).getAccessToken();
    }

    public AppState getAppState(final String accessToken) {
        final AppState state = new AppState();
        state.setAccessToken(accessToken);
        state.setClientId(restApiService.getClientId());
        state.setRedirectUri(restApiService.getRedirectUri());
        if (state.getAccessToken() != null) {
            final RunkeeperProfile profile = featureService.getProfile(state.getAccessToken());
            state.setFullName(profile.getName());
            state.setUsername(profile.getUsername());
        }
        return state;
    }

    public void deAuthorize(final String accessToken) {
        restApiService.postForObject(DE_AUTHORIZATION_URL, accessToken);
    }
}
