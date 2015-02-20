package net.inpercima.runandfun.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RunkeeperActivities {

    static final String TYPE_RUNNING = "Running";

    private int size = 0;

    private RunkeeperItem[] items = new RunkeeperItem[0];

    private String previous;

    private String next;

    public List<RunkeeperItem> getItemsAsList() {
        return items != null ? Arrays.asList(items) : new ArrayList<>();
    }

    public List<RunkeeperItem> getRuns() {
        return getItemsAsList().stream().filter(item -> item.getType().equals(TYPE_RUNNING))
                .collect(Collectors.toList());
    }

    public int getSize() {
        return size;
    }

    public void setSize(final int size) {
        this.size = size;
    }

    public RunkeeperItem[] getItems() {
        return items;
    }

    public void setItems(final RunkeeperItem[] items) {
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
