package net.inpercima.runandfun.service;

import static net.inpercima.runandfun.constants.RunkeeperApiConstants.AUTHORIZATION_CODE;
import static net.inpercima.runandfun.constants.RunkeeperApiConstants.GRANT_AUTHORIZATION_QUERY;
import static net.inpercima.runandfun.constants.RunkeeperApiConstants.PROFILE_APP;
import static net.inpercima.runandfun.constants.RunkeeperApiConstants.PROFILE_URL;
import static net.inpercima.runandfun.constants.RunkeeperApiConstants.TOKEN_URL;
import static net.inpercima.runandfun.constants.RunkeeperApiConstants.URLENCODED_APP;
import static net.inpercima.runandfun.constants.RunkeeperApiConstants.USER_APP;
import static net.inpercima.runandfun.constants.RunkeeperApiConstants.USER_URL;
import static net.inpercima.runandfun.constants.RunkeeperApiConstants.UTF_8;

import java.io.IOException;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * @author Marcel Jänicke
 * @since 26.01.2015
 */
@Service
public class RunAndFunServiceImpl implements RunAndFunService {

    @Autowired
    private HelperService helperService;

    private static final Logger LOGGER = LoggerFactory.getLogger(RunAndFunServiceImpl.class);

    @Value("${app.clientId}")
    private String clientId;

    @Value("${app.clientSecret}")
    private String clientSecret;

    @Value("${app.redirectUri:http://localhost:8080/login}")
    private String redirectUri;

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    @Override
    public String getAccessToken(final String code) {
        String response = null;
        try {
            String query = String.format(GRANT_AUTHORIZATION_QUERY, URLEncoder.encode(AUTHORIZATION_CODE, UTF_8),
                    URLEncoder.encode(code, UTF_8), URLEncoder.encode(clientId, UTF_8),
                    URLEncoder.encode(clientSecret, UTF_8), URLEncoder.encode(redirectUri, UTF_8));
            JsonNode jsonNode = helperService.convertJson(helperService.handleResponse(helperService.openPostRequest(
                    TOKEN_URL, query, URLENCODED_APP)));
            response = jsonNode.get("access_token").toString()
                    .substring(1, jsonNode.get("access_token").toString().length() - 1);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return response;
    }

    @Override
    public String getUserData(final String accessToken) {
        String response = null;
        response = helperService.handleResponse(helperService.openGetRequest(USER_URL, USER_APP, accessToken));
        return response;
    }

    @Override
    public String getProfileData(final String accessToken) {
        String response = null;
        response = helperService.handleResponse(helperService.openGetRequest(PROFILE_URL, PROFILE_APP, accessToken));
        return response;
    }

}
