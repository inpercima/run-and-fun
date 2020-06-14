package net.inpercima.runandfun.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import net.inpercima.restapi.service.RestApiService;
import net.inpercima.runandfun.runkeeper.model.RunkeeperToken;

/**
 * @author Marcel Jänicke
 * @author Sebastian Peters
 * @since 26.01.2015
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RunAndFunServiceTest {

    @Inject
    private RunAndFunService runAndFunService;

    @MockBean
    private RestApiService restApiService;

    @Test
    public void getAccessTokenWithEmptyCodeShouldReturnNull() {
        final RunkeeperToken runkeeperToken = new RunkeeperToken();
        runkeeperToken.setAccessToken(null);
        Mockito.when(restApiService.postForObject(anyString(), any(), any())).thenReturn(runkeeperToken);

        assertThat(runAndFunService.getAccessToken("")).isNull();
        assertThat(runAndFunService.getAccessToken(null)).isNull();
        assertThat(runAndFunService.getAccessToken("invalid")).isNull();
    }
}
