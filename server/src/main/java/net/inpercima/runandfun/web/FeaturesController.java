package net.inpercima.runandfun.web;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import net.inpercima.runandfun.app.constants.AppConstants;
import net.inpercima.runandfun.runkeeper.model.RunkeeperFriendItem;
import net.inpercima.runandfun.runkeeper.model.RunkeeperProfile;
import net.inpercima.runandfun.service.FeaturesService;

/**
 * @author Marcel Jänicke
 * @author Sebastian Peters
 * @since 02.02.2015
 */
@RestController
public class FeaturesController {

    @Inject
    private FeaturesService featuresService;

    @GetMapping(value = "/friends")
    public List<RunkeeperFriendItem> listFriends(final HttpSession session) {
        return featuresService.listFriends((String) session.getAttribute(AppConstants.SESSION_ACCESS_TOKEN));
    }

    @GetMapping(value = "/profile")
    public RunkeeperProfile getProfile(final HttpSession session) {
        return featuresService.getProfile((String) session.getAttribute(AppConstants.SESSION_ACCESS_TOKEN));
    }
}
