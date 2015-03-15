package net.inpercima.runandfun.model;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Mapping;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Sebastian Peters
 */
@Document(indexName = "activity", type = "activity")
@Mapping(mappingPath = "mapping.json")
public class Activity {

    public static final String FIELD_ID = "id";

    public static final String FIELD_TYPE = "type";

    public static final String FIELD_DATE = "date";

    public static final String FIELD_DISTANCE = "distance";

    public static final String FIELD_DURATION = "duration";

    @Id
    private final String id;

    private final String type;

    private final Date date;

    private final double distance;

    private final String duration;

    @JsonCreator
    public Activity(@JsonProperty(value = FIELD_ID) final String id,
            @JsonProperty(value = FIELD_TYPE) final String type, @JsonProperty(value = FIELD_DATE) final Date date,
            @JsonProperty(value = FIELD_DISTANCE) final double distance,
            @JsonProperty(value = FIELD_DURATION) final String duration) {
        this.id = id;
        this.type = type;
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

    public String getType() {
        return type;
    }

    public Date getDate() {
        return date;
    }

    public double getDistance() {
        return distance;
    }

    public String getDuration() {
        return duration;
    }

}
