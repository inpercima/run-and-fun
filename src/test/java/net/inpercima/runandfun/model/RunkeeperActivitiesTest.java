package net.inpercima.runandfun.model;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class RunkeeperActivitiesTest {

    public static final int ACTIVITY_SIZE = 4;

    private static final String START_TIME = "Thu, 1 Jan 2015 14:20:59";

    private RunkeeperActivities activities;

    @Before
    public void setup() {
        activities = createActivities();
    }

    public static RunkeeperActivities createActivities() {
        final RunkeeperActivities result = new RunkeeperActivities();
        assertEquals(0, result.getItems().length);

        final RunkeeperItem[] items = new RunkeeperItem[ACTIVITY_SIZE];
        int i = 0;
        items[i] = new RunkeeperItem();
        items[i].setType(RunkeeperItem.TYPE_RUNNING);
        items[i].setUri(RunkeeperItem.ID_PREFIX + i);
        items[i].setStartTime(START_TIME);
        items[++i] = new RunkeeperItem();
        items[i].setType(RunkeeperItem.TYPE_RUNNING);
        items[i].setUri(RunkeeperItem.ID_PREFIX + i);
        items[i].setStartTime(START_TIME);
        items[++i] = new RunkeeperItem();
        items[i].setType(RunkeeperItem.TYPE_HIKING);
        items[i].setUri(RunkeeperItem.ID_PREFIX + i);
        items[i].setStartTime(START_TIME);
        items[++i] = new RunkeeperItem();
        items[i].setType(RunkeeperItem.TYPE_CYCLING);
        items[i].setUri(RunkeeperItem.ID_PREFIX + i);
        items[i].setStartTime(START_TIME);
        assertEquals(ACTIVITY_SIZE, i + 1);
        result.setItems(items);
        assertArrayEquals(items, result.getItems());
        assertEquals(ACTIVITY_SIZE, result.getItems().length);
        return result;
    }

    @Test
    public final void getRuns() {
        final List<RunkeeperItem> runs = activities.getRuns();
        assertNotNull(runs);
        assertEquals(2, runs.size());
    }

    @Test
    public final void getRides() {
        final List<RunkeeperItem> rides = activities.getRides();
        assertNotNull(rides);
        assertEquals(1, rides.size());
    }

    @Test
    public void parseRfc1123DateTime() {
        final LocalDate date = RunkeeperItem.parseRfc1123DateTime(START_TIME + RunkeeperItem.GMT_POSTFIX);
        assertNotNull(date);
        assertEquals("2015-01-01", date.toString());
    }

    @Test
    public void convertSecondToHHMMSS() {
        final String duration = RunkeeperItem.convertSecondToHHMMSS(0);
        assertNotNull(duration);
        assertEquals("00:00:00", duration);
        assertEquals("00:00:01", RunkeeperItem.convertSecondToHHMMSS(1));
        assertEquals("00:00:11", RunkeeperItem.convertSecondToHHMMSS(11));
        assertEquals("00:01:00", RunkeeperItem.convertSecondToHHMMSS(60));
        assertEquals("01:00:00", RunkeeperItem.convertSecondToHHMMSS(3600));
        assertEquals("01:01:01", RunkeeperItem.convertSecondToHHMMSS(3661));
    }

}
