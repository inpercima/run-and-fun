package net.inpercima.runandfun.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

public class RunAndFunServiceImpl implements RunAndFunService {

    @Override
    public String createToken(final String code, final String clientId, final String clientSecret,
            final String redirectUri) throws MalformedURLException, IOException {

        final String grantType = "authorization_code";
        final String tokenUrl = "https://runkeeper.com/apps/token";
        final String charset = "UTF-8";

        String query = String.format("grant_type=%s&code=%s&client_id=%s&client_secret=%s&redirect_uri=%s",
                URLEncoder.encode(grantType, charset), URLEncoder.encode(code, charset),
                URLEncoder.encode(clientId, charset), URLEncoder.encode(clientSecret, charset),
                URLEncoder.encode(redirectUri, charset));

        URL url = new URL(tokenUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        // trigger POST
        connection.setDoOutput(true);
        connection.setRequestProperty("Accept-Charset", charset);
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);

        try (OutputStream output = connection.getOutputStream()) {
            output.write(query.getBytes(charset));
        }
        String response = null;

        InputStream stream = connection.getErrorStream();
        if (stream == null) {
            stream = connection.getInputStream();
        }
        try (Scanner scanner = new Scanner(stream)) {
            scanner.useDelimiter("\\Z");
            response = scanner.next();
        }

        return response;
    }
}
