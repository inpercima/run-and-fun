package net.inpercima.runandfun.service;

import java.net.HttpURLConnection;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * @author Marcel Jänicke
 * @since 10.02.2015
 */
public interface HelperService {

    JsonNode convertJson(String json);

    HttpURLConnection openGetRequest(String url, String appTypeValue, String accessToken);

    HttpURLConnection openPostRequest(String url, String query, String contentTypeValue);

    String handleResponse(HttpURLConnection connection);

}
