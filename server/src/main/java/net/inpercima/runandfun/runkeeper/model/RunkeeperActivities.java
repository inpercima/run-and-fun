package net.inpercima.runandfun.runkeeper.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;

@Data
public class RunkeeperActivities {

    private int size = 0;

    private RunkeeperActivityItem[] items = new RunkeeperActivityItem[0];

    private String previous;

    private String next;

    public List<RunkeeperActivityItem> getItemsAsList() {
        return items != null ? Arrays.asList(items) : new ArrayList<>();
    }

    public List<RunkeeperActivityItem> getRuns() {
        return getItemsAsList().stream().filter(item -> item.getType().equals(RunkeeperActivityItem.TYPE_RUNNING))
                .collect(Collectors.toList());
    }
}
