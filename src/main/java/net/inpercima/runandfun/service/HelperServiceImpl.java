package net.inpercima.runandfun.service;

import static net.inpercima.runandfun.app.constants.AppConstants.ACCEPT_CHARSET;
import static net.inpercima.runandfun.runkeeper.constants.RunkeeperConstants.ACCEPT;
import static net.inpercima.runandfun.runkeeper.constants.RunkeeperConstants.ACCESS_TOKEN;
import static net.inpercima.runandfun.runkeeper.constants.RunkeeperConstants.AUTHORIZATION;
import static net.inpercima.runandfun.runkeeper.constants.RunkeeperConstants.AUTHORIZATION_CODE;
import static net.inpercima.runandfun.runkeeper.constants.RunkeeperConstants.AUTHORIZATION_BASIC;
import static net.inpercima.runandfun.runkeeper.constants.RunkeeperConstants.AUTHORIZATION_BEARER;
import static net.inpercima.runandfun.runkeeper.constants.RunkeeperConstants.CLIENT_ID;
import static net.inpercima.runandfun.runkeeper.constants.RunkeeperConstants.CLIENT_SECRET;
import static net.inpercima.runandfun.runkeeper.constants.RunkeeperConstants.CODE;
import static net.inpercima.runandfun.runkeeper.constants.RunkeeperConstants.GRANT_TYPE;
import static net.inpercima.runandfun.runkeeper.constants.RunkeeperConstants.REDIRECT_URI;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.google.common.base.Strings;

/**
 * @author Marcel Jänicke
 * @since 10.02.2015
 */
@Service
public class HelperServiceImpl implements HelperService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HelperServiceImpl.class);

    @Value("${app.clientId}")
    private String clientId;

    @Value("${app.clientSecret}")
    private String clientSecret;

    @Value("${app.redirectUri}")
    private String redirectUri;

    private final RestTemplate restTemplate;

    @Override
    public String getClientId() {
        return clientId;
    }

    @Override
    public void setClientId(final String clientId) {
        this.clientId = clientId;
    }

    @Override
    public void setClientSecret(final String clientSecret) {
        this.clientSecret = clientSecret;
    }

    @Override
    public String getRedirectUri() {
        return redirectUri;
    }

    public HelperServiceImpl() {
        restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter(StandardCharsets.UTF_8));
    }

    @Override
    public void postForObject(final String url, final String accessToken) {
        restTemplate.postForLocation(url, createTokenParams(accessToken));
    }

    @Override
    public <T> T postForObject(final String url, final String code, final Class<T> clazz) {
        return restTemplate.postForObject(url, createTokenParams(code), clazz);
    }

    public <T> HttpEntity<T> getForObject(final String url, final String applicationType, final Class<T> clazz) {
        return restTemplate.exchange(url, HttpMethod.GET, createHttpEntity(applicationType), clazz);
    }

    @Override
    public <T> HttpEntity<T> getForObject(final String url, final String applicationType, final String accessToken,
            final Class<T> clazz) {
        LOGGER.debug("get {} with {} for token {}", url, applicationType, accessToken);
        return restTemplate.exchange(url, HttpMethod.GET, createHttpEntity(accessToken, applicationType), clazz,
                createAccessParams(accessToken));
    }

    private static MultiValueMap<String, String> createAccessParams(final String accessToken) {
        final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add(ACCESS_TOKEN, accessToken);
        return params;
    }

    private MultiValueMap<String, String> createTokenParams(final String code) {
        final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add(CLIENT_ID, clientId);
        params.add(CLIENT_SECRET, clientSecret);
        params.add(GRANT_TYPE, AUTHORIZATION_CODE);
        params.add(REDIRECT_URI, redirectUri);
        params.add(CODE, code);
        return params;
    }

    private <T> HttpEntity<T> createHttpEntity(final String applicationType) {
        final String basicCode = clientId.concat(":").concat(clientSecret);
        String base64Code = null;
        try {
            base64Code = Base64.getEncoder().encodeToString(basicCode.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            LOGGER.error(e.getMessage(), e);
        }
        final HttpHeaders headers = createHttpHeaders(applicationType);
        headers.set(AUTHORIZATION, AUTHORIZATION_BASIC.concat(Strings.nullToEmpty(base64Code)));
        return new HttpEntity<>(headers);
    }

    private static <T> HttpEntity<T> createHttpEntity(final String accessToken, final String applicationType) {
        final HttpHeaders headers = createHttpHeaders(applicationType);
        headers.set(AUTHORIZATION, AUTHORIZATION_BEARER.concat(Strings.nullToEmpty(accessToken)));
        return new HttpEntity<>(headers);
    }

    private static HttpHeaders createHttpHeaders(final String applicationType) {
        final HttpHeaders headers = new HttpHeaders();
        headers.set(ACCEPT, applicationType);
        headers.set(ACCEPT_CHARSET, StandardCharsets.UTF_8.displayName());
        return headers;
    }

}
