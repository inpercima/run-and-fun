package net.inpercima.runandfun.service;

import static org.junit.Assert.assertNull;

import org.junit.Test;

import net.inpercima.restapi.service.RestApiService;

/**
 * @author Marcel Jänicke
 * @author Sebastian Peters
 * @since 26.01.2015
 */
public class RunAndFunServiceTest {

    private final RunAndFunService runAndFun = new RunAndFunServiceImpl(new RestApiService());

    @Test
    public void getAccessTokenWithEmptyCodeShouldReturnNull() {
        assertNull(runAndFun.getAccessToken(""));
        assertNull(runAndFun.getAccessToken(null));
        assertNull(runAndFun.getAccessToken("invalid"));
    }

}
