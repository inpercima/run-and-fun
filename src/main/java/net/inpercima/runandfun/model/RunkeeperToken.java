package net.inpercima.runandfun.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Marcel Jänicke
 * @since 11.02.2015
 */
public class RunkeeperToken {
    
    @JsonProperty(value = "access_token")
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccess_token(String accessToken) {
        this.accessToken = accessToken;
    }

}
