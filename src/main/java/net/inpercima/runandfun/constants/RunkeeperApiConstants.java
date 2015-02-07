package net.inpercima.runandfun.constants;

import java.nio.charset.StandardCharsets;

/**
 * @author Marcel Jänicke
 * @since 05.02.2015
 */
public class RunkeeperApiConstants {

    // urls
    public final static String TOKEN_URL = "https://runkeeper.com/apps/token";

    public final static String AUTHORIZATION_URL = "https://runkeeper.com/apps/authorize";

    public final static String DE_AUTHORIZATION_URL = "https://runkeeper.com/apps/de-authorize";

    public final static String USER_URL = "http://api.runkeeper.com/user";

    public final static String PROFILE_URL = "http://api.runkeeper.com/profile";

    // queries
    public final static String GRANT_AUTHORIZATION_QUERY = "grant_type=%s&code=%s&client_id=%s&client_secret=%s&redirect_uri=%s";

    public final static String REQUEST_QUERY = "access_token=%s";

    // parameter values
    public final static String AUTHORIZATION_CODE = "authorization_code";

    public final static String BEARER = "Bearer ";

    // parameter keys
    public final static String CONTENT_TYPE = "Content-Type";

    public final static String ACCEPT = "Accept";

    public final static String AUTHORIZATION = "Authorization";

    // methods
    public final static String POST_METHOD = "POST";

    public final static String GET_METHOD = "GET";

    // application types
    public final static String URLENCODED_APP = "application/x-www-form-urlencoded";

    public final static String USER_APP = "application/vnd.com.runkeeper.User+json";

    public final static String PROFILE_APP = "application/vnd.com.runkeeper.Profile+json";

    // misc
    public final static String UTF_8 = StandardCharsets.UTF_8.displayName();

    private RunkeeperApiConstants() {
        // util class
    }

}
