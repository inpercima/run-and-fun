package net.inpercima.runandfun.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RunkeeperFriendItem {

    private String name;

    private String profile;

    private String url;

    @JsonProperty(value = "userID")
    private String userId;

    @Override
    public String toString() {
        return toJson();
    }

    public String toJson() {
        final StringBuilder sb = new StringBuilder("{ ");
        sb.append("\"name\" : \"");
        sb.append(getName());
        sb.append("\", \"profile\" : \"");
        sb.append(getProfile());
        sb.append("\", \"url\" : \"");
        sb.append(getUrl());
        sb.append("\", \"userID\" : \"");
        sb.append(getUserId());
        sb.append("\" }");
        return sb.toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
