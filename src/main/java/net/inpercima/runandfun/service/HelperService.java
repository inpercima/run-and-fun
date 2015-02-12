package net.inpercima.runandfun.service;

import org.springframework.http.HttpEntity;
import org.springframework.util.MultiValueMap;

/**
 * @author Marcel Jänicke
 * @since 10.02.2015
 */
public interface HelperService {

    MultiValueMap<String, String> createTokenParams(String code);

    MultiValueMap<String, String> createAccessParams(String accessToken);

    HttpEntity<?> createHttpEntity(String accessToken, String applicationType);
}
