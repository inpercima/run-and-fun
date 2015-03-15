package net.inpercima.runandfun.web;

import java.time.LocalDate;

import javax.servlet.http.HttpSession;

import net.inpercima.runandfun.model.Activity;
import net.inpercima.runandfun.model.AppState;
import net.inpercima.runandfun.service.RunAndFunService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Marcel Jänicke
 * @author Sebastian Peters
 * @since 02.02.2015
 */
@RestController
public class RestApiController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestApiController.class);

    @Autowired
    private RunAndFunService runAndFunService;

    @RequestMapping(value = "/state", method = RequestMethod.GET)
    public AppState state(final HttpSession session) {
        return runAndFunService.getAppState(session);
    }

    @RequestMapping(value = "/listActivities", method = RequestMethod.GET)
    @ResponseBody
    public Page<Activity> listActivities(
            @PageableDefault(direction = Direction.DESC, sort = "date") final Pageable pageable,
            @RequestParam(required = false) final String type,
            @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE) final LocalDate minDate,
            @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE) final LocalDate maxDate,
            @RequestParam(required = false) final Float minDistance,
            @RequestParam(required = false) final Float maxDistance, @RequestParam(required = false) final String query) {
        LOGGER.info("listActivities of type '{}' for date between {} - {}, distance between {} - {}, {}", type,
                minDate, maxDate, minDistance, maxDistance, pageable);
        return runAndFunService.listActivities(pageable, type, minDate, maxDate, minDistance, maxDistance, query);
    }

}
