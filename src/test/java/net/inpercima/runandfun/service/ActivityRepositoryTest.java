package net.inpercima.runandfun.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import net.inpercima.runandfun.Application;
import net.inpercima.runandfun.model.Activity;
import net.inpercima.runandfun.model.RunkeeperActivityItem;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class ActivityRepositoryTest {

    @Autowired
    ActivityRepository repository;

    @Test
    public final void findAllOrderByDateDesc() {
        final Pageable pageable = new PageRequest(0, 1, Direction.DESC, Activity.FIELD_DATE);
        final Page<Activity> page = repository.findAll(pageable);
        final List<Activity> content = page.getContent();
        assertEquals(1, content.size());
    }

    @Test
    public void countAndfindAllByType() {
        final int count = repository.countByType(RunkeeperActivityItem.TYPE_RUNNING);
        final Pageable pageable = new PageRequest(0, count);
        final Page<Activity> page = repository.findAllByTypeOrderByDateDesc(RunkeeperActivityItem.TYPE_RUNNING, pageable);
        assertNotNull(page);
        final List<Activity> content = page.getContent();
        assertEquals(count, content.size());
        content.stream().forEach(result -> {
            assertEquals(RunkeeperActivityItem.TYPE_RUNNING, result.getType());
        });
    }

    @Test
    public void countAndfindAllByTypeNull() {
        final long count = repository.count();
        final Pageable pageable = new PageRequest(0, (int) count);
        final Page<Activity> page = repository.findAllByTypeOrderByDateDesc(null, pageable);
        assertNotNull(page);
        final List<Activity> content = page.getContent();
        assertEquals(count, content.size());
    }
}
