package net.inpercima.runandfun.runkeeper.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Marcel Jänicke
 * @since 11.02.2015
 */
@Getter
@Setter
public class RunkeeperToken {

    @JsonProperty(value = "access_token")
    private String accessToken;
}
