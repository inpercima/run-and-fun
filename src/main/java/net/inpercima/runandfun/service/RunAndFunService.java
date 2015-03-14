package net.inpercima.runandfun.service;

import javax.servlet.http.HttpSession;

import net.inpercima.runandfun.model.Activity;
import net.inpercima.runandfun.model.AppState;
import net.inpercima.runandfun.model.RunkeeperActivities;
import net.inpercima.runandfun.model.RunkeeperItem;
import net.inpercima.runandfun.model.RunkeeperProfile;
import net.inpercima.runandfun.model.RunkeeperUser;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author Marcel Jänicke
 * @since 26.01.2015
 */
public interface RunAndFunService {

    String getAccessToken(String code);

    RunkeeperUser getUser(String accessToken);

    RunkeeperProfile getProfile(String accessToken);

    RunkeeperActivities getActivities(String accessToken);

    void indexActivities(Iterable<RunkeeperItem> runkeeperItems);

    Page<Activity> listActivities(Pageable pageable, String query, Float minDistance, Float maxDistance);

    AppState getAppState(HttpSession session);

    void setAccessTokenToSession(HttpSession session, String accessToken);

    void logout(HttpSession session);

    void deAuthorize(HttpSession session);

}
