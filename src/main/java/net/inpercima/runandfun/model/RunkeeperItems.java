package net.inpercima.runandfun.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RunkeeperItems {

    private String type;

    @JsonProperty(value = "start_time")
    private String startTime;

    @JsonProperty(value = "utc_offset")
    private int utcOffset;

    @JsonProperty(value = "total_distance")
    private Double totalDistance;

    private Double duration;

    @JsonProperty(value = "total_calories")
    private Double totalCalories;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public int getUtcOffset() {
        return utcOffset;
    }

    public void setUtcOffset(int utcOffset) {
        this.utcOffset = utcOffset;
    }

    public Double getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(Double totalDistance) {
        this.totalDistance = totalDistance;
    }

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public Double getTotalCalories() {
        return totalCalories;
    }

    public void setTotalCalories(Double totalCalories) {
        this.totalCalories = totalCalories;
    }

}
