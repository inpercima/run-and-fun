package net.inpercima.runandfun.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.junit4.SpringRunner;

import net.inpercima.runandfun.app.model.AppActivity;
import net.inpercima.runandfun.runkeeper.model.RunkeeperActivityItem;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ActivityRepositoryTest {

    @Inject
    ActivityRepository repository;

    @Test
    public final void findAllOrderByDateDesc() {
        final Pageable pageable = PageRequest.of(0, 1, Direction.DESC, AppActivity.FIELD_DATE);
        final Page<AppActivity> page = repository.findAll(pageable);
        final List<AppActivity> content = page.getContent();
        assertThat(content.size()).isEqualTo(1);
    }

    @Test
    public void countAndfindAllByType() {
        final int count = repository.countByType(RunkeeperActivityItem.TYPE_RUNNING);
        final Pageable pageable = PageRequest.of(0, count);
        final Page<AppActivity> page = repository.findAllByTypeOrderByDateDesc(RunkeeperActivityItem.TYPE_RUNNING,
                pageable);
        assertThat(page).isNotNull();
        final List<AppActivity> content = page.getContent();
        assertThat(content.size()).isEqualTo(count);
        content.stream().forEach(result -> {
            assertThat(result.getType()).isEqualTo(RunkeeperActivityItem.TYPE_RUNNING);
        });
    }

    @Test
    public void countAndFindAllByTypeNull() {
        final long count = repository.count();
        final Pageable pageable = PageRequest.of(0, (int) count);
        final Page<AppActivity> page = repository.findAllByTypeOrderByDateDesc(null, pageable);
        assertThat(page).isNotNull();
        final List<AppActivity> content = page.getContent();
        assertThat(content.size()).isEqualTo(count);
    }
}
