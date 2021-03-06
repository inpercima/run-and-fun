package net.inpercima.runandfun.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import net.inpercima.restapi.service.RestApiService;
import net.inpercima.runandfun.runkeeper.model.RunkeeperToken;

/**
 * @author Marcel Jänicke
 * @author Sebastian Peters
 * @since 26.01.2015
 */
@SpringBootTest
public class AuthServiceTest {

    @Inject
    private AuthService authService;

    @MockBean
    private RestApiService restApiService;

    @Test
    public void getAccessTokenWithEmptyCodeShouldReturnNull() {
        final RunkeeperToken runkeeperToken = new RunkeeperToken();
        runkeeperToken.setAccessToken(null);
        Mockito.when(restApiService.postForObject(anyString(), any(), any())).thenReturn(runkeeperToken);

        assertThat(authService.getAccessToken("")).isNull();
        assertThat(authService.getAccessToken(null)).isNull();
        assertThat(authService.getAccessToken("invalid")).isNull();
    }
}
