package net.inpercima.runandfun.service;

/**
 * @author Marcel Jänicke
 * @since 26.01.2015
 */
public interface RunAndFunService {

    String getAccessToken(String code);

    String getUserData(String accessToken);

    String getProfileData(String accessToken);

    void loadProperties();

}
