package net.inpercima.runandfun.service;

import javax.servlet.http.HttpSession;

import net.inpercima.runandfun.model.AppState;
import net.inpercima.runandfun.model.RunkeeperProfile;
import net.inpercima.runandfun.model.RunkeeperUser;

/**
 * @author Marcel Jänicke
 * @since 26.01.2015
 */
public interface RunAndFunService {

    String getAccessToken(String code);

    RunkeeperUser getUserData(String accessToken);

    RunkeeperProfile getProfileData(String accessToken);

    AppState getAppState(HttpSession session);

}
