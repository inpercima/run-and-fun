package net.inpercima.runandfun.app.model;

import java.util.Date;

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

    public static final String FIELD_TYPE = "type";

    public static final String FIELD_DATE = "date";

    public static final String FIELD_DISTANCE = "distance";

    @Id
    private final String id;

    private final String username;

    @Field(type = FieldType.Keyword)
    private final String type;

    @Field(type = FieldType.Date, format = DateFormat.basic_date_time_no_millis)
    private final Date date;

    @Field(type = FieldType.Float)
    private final double distance;

    @Field(type = FieldType.Float)
    private final double duration;

    public AppActivity(final String id, final String username, final String type, final Date date,
            final double distance, final double duration) {
        this.id = id;
        this.username = username;
        this.type = type;
        this.date = date;
        this.distance = distance;
        this.duration = duration;
    }
}
