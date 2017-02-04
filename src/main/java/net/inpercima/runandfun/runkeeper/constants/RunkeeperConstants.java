package net.inpercima.runandfun.runkeeper.constants;

/**
 * @author Marcel Jänicke
 * @since 05.02.2015
 */
public interface RunkeeperConstants {

    // request parameters
    public final static String ACCEPT = "Accept";

    public final static String ACCESS_TOKEN = "access_token";

    public final static String AUTHORIZATION = "Authorization";

    public final static String AUTHORIZATION_BASIC = "Basic ";

    public final static String AUTHORIZATION_BEARER = "Bearer ";

    public final static String AUTHORIZATION_CODE = "authorization_code";

    public final static String CLIENT_ID = "client_id";

    public final static String CLIENT_SECRET = "client_secret";

    public final static String CODE = "code";

    public final static String GRANT_TYPE = "grant_type";

    public final static String REDIRECT_URI = "redirect_uri";

    // media types
    public final static String ACTIVITIES_MEDIA = "application/vnd.com.runkeeper.FitnessActivityFeed+json";

    public final static String FRIENDS_MEDIA = "application/vnd.com.runkeeper.TeamFeed+json";

    public final static String PROFILE_MEDIA = "application/vnd.com.runkeeper.Profile+json";

    public final static String ROOT_MEDIA = "application/vnd.com.runkeeper.Root+json";

    public final static String USER_MEDIA = "application/vnd.com.runkeeper.User+json";

    // urls
    public final static String ACTIVITIES_URL = "http://api.runkeeper.com/fitnessActivities";

    public final static String AUTHORIZATION_URL = "https://runkeeper.com/apps/authorize";

    public final static String DE_AUTHORIZATION_URL = "https://runkeeper.com/apps/de-authorize";

    public final static String FRIENDS_URL = "http://api.runkeeper.com/team";

    public final static String PROFILE_URL = "http://api.runkeeper.com/profile";

    public final static String TOKEN_URL = "https://runkeeper.com/apps/token";

    public final static String USER_URL = "http://api.runkeeper.com/user";

    // url parameters
    public final static String NO_EARLIER_THAN = "&noEarlierThan=%s";

    public final static String PAGE_SIZE_ONE = "?pageSize=1";

    public final static String SPECIFIED_PAGE_SIZE = "?pageSize=%s";

    // concatenated urls
    public final static String ACTIVITIES_URL_PAGE_SIZE_ONE = ACTIVITIES_URL + PAGE_SIZE_ONE;

    public final static String ACTIVITIES_URL_SPECIFIED = ACTIVITIES_URL + SPECIFIED_PAGE_SIZE + NO_EARLIER_THAN;

    public final static String ACTIVITIES_URL_SPECIFIED_PAGE_SIZE = ACTIVITIES_URL + SPECIFIED_PAGE_SIZE;

    public final static String FRIENDS_URL_PAGE_SIZE_ONE = FRIENDS_URL + PAGE_SIZE_ONE;

    public final static String FRIENDS_URL_SPECIFIED_PAGE_SIZE = FRIENDS_URL + SPECIFIED_PAGE_SIZE;

}
