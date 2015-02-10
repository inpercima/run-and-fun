package net.inpercima.runandfun.service;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * @author Marcel Jänicke
 * @since 10.02.2015
 */
public interface HelperService {

    JsonNode convertJson(String json);

    String handleResponse(boolean isPost, String url, String parameterValue, String content);

}
