package net.inpercima.runandfun.service;

import static net.inpercima.runandfun.runkeeper.constants.RunkeeperConstants.FRIENDS_MEDIA;
import static net.inpercima.runandfun.runkeeper.constants.RunkeeperConstants.FRIENDS_URL_PAGE_SIZE_ONE;
import static net.inpercima.runandfun.runkeeper.constants.RunkeeperConstants.FRIENDS_URL_SPECIFIED_PAGE_SIZE;
import static net.inpercima.runandfun.runkeeper.constants.RunkeeperConstants.PROFILE_MEDIA;
import static net.inpercima.runandfun.runkeeper.constants.RunkeeperConstants.PROFILE_URL;

import java.util.List;

import javax.inject.Inject;

import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.inpercima.restapi.service.RestApiService;
import net.inpercima.runandfun.runkeeper.model.RunkeeperFriendItem;
import net.inpercima.runandfun.runkeeper.model.RunkeeperFriends;
import net.inpercima.runandfun.runkeeper.model.RunkeeperProfile;

/**
 * @author Marcel Jänicke
 * @author Sebastian Peters
 * @since 26.01.2015
 */
@NoArgsConstructor
@Service
@Slf4j
public class FeaturesService {

    @Inject
    private RestApiService restApiService;

    public List<RunkeeperFriendItem> listFriends(String accessToken) {
        log.debug("list friends for token {}", accessToken);

        final HttpEntity<RunkeeperFriends> friendsForSize = restApiService.getForObject(FRIENDS_URL_PAGE_SIZE_ONE,
                FRIENDS_MEDIA, accessToken, RunkeeperFriends.class);
        final int pageSize = friendsForSize.getBody().getSize();

        return restApiService.getForObject(String.format(FRIENDS_URL_SPECIFIED_PAGE_SIZE, pageSize), FRIENDS_MEDIA,
                accessToken, RunkeeperFriends.class).getBody().getItemsAsList();
    }

    public RunkeeperProfile getProfile(final String accessToken) {
        log.debug("get profile for token {}", accessToken);

        return restApiService.getForObject(PROFILE_URL, PROFILE_MEDIA, accessToken, RunkeeperProfile.class).getBody();
    }
}
