package net.inpercima.runandfun.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    public int getSize() {
        return size;
    }

    public void setSize(final int size) {
        this.size = size;
    }

    public RunkeeperActivityItem[] getItems() {
        return items;
    }

    public void setItems(final RunkeeperActivityItem[] items) {
        this.items = items;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(final String previous) {
        this.previous = previous;
    }

    public String getNext() {
        return next;
    }

    public void setNext(final String next) {
        this.next = next;
    }

}
