package net.inpercima.runandfun.runkeeper.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RunkeeperFriendItem {

    private String reply;

    private String name;

    private String profile;

    private String uri;

    @JsonProperty(value = "userID")
    private String userId;
}
