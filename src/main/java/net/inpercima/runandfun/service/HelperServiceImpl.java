package net.inpercima.runandfun.service;

import static net.inpercima.runandfun.constants.RunkeeperApiConstants.ACCEPT;
import static net.inpercima.runandfun.constants.RunkeeperApiConstants.AUTHORIZATION;
import static net.inpercima.runandfun.constants.RunkeeperApiConstants.BEARER;
import static net.inpercima.runandfun.constants.RunkeeperApiConstants.CONTENT_TYPE;
import static net.inpercima.runandfun.constants.RunkeeperApiConstants.GET_METHOD;
import static net.inpercima.runandfun.constants.RunkeeperApiConstants.POST_METHOD;
import static net.inpercima.runandfun.constants.RunkeeperApiConstants.UTF_8;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Marcel Jänicke
 * @since 10.02.2015
 */
@Service
public class HelperServiceImpl implements HelperService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HelperServiceImpl.class);

    public HttpURLConnection openGetRequest(final String url, final String appTypeValue, final String accessToken) {
        return openRequest(url, null, appTypeValue, false, accessToken);
    }

    public HttpURLConnection openPostRequest(final String url, final String query, final String contentTypeValue) {
        return openRequest(url, query, contentTypeValue, true, null);
    }

    private HttpURLConnection openRequest(final String url, final String query, final String typeValue,
            final boolean isPost, final String accessToken) {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod(isPost ? POST_METHOD : GET_METHOD);
            connection.setDoOutput(isPost);
            connection.setRequestProperty(isPost ? CONTENT_TYPE : ACCEPT, typeValue);
            if (isPost) {
                connection.getOutputStream().write(query.getBytes(UTF_8));
            }
            if (accessToken != null) {
                connection.setRequestProperty(AUTHORIZATION, BEARER.concat(accessToken));
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return connection;
    }

    public String handleResponse(final HttpURLConnection connection) {
        BufferedReader reader;
        String response = "";
        try {
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                response = response + line;
            }
            reader.close();
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return response;
    }

    public JsonNode convertJson(String json) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            jsonNode = mapper.readTree(json);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return jsonNode;
    }

}
