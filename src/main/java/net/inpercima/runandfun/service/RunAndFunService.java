package net.inpercima.runandfun.service;

import java.time.LocalDate;

import net.inpercima.runandfun.model.Activity;
import net.inpercima.runandfun.model.AppState;
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

    void indexActivities(String accessToken);

    Page<Activity> listActivities(Pageable pageable, String type, LocalDate minDate, LocalDate maxDate,
            Float minDistance, Float maxDistance, String query);

    AppState getAppState(String accessToken);

    void deAuthorize(String accessToken);
}
