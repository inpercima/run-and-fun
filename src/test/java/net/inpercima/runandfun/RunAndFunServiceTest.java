package net.inpercima.runandfun;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;

import net.inpercima.runandfun.service.RunAndFunService;
import net.inpercima.runandfun.service.RunAndFunServiceImpl;

import org.junit.Ignore;
import org.junit.Test;

/**
 * @author Marcel Jänicke
 * @since 26.01.2015
 */
public class RunAndFunServiceTest {

    @Test
    @Ignore
    public void test() throws UnsupportedEncodingException, MalformedURLException, IOException {
        RunAndFunService runAndFun = new RunAndFunServiceImpl();
        String token = runAndFun.getAccessToken("");
        assertNotNull(token);
    }

}
