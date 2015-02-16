package net.inpercima.runandfun.service;

import javax.servlet.http.HttpSession;

import net.inpercima.runandfun.model.AppState;
import net.inpercima.runandfun.model.RunkeeperActivities;
import net.inpercima.runandfun.model.RunkeeperProfile;
import net.inpercima.runandfun.model.RunkeeperUser;

/**
 * @author Marcel Jänicke
 * @since 26.01.2015
 */
public interface RunAndFunService {

    String getAccessToken(String code);

    RunkeeperUser getUser(String accessToken);

    RunkeeperProfile getProfile(String accessToken);
    
    RunkeeperActivities getActivities(String accessToken);

    AppState getAppState(HttpSession session);

}
