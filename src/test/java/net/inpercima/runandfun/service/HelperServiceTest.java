package net.inpercima.runandfun.service;

import static net.inpercima.runandfun.runkeeper.constants.RunkeeperConstants.ROOT_MEDIA;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.inpercima.runandfun.Application;
import net.inpercima.runandfun.runkeeper.model.RunkeeperRoot;

/**
 * @author Marcel Jänicke
 * @since 05.01.2017
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class HelperServiceTest {

    private final HelperService helperService = new HelperServiceImpl();

    @Value("${app.clientId}")
    private String clientId;

    @Value("${app.clientSecret}")
    private String clientSecret;

    @Test
    public void getForObject() {
        helperService.setClientId(clientId);
        helperService.setClientSecret(clientSecret);
        // modifiedNoEarlierThan=2017-01-05T00:00:00, day date
        final String url = "https://api.runkeeper.com/?modifiedNoEarlierThan=" + LocalDate.now() + "T00:00:00";
        final RunkeeperRoot runkeeperRoot = helperService.getForObject(url, ROOT_MEDIA, RunkeeperRoot.class).getBody();
        assertNotNull(runkeeperRoot.getChangeLogs());
    }

}
