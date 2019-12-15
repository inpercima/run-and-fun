package net.inpercima.runandfun.runkeeper.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RunkeeperFriendItem {
    
    private String reply;

    private String name;

    private String profile;

    private String uri;

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
        sb.append("\", \"uri\" : \"");
        sb.append(getUri());
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

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

}
