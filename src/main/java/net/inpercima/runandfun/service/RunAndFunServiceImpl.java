package net.inpercima.runandfun.service;

import static net.inpercima.runandfun.constants.RunAndFunConstants.CLIENT_ID;
import static net.inpercima.runandfun.constants.RunAndFunConstants.CLIENT_SECRET;
import static net.inpercima.runandfun.constants.RunAndFunConstants.CODE;
import static net.inpercima.runandfun.constants.RunAndFunConstants.CONTENT_TYPE;
import static net.inpercima.runandfun.constants.RunAndFunConstants.FORM_URLENCODED;
import static net.inpercima.runandfun.constants.RunAndFunConstants.GRANT_TYPE;
import static net.inpercima.runandfun.constants.RunAndFunConstants.GRANT_TYPE_AUTHORIZATION;
import static net.inpercima.runandfun.constants.RunAndFunConstants.METHOD_POST;
import static net.inpercima.runandfun.constants.RunAndFunConstants.REDIRECT_URI;
import static net.inpercima.runandfun.constants.RunAndFunConstants.TOKEN_URL;
import static net.inpercima.runandfun.constants.RunAndFunConstants.UTF_8;
import static net.inpercima.runandfun.constants.RunAndFunConstants.APP_PROFILE;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

public class RunAndFunServiceImpl implements RunAndFunService {

    @Override
    public String getAccessToken(final String code, final String clientId, final String clientSecret,
            final String redirectUri) throws MalformedURLException, IOException {
        Map<String, String> params = new LinkedHashMap<>();
        params.put(GRANT_TYPE, GRANT_TYPE_AUTHORIZATION);
        params.put(CODE, code);
        params.put(CLIENT_ID, clientId);
        params.put(CLIENT_SECRET, clientSecret);
        params.put(REDIRECT_URI, redirectUri);
        String query = generateQuery(params);
        return handleResponse(openPostRequest(TOKEN_URL, query, FORM_URLENCODED));
    }

    @Override
    public String getUserData(final String accessToken) throws MalformedURLException, IOException {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("access_token", accessToken);
        String query = generateQuery(params);

        return handleResponse(openPostRequest(TOKEN_URL, query, APP_PROFILE));
    }

    private HttpURLConnection openPostRequest(final String url, final String query, final String contentType)
            throws UnsupportedEncodingException, IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod(METHOD_POST);
        connection.setDoOutput(true);
        connection.setRequestProperty(CONTENT_TYPE, contentType);
        connection.getOutputStream().write(query.getBytes(UTF_8));
        return connection;
    }

    private String generateQuery(final Map<String, String> params) throws UnsupportedEncodingException {
        StringBuilder query = new StringBuilder();
        for (Map.Entry<String, String> param : params.entrySet()) {
            query.append(query.length() != 0 ? query.append("&") : "");
            query.append(URLEncoder.encode(param.getKey(), UTF_8)).append("=");
            query.append(URLEncoder.encode(String.valueOf(param.getValue()), UTF_8));
        }
        return query.toString();
    }

    private String handleResponse(final HttpURLConnection connection) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        String response = "";
        while ((line = reader.readLine()) != null) {
            response = response + line;
        }
        reader.close();
        System.out.println(response);
        return response;
    }
}
