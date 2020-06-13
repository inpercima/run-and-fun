package net.inpercima.runandfun.runkeeper.model;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Marcel Jänicke
 * @since 11.02.2015
 */
@Getter
@Setter
public class RunkeeperProfile {

    private static final String SEPARATOR_USER = "user/";

    private String name;

    @JsonProperty(value = "athlete_type")
    private String athleteType;

    private String location;

    private String birthday;

    private boolean elite;

    private String gender;

    private String profile;

    @JsonProperty(value = "small_picture")
    private String smallPicture;

    public String getUsername() {
        return StringUtils.substringAfter(getProfile(), SEPARATOR_USER);
    }
}
