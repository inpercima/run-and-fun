package net.inpercima.runandfun.service;

import static net.inpercima.runandfun.constants.RunkeeperApiConstants.ACCEPT;
import static net.inpercima.runandfun.constants.RunkeeperApiConstants.ACCEPT_CHARSET;
import static net.inpercima.runandfun.constants.RunkeeperApiConstants.ACCESS_TOKEN;
import static net.inpercima.runandfun.constants.RunkeeperApiConstants.AUTHORIZATION;
import static net.inpercima.runandfun.constants.RunkeeperApiConstants.AUTHORIZATION_CODE;
import static net.inpercima.runandfun.constants.RunkeeperApiConstants.BEARER;
import static net.inpercima.runandfun.constants.RunkeeperApiConstants.CLIENT_ID;
import static net.inpercima.runandfun.constants.RunkeeperApiConstants.CLIENT_SECRET;
import static net.inpercima.runandfun.constants.RunkeeperApiConstants.CODE;
import static net.inpercima.runandfun.constants.RunkeeperApiConstants.GRANT_TYPE;
import static net.inpercima.runandfun.constants.RunkeeperApiConstants.REDIRECT_URI;

import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * @author Marcel Jänicke
 * @since 10.02.2015
 */
@Service
public class HelperServiceImpl implements HelperService {

    @Value("${app.clientId}")
    private String clientId;

    @Value("${app.clientSecret}")
    private String clientSecret;

    @Value("${app.redirectUri}")
    private String redirectUri;

    private RestTemplate restTemplate;

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    public HelperServiceImpl() {
        restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter(StandardCharsets.UTF_8));
    }

    @Override
    public <T> T postForObject(final String url, final String code, final Class<T> clazz) {
        return restTemplate.postForObject(url, createTokenParams(code), clazz);
    }

    @Override
    public <T> HttpEntity<T> getForObject(final String url, final String applicationType, final String accessToken,
            final Class<T> clazz) {
        return restTemplate.exchange(url, HttpMethod.GET, createHttpEntity(accessToken, applicationType), clazz,
                createAccessParams(accessToken));
    }

    private MultiValueMap<String, String> createTokenParams(final String code) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add(CLIENT_ID, clientId);
        params.add(CLIENT_SECRET, clientSecret);
        params.add(GRANT_TYPE, AUTHORIZATION_CODE);
        params.add(REDIRECT_URI, redirectUri);
        params.add(CODE, code);
        return params;
    }

    private MultiValueMap<String, String> createAccessParams(final String accessToken) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add(ACCESS_TOKEN, accessToken);
        return params;
    }

    private <T> HttpEntity<T> createHttpEntity(String accessToken, final String applicationType) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(ACCEPT, applicationType);
        headers.set(AUTHORIZATION, BEARER.concat(accessToken));
        headers.set(ACCEPT_CHARSET, StandardCharsets.UTF_8.displayName());
        HttpEntity<T> entity = new HttpEntity<T>(headers);
        return entity;
    }

}
