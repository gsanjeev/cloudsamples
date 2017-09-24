package com.laxtech.example.cloudsecurity.controller;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller that serves the current time at:
 *
 *     http://localhost:8081/time
 */
@RestController
public class TimeRestController {

    // Only users with the role ROLE_ADMIN or ROLE_EXTERNAL_USER are allowed to retrieve the time
    @RequestMapping(value = "/time")
    @PreAuthorize("hasAnyRole('ADMIN', 'EXTERNAL_USER')")
    public String currentTime() {
        return LocalTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME);
    }
}
