package net.inpercima.runandfun.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RunkeeperItem {

    public static final String ID_PREFIX = "/fitnessActivities/";

    protected static final String GMT_POSTFIX = " GMT";

    protected static final String TYPE_RUNNING = "Running";

    protected static final String TYPE_CYCLING = "Cycling";

    protected static final String TYPE_HIKING = "Hiking";

    private static final DateTimeFormatter DATE_FORMAT = new DateTimeFormatterBuilder().appendPattern("HH:mm:ss")
            .toFormatter();

    private String type;

    private String uri;

    @JsonProperty(value = "start_time")
    private String startTime;

    @JsonProperty(value = "utc_offset")
    private int utcOffset;

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
        sb.append(getDate());
        sb.append("\", \"distance\" : \"");
        sb.append(getTotalDistance() != null ? getTotalDistance().doubleValue() / 1000 : 0);
        sb.append("\", \"duration\" : \"");
        sb.append(convertSecondToHHMMSS(getDuration() != null ? getDuration().intValue() : 0));
        sb.append("\" }");
        return sb.toString();
    }

    public String getId() {
        return getUri() != null ? getUri().substring(ID_PREFIX.length()) : null;
    }

    public LocalDate getDate() {
        return getStartTime() != null ? parseRfc1123DateTime(getStartTime() + GMT_POSTFIX) : null;
    }

    public static LocalDate parseRfc1123DateTime(final String date) {
        return LocalDate.parse(date, DateTimeFormatter.RFC_1123_DATE_TIME);
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

    public int getUtcOffset() {
        return utcOffset;
    }

    public void setUtcOffset(final int utcOffset) {
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
