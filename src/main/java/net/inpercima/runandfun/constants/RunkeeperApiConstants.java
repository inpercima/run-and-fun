package net.inpercima.runandfun.constants;

/**
 * @author Marcel Jänicke
 * @since 05.02.2015
 */
public interface RunkeeperApiConstants {

    // urls
    public final static String TOKEN_URL = "https://runkeeper.com/apps/token";

    public final static String AUTHORIZATION_URL = "https://runkeeper.com/apps/authorize";

    public final static String DE_AUTHORIZATION_URL = "https://runkeeper.com/apps/de-authorize";

    public final static String USER_URL = "http://api.runkeeper.com/user";

    public final static String PROFILE_URL = "http://api.runkeeper.com/profile";

    public final static String ACTIVITIES_URL = "http://api.runkeeper.com/fitnessActivities";

    // parameter keys
    public final static String ACCEPT = "Accept";

    public final static String ACCEPT_CHARSET = "Accept-Charset";

    public final static String AUTHORIZATION = "Authorization";

    public final static String CLIENT_ID = "client_id";

    public final static String CLIENT_SECRET = "client_secret";

    public final static String GRANT_TYPE = "grant_type";

    public final static String REDIRECT_URI = "redirect_uri";

    public final static String ACCESS_TOKEN = "access_token";

    public final static String CODE = "code";

    public final static String PAGE_SIZE = "?pageSize=";

    // parameter values
    public final static String AUTHORIZATION_CODE = "authorization_code";

    public final static String BEARER = "Bearer ";

    // application types
    public final static String USER_APP = "application/vnd.com.runkeeper.User+json";

    public final static String PROFILE_APP = "application/vnd.com.runkeeper.Profile+json";

    public final static String ACTIVITIES_APP = "application/vnd.com.runkeeper.FitnessActivityFeed+json";

}
