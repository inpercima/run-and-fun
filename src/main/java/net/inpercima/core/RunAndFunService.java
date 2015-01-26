package net.inpercima.core;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;

public interface RunAndFunService {

	String createToken(String code, String clientId, String clientSecret,
			String redirectUri) throws UnsupportedEncodingException, MalformedURLException, IOException;

}
