package net.inpercima.runandfun.service;

import org.springframework.http.HttpEntity;

/**
 * @author Marcel Jänicke
 * @since 10.02.2015
 */
public interface HelperService {

    <T> T postForObject(String url, String code, Class<T> clazz);

    <T> HttpEntity<T> getForObject(String url, String applicationType, String accessToken, Class<T> clazz);

}
