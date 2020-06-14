package net.inpercima.runandfun.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchHits;

import net.inpercima.runandfun.app.model.AppActivity;
import net.inpercima.runandfun.app.model.AppState;
import net.inpercima.runandfun.runkeeper.model.RunkeeperFriendItem;
import net.inpercima.runandfun.runkeeper.model.RunkeeperProfile;
import net.inpercima.runandfun.runkeeper.model.RunkeeperUser;

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

    AppActivity getLastActivity();

    Page<AppActivity> listAllActivitiesByType(String type);

    SearchHits<AppActivity> listActivities(Pageable pageable, String type, LocalDate minDate, LocalDate maxDate,
            Float minDistance, Float maxDistance, String query);

    AppState getAppState(String accessToken);

    void deAuthorize(String accessToken);
}
