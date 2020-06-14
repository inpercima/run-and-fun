package net.inpercima.runandfun.app.model;

import lombok.Data;

@Data
public class AppState {

    private String clientId;

    private String redirectUri;

    private String accessToken;

    private String username;

    private String fullName;
}
