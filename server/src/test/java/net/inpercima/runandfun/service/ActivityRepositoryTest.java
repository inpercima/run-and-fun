package net.inpercima.runandfun.service;

import static org.assertj.core.api.Assertions.assertThat;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import net.inpercima.runandfun.app.model.AppActivity;

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
