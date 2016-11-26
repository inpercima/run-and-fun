package net.inpercima.runandfun.runkeeper.model;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RunkeeperActivityItem {

    public static final String SEPARATOR_ID = "fitnessActivities/";

    public static final String GMT_POSTFIX = " GMT";

    public static final String TYPE_RUNNING = "Running";

    public static final String TYPE_CYCLING = "Cycling";

    public static final String TYPE_HIKING = "Hiking";

    private static final DateTimeFormatter DATE_FORMAT = new DateTimeFormatterBuilder().appendPattern("HH:mm:ss")
            .toFormatter();

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

    @Override
    public String toString() {
        return toJson();
    }

    public String toJson() {
        final StringBuilder sb = new StringBuilder("{ ");
        sb.append("\"date\" : \"");
        sb.append(getLocalDateTime());
        sb.append("\", \"distance\" : \"");
        sb.append(getDistance());
        sb.append("\", \"duration\" : \"");
        sb.append(getFormattedDuration());
        sb.append("\" }");
        return sb.toString();
    }

    public String getId() {
        return getUri() != null ? StringUtils.substringAfter(getUri(), SEPARATOR_ID) : null;
    }

    public LocalDateTime getLocalDateTime() {
        return getStartTime() != null ? parseRfc1123DateTime(getStartTime() + GMT_POSTFIX) : null;
    }

    public Date getDate() {
        return Date.from(getLocalDateTime().atZone(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDateTime parseRfc1123DateTime(final String date) {
        return LocalDateTime.parse(date, DateTimeFormatter.RFC_1123_DATE_TIME);
    }

    public double getDistance() {
        return getTotalDistance() != null ? getTotalDistance().doubleValue() / 1000 : 0;
    }

    public String getFormattedDuration() {
        return convertSecondToHHMMSS(getDuration() != null ? getDuration().intValue() : 0);
    }

    public static String convertSecondToHHMMSS(final int nSecondTime) {
        return LocalTime.MIN.plusSeconds(nSecondTime).format(DATE_FORMAT);
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(final String activity) {
        this.uri = activity;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(final String startTime) {
        this.startTime = startTime;
    }

    public Integer getUtcOffset() {
        return utcOffset;
    }

    public void setUtcOffset(final Integer utcOffset) {
        this.utcOffset = utcOffset;
    }

    public Double getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(final Double totalDistance) {
        this.totalDistance = totalDistance;
    }

    public Double getDuration() {
        return duration;
    }

    public void setDuration(final Double duration) {
        this.duration = duration;
    }

    public Double getTotalCalories() {
        return totalCalories;
    }

    public void setTotalCalories(final Double totalCalories) {
        this.totalCalories = totalCalories;
    }

}
