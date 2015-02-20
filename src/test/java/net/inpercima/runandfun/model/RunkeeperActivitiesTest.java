package net.inpercima.runandfun.model;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;

public class RunkeeperActivitiesTest {

    private final RunkeeperActivities activities = new RunkeeperActivities();

    @Test
    public final void getRuns() {
        final List<RunkeeperItem> runs = activities.getRuns();
        assertNotNull(runs);
        assertEquals(0, runs.size());

        final RunkeeperItem[] items = new RunkeeperItem[3];
        items[0] = new RunkeeperItem();
        items[0].setType(RunkeeperActivities.TYPE_RUNNING);
        items[1] = new RunkeeperItem();
        items[1].setType(RunkeeperActivities.TYPE_RUNNING);
        items[2] = new RunkeeperItem();
        items[2].setType("foo");
        activities.setItems(items);

        assertArrayEquals(items, activities.getItems());
        assertEquals(2, activities.getRuns().size());
    }

}
