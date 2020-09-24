package net.inpercima.runandfun.runkeeper.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.apache.commons.lang3.StringUtils;

import lombok.Data;

@Data
public class RunkeeperActivityItem {

    public static final String SEPARATOR_ID = "fitnessActivities/";

    public static final String GMT_POSTFIX = " GMT";

    public static final String TYPE_RUNNING = "Running";

    public static final String TYPE_CYCLING = "Cycling";

    public static final String TYPE_HIKING = "Hiking";

    private String type;

    private String uri;

    @JsonProperty(value = "start_time")
    private String startTime;

    @JsonProperty(value = "utc_offset")
    private Integer utcOffset;

    @JsonProperty(value = "total_distance")
    private Double totalDistance;

    private Double duration;

    @JsonProperty(value = "total_calories")
    private Double totalCalories;

    public String getId() {
        return getUri() != null ? StringUtils.substringAfter(getUri(), SEPARATOR_ID) : null;
    }

    public LocalDateTime getDate() {
        return getStartTime() != null ? parseRfc1123DateTime(getStartTime() + GMT_POSTFIX) : null;
    }

    public static LocalDateTime parseRfc1123DateTime(final String date) {
        return LocalDateTime.parse(date, DateTimeFormatter.RFC_1123_DATE_TIME);
    }

    public double getDistance() {
        return getTotalDistance() != null ? getTotalDistance().doubleValue() / 1000 : 0;
    }
}
