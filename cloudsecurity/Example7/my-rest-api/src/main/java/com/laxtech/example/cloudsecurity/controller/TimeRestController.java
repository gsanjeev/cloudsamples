package com.laxtech.example.cloudsecurity.controller;

import java.security.Principal;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller that serves the current time at:
 *
 *     http://localhost:8081/time
 */
@RestController
public class TimeRestController {
    private static final Logger LOG = LoggerFactory.getLogger(TimeRestController.class);

    // Only users with the role ROLE_ADMIN or ROLE_EXTERNAL_USER are allowed to retrieve the time
    @RequestMapping(value = "/time")
    @PreAuthorize("hasRole('ADMIN')")
    public String currentTime(@RequestHeader(value="Authorization") String authorizationHeader,
                              Principal currentUser) {
        String time = LocalTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME);
        LOG.info("currentTime api: User={}, Auth={}, current time={}", currentUser.getName(), authorizationHeader, time);//log for testing only.

        return time;
    }
}
