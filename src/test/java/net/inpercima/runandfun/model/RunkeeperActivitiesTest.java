package net.inpercima.runandfun.model;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class RunkeeperActivitiesTest {

    public static final int ACTIVITY_SIZE = 4;

    private static final String START_TIME = "14:20:59";

    private static final String START_DATETIME = "Thu, 1 Jan 2015 " + START_TIME;

    private RunkeeperActivities activities;

    @Before
    public void setup() {
        activities = createActivities();
    }

    public static RunkeeperActivities createActivities() {
        final RunkeeperActivities result = new RunkeeperActivities();
        assertEquals(0, result.getItems().length);

        final RunkeeperActivityItem[] items = new RunkeeperActivityItem[ACTIVITY_SIZE];
        int i = 0;
        items[i] = new RunkeeperActivityItem();
        items[i].setType(RunkeeperActivityItem.TYPE_RUNNING);
        items[i].setUri(RunkeeperActivityItem.ID_PREFIX + i);
        items[i].setStartTime(START_DATETIME);
        items[++i] = new RunkeeperActivityItem();
        items[i].setType(RunkeeperActivityItem.TYPE_RUNNING);
        items[i].setUri(RunkeeperActivityItem.ID_PREFIX + i);
        items[i].setStartTime(START_DATETIME);
        items[++i] = new RunkeeperActivityItem();
        items[i].setType(RunkeeperActivityItem.TYPE_HIKING);
        items[i].setUri(RunkeeperActivityItem.ID_PREFIX + i);
        items[i].setStartTime(START_DATETIME);
        items[++i] = new RunkeeperActivityItem();
        items[i].setType(RunkeeperActivityItem.TYPE_CYCLING);
        items[i].setUri(RunkeeperActivityItem.ID_PREFIX + i);
        items[i].setStartTime(START_DATETIME);
        assertEquals(ACTIVITY_SIZE, i + 1);
        result.setItems(items);
        assertArrayEquals(items, result.getItems());
        assertEquals(ACTIVITY_SIZE, result.getItems().length);
        return result;
    }

    @Test
    public final void getRuns() {
        final List<RunkeeperActivityItem> runs = activities.getRuns();
        assertNotNull(runs);
        assertEquals(2, runs.size());
    }

    @Test
    public void parseRfc1123DateTime() {
        final LocalDateTime date = RunkeeperActivityItem.parseRfc1123DateTime(START_DATETIME + RunkeeperActivityItem.GMT_POSTFIX);
        assertNotNull(date);
        assertEquals("2015-01-01T" + START_TIME, date.toString());
    }

    @Test
    public void getDate() {
        assertThat(activities.getItems()[0].getDate().toString(), containsString(START_TIME));
    }

    @Test
    public void convertSecondToHHMMSS() {
        final String duration = RunkeeperActivityItem.convertSecondToHHMMSS(0);
        assertNotNull(duration);
        assertEquals("00:00:00", duration);
        assertEquals("00:00:01", RunkeeperActivityItem.convertSecondToHHMMSS(1));
        assertEquals("00:00:11", RunkeeperActivityItem.convertSecondToHHMMSS(11));
        assertEquals("00:01:00", RunkeeperActivityItem.convertSecondToHHMMSS(60));
        assertEquals("01:00:00", RunkeeperActivityItem.convertSecondToHHMMSS(3600));
        assertEquals("01:01:01", RunkeeperActivityItem.convertSecondToHHMMSS(3661));
    }

}
