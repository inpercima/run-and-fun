package net.inpercima.runandfun.service;

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

import net.inpercima.runandfun.constants.RunAndFunConstants;

public class RunAndFunServiceImpl implements RunAndFunService {

	@Override
	public String getAccessToken(final String code, final String clientId,
			final String clientSecret, final String redirectUri)
			throws MalformedURLException, IOException {

		Map<String, String> params = new LinkedHashMap<>();
		params.put(RunAndFunConstants.GRANT_TYPE,
				RunAndFunConstants.GRANT_TYPE_AUTHORIZATION);
		params.put(RunAndFunConstants.CODE, code);
		params.put(RunAndFunConstants.CLIENT_ID, clientId);
		params.put(RunAndFunConstants.CLIENT_SECRET, clientSecret);
		params.put(RunAndFunConstants.REDIRECT_URI, redirectUri);
		String query = generateQuery(params);

		return handleResponse(openPostRequest(RunAndFunConstants.TOKEN_URL,
				query));
	}

	private HttpURLConnection openPostRequest(final String url,
			final String query) throws UnsupportedEncodingException,
			IOException {
		URL postUrl = new URL(url);
		HttpURLConnection connection = (HttpURLConnection) postUrl
				.openConnection();
		connection.setRequestMethod(RunAndFunConstants.METHOD_POST);
		connection.setDoOutput(true);
		connection.setRequestProperty("Content-Type",
				RunAndFunConstants.FORM_URLENCODED);
		connection.getOutputStream().write(
				query.getBytes(RunAndFunConstants.UTF_8));
		return connection;
	}

	private String generateQuery(final Map<String, String> params)
			throws UnsupportedEncodingException {
		StringBuilder query = new StringBuilder();
		for (Map.Entry<String, String> param : params.entrySet()) {
			if (query.length() != 0)
				query.append("&");
			query.append(URLEncoder.encode(param.getKey(),
					RunAndFunConstants.UTF_8));
			query.append("=");
			query.append(URLEncoder.encode(String.valueOf(param.getValue()),
					RunAndFunConstants.UTF_8));
		}
		System.out.println(query.toString());
		return query.toString();
	}

	private String handleResponse(final HttpURLConnection connection)
			throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				connection.getInputStream()));
		String line;
		String response = "";
		while ((line = reader.readLine()) != null) {
			response = response + line;
		}
		reader.close();
		return response;
	}
}
