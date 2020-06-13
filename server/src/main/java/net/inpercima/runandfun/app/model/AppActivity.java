package net.inpercima.runandfun.app.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.Data;

/**
 * @author Sebastian Peters
 */
@Data
@Document(indexName = "activity")
public class AppActivity {

    public static final String FIELD_ID = "id";

    public static final String FIELD_USERNAME = "username";

    public static final String FIELD_TYPE = "type";

    public static final String FIELD_DATE = "date";

    public static final String FIELD_DISTANCE = "distance";

    public static final String FIELD_DURATION = "duration";

    @Id
    private final String id;

    private final String username;

    @Field(type = FieldType.Text)
    private final String type;

    @Field(type = FieldType.Date, format = DateFormat.basic_date)
    private final Date date;

    @Field(type = FieldType.Float)
    private final double distance;

    @Field(type = FieldType.Text)
    private final String duration;

    @JsonCreator
    public AppActivity(@JsonProperty(value = FIELD_ID) final String id, @JsonProperty(value = FIELD_USERNAME) final String username,
            @JsonProperty(value = FIELD_TYPE) final String type, @JsonProperty(value = FIELD_DATE) final Date date,
            @JsonProperty(value = FIELD_DISTANCE) final double distance, @JsonProperty(value = FIELD_DURATION) final String duration) {
        this.id = id;
        this.username = username;
        this.type = type;
        this.date = date;
        this.distance = distance;
        this.duration = duration;
    }
}
