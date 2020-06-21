package net.inpercima.runandfun.service;

import static org.assertj.core.api.Assertions.assertThat;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import net.inpercima.runandfun.app.model.AppActivity;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ActivityRepositoryTest {

    @Inject
    ActivityRepository repository;

    @Test
    public final void findTopByOrderByDateDesc() {
        final AppActivity activity = repository.findTopByOrderByDateDesc();
        assertThat(activity).isNotNull();
    }
}
