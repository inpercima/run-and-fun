package net.inpercima.runandfun.service;

import static net.inpercima.runandfun.constants.RunkeeperApiConstants.ACCEPT;
import static net.inpercima.runandfun.constants.RunkeeperApiConstants.AUTHORIZATION;
import static net.inpercima.runandfun.constants.RunkeeperApiConstants.AUTHORIZATION_CODE;
import static net.inpercima.runandfun.constants.RunkeeperApiConstants.CONTENT_TYPE;
import static net.inpercima.runandfun.constants.RunkeeperApiConstants.GET_METHOD;
import static net.inpercima.runandfun.constants.RunkeeperApiConstants.GRANT_AUTHORIZATION_QUERY;
import static net.inpercima.runandfun.constants.RunkeeperApiConstants.POST_METHOD;
import static net.inpercima.runandfun.constants.RunkeeperApiConstants.TOKEN_URL;
import static net.inpercima.runandfun.constants.RunkeeperApiConstants.URLENCODED_APP;
import static net.inpercima.runandfun.constants.RunkeeperApiConstants.USER_APP;
import static net.inpercima.runandfun.constants.RunkeeperApiConstants.PROFILE_APP;
import static net.inpercima.runandfun.constants.RunkeeperApiConstants.USER_URL;
import static net.inpercima.runandfun.constants.RunkeeperApiConstants.PROFILE_URL;
import static net.inpercima.runandfun.constants.RunkeeperApiConstants.UTF_8;
import static net.inpercima.runandfun.constants.RunkeeperApiConstants.BEARER;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Marcel Jänicke
 * @since 26.01.2015
 */
public class RunAndFunServiceImpl implements RunAndFunService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RunAndFunServiceImpl.class);

    private String clientId;

    private String clientSecret;

    private String redirectUri;

    @Override
    public String getAccessToken(final String code) {
        String response = null;
        try {
            String query = String.format(GRANT_AUTHORIZATION_QUERY, URLEncoder.encode(AUTHORIZATION_CODE, UTF_8),
                    URLEncoder.encode(code, UTF_8), URLEncoder.encode(clientId, UTF_8),
                    URLEncoder.encode(clientSecret, UTF_8), URLEncoder.encode(redirectUri, UTF_8));
            JsonNode jsonNode = convertJson(handleResponse(openPostRequest(TOKEN_URL, query, URLENCODED_APP)));
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
        response = handleResponse(openGetRequest(USER_URL, USER_APP, accessToken));
        return response;
    }
    
    @Override
    public String getProfileData(final String accessToken) {
        String response = null;
        response = handleResponse(openGetRequest(PROFILE_URL, PROFILE_APP, accessToken));
        return response;
    }

    private JsonNode convertJson(String json) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            jsonNode = mapper.readTree(json);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return jsonNode;
    }

    private HttpURLConnection openGetRequest(final String url, final String appTypeValue, final String accessToken) {
        return openRequest(url, null, appTypeValue, false, accessToken);
    }

    private HttpURLConnection openPostRequest(final String url, final String query, final String contentTypeValue) {
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

    private String handleResponse(final HttpURLConnection connection) {
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

    @Override
    public void loadProperties() {
        Properties properties = new Properties();
        InputStream in = getClass().getResourceAsStream("/run-and-fun.properties");
        try {
            properties.load(in);
            in.close();
            setRedirectUri(properties.getProperty("redirectUri"));
            setClientId(properties.getProperty("clientId"));
            setClientSecret(properties.getProperty("clientSecret"));
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}
