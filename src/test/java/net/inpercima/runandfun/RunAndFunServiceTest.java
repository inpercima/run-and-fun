package net.inpercima.runandfun;

import static org.junit.Assert.assertNull;
import net.inpercima.runandfun.service.HelperServiceImpl;
import net.inpercima.runandfun.service.RunAndFunService;
import net.inpercima.runandfun.service.RunAndFunServiceImpl;

import org.junit.Test;
import org.springframework.web.client.RestClientException;

/**
 * @author Marcel Jänicke
 * @author Sebastian Peters
 * @since 26.01.2015
 */
public class RunAndFunServiceTest {

    private final RunAndFunService runAndFun = new RunAndFunServiceImpl(new HelperServiceImpl());

    @Test
    public void getAccessTokenWithEmptyCodeShouldReturnNull() {
        assertNull(runAndFun.getAccessToken(""));
        assertNull(runAndFun.getAccessToken(null));
    }

    @Test(expected = RestClientException.class)
    public void getAccessTokenWithInvalidCodeShouldReturnException() {
        runAndFun.getAccessToken("invalid");
    }
}
