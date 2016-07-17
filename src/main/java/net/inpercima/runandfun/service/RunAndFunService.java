package net.inpercima.runandfun.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import net.inpercima.runandfun.model.Activity;
import net.inpercima.runandfun.model.AppState;
import net.inpercima.runandfun.model.RunkeeperFriendItem;
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
    
    List<RunkeeperFriendItem> getFriends(String accessToken);

    int indexActivities(String accessToken);

    Page<Activity> listAllActivitiesByType(String type);

    Page<Activity> listActivities(Pageable pageable, String type, LocalDate minDate, LocalDate maxDate, Float minDistance,
            Float maxDistance, String query);

    AppState getAppState(String accessToken);

    void deAuthorize(String accessToken);
}
