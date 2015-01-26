package net.inpercima.core;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;

import org.junit.Test;

public class RunAndFunServiceTest {

	@Test
	public void test() throws UnsupportedEncodingException,
			MalformedURLException, IOException {
		RunAndFunService runAndFun = new RunAndFunServiceImpl();
		String token = runAndFun.createToken("", "", "", "");
		assertNotNull(token);
	}

}
