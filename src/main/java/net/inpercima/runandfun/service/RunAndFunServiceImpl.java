package net.inpercima.runandfun.service;

import static net.inpercima.runandfun.constants.RunkeeperApiConstants.PROFILE_APP;
import static net.inpercima.runandfun.constants.RunkeeperApiConstants.PROFILE_URL;
import static net.inpercima.runandfun.constants.RunkeeperApiConstants.TOKEN_URL;
import static net.inpercima.runandfun.constants.RunkeeperApiConstants.USER_APP;
import static net.inpercima.runandfun.constants.RunkeeperApiConstants.USER_URL;

import java.nio.charset.StandardCharsets;

import net.inpercima.model.RunkeeperProfile;
import net.inpercima.model.RunkeeperToken;
import net.inpercima.model.RunkeeperUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author Marcel Jänicke
 * @since 26.01.2015
 */
@Service
public class RunAndFunServiceImpl implements RunAndFunService {

    @Autowired
    private HelperService helperService;

    private RestTemplate restTemplate;

    public RunAndFunServiceImpl() {
        restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter(StandardCharsets.UTF_8));
    }

    @Override
    public String getAccessToken(final String code) {
        RunkeeperToken result = restTemplate.postForObject(TOKEN_URL, helperService.createTokenParams(code),
                RunkeeperToken.class);
        return result.getAccess_token();
    }

    @Override
    public String getUserData(final String accessToken) {
        HttpEntity<RunkeeperUser> user = restTemplate.exchange(USER_URL, HttpMethod.GET,
                helperService.createHttpEntity(accessToken, USER_APP), RunkeeperUser.class,
                helperService.createAccessParams(accessToken));
        return user.getBody().getUserID();
    }

    @Override
    public String getProfileData(final String accessToken) {
        HttpEntity<RunkeeperProfile> profile = restTemplate.exchange(PROFILE_URL, HttpMethod.GET,
                helperService.createHttpEntity(accessToken, PROFILE_APP), RunkeeperProfile.class,
                helperService.createAccessParams(accessToken));
        return profile.getBody().getName();
    }

}
