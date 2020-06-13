package net.inpercima.runandfun.runkeeper.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RunkeeperFriends {

    private int size = 0;

    private RunkeeperFriendItem[] items = new RunkeeperFriendItem[0];

    private String next;

    public List<RunkeeperFriendItem> getItemsAsList() {
        return items != null ? Arrays.asList(items) : new ArrayList<>();
    }
}
