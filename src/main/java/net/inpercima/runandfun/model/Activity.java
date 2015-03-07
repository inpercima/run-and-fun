package net.inpercima.runandfun.model;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Mapping;

/**
 * @author Sebastian Peters
 */
@Document(indexName = "activity", type = "activity")
@Mapping(mappingPath = "mapping.json")
public class Activity {

    @Id
    private String id;

    private Date date;

    private double distance;

    private String duration;

    public Activity() {
    }

    public Activity(final String id, final Date date, final double distance, final String duration) {
        this.id = id;
        this.date = date;
        this.distance = distance;
        this.duration = duration;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(final Date date) {
        this.date = date;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(final double distance) {
        this.distance = distance;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(final String duration) {
        this.duration = duration;
    }

}
