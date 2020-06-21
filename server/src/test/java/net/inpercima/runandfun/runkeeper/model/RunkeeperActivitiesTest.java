package net.inpercima.runandfun.runkeeper.model;

import static org.assertj.core.api.Assertions.assertThat;

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
        assertThat(result.getItems().length).isZero();

        final RunkeeperActivityItem[] items = new RunkeeperActivityItem[ACTIVITY_SIZE];
        int i = 0;
        items[i] = new RunkeeperActivityItem();
        items[i].setType(RunkeeperActivityItem.TYPE_RUNNING);
        items[i].setUri(RunkeeperActivityItem.SEPARATOR_ID + i);
        items[i].setStartTime(START_DATETIME);
        items[++i] = new RunkeeperActivityItem();
        items[i].setType(RunkeeperActivityItem.TYPE_RUNNING);
        items[i].setUri(RunkeeperActivityItem.SEPARATOR_ID + i);
        items[i].setStartTime(START_DATETIME);
        items[++i] = new RunkeeperActivityItem();
        items[i].setType(RunkeeperActivityItem.TYPE_HIKING);
        items[i].setUri(RunkeeperActivityItem.SEPARATOR_ID + i);
        items[i].setStartTime(START_DATETIME);
        items[++i] = new RunkeeperActivityItem();
        items[i].setType(RunkeeperActivityItem.TYPE_CYCLING);
        items[i].setUri(RunkeeperActivityItem.SEPARATOR_ID + i);
        items[i].setStartTime(START_DATETIME);
        assertThat(i + 1).isEqualTo(ACTIVITY_SIZE);
        result.setItems(items);
        assertThat(result.getItems()).isEqualTo(items);
        assertThat(result.getItems().length).isEqualTo(ACTIVITY_SIZE);
        return result;
    }

    @Test
    public final void getRuns() {
        final List<RunkeeperActivityItem> runs = activities.getRuns();
        assertThat(runs).isNotNull();
        assertThat(runs.size()).isEqualTo(2);
    }

    @Test
    public void parseRfc1123DateTime() {
        final LocalDateTime date = RunkeeperActivityItem
                .parseRfc1123DateTime(START_DATETIME + RunkeeperActivityItem.GMT_POSTFIX);
        assertThat(date).isNotNull();
        assertThat(date.toString()).isEqualTo("2015-01-01T" + START_TIME);
    }

    @Test
    public void getDate() {
        assertThat(activities.getItems()[0].getDate().toString()).contains(START_TIME);
    }
}
