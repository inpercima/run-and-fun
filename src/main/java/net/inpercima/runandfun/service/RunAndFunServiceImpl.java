package net.inpercima.runandfun.service;

import static net.inpercima.runandfun.constants.RunkeeperApiConstants.PROFILE_APP;
import static net.inpercima.runandfun.constants.RunkeeperApiConstants.PROFILE_URL;
import static net.inpercima.runandfun.constants.RunkeeperApiConstants.TOKEN_URL;
import static net.inpercima.runandfun.constants.RunkeeperApiConstants.USER_APP;
import static net.inpercima.runandfun.constants.RunkeeperApiConstants.USER_URL;
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

    @Override
    public String getAccessToken(final String code) {
        RunkeeperToken token = helperService.postForObject(TOKEN_URL, code, RunkeeperToken.class);
        return token.getAccessToken();
    }

    @Override
    public RunkeeperUser getUserData(final String accessToken) {
        HttpEntity<RunkeeperUser> user = helperService.getForObject(USER_URL, USER_APP, accessToken,
                RunkeeperUser.class);
        return user.getBody();
    }

    @Override
    public RunkeeperProfile getProfileData(final String accessToken) {
        HttpEntity<RunkeeperProfile> user = helperService.getForObject(PROFILE_URL, PROFILE_APP, accessToken,
                RunkeeperProfile.class);
        return user.getBody();
    }

}
