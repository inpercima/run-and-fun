package net.inpercima.runandfun.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RunkeeperFriends {

    private int size = 0;

    private RunkeeperFriendItem[] items = new RunkeeperFriendItem[0];

    private String next;

    public List<RunkeeperFriendItem> getItemsAsList() {
        return items != null ? Arrays.asList(items) : new ArrayList<>();
    }

    public int getSize() {
        return size;
    }

    public void setSize(final int size) {
        this.size = size;
    }

    public RunkeeperFriendItem[] getItems() {
        return items;
    }

    public void setItems(final RunkeeperFriendItem[] items) {
        this.items = items;
    }

    public String getNext() {
        return next;
    }

    public void setNext(final String next) {
        this.next = next;
    }

}
