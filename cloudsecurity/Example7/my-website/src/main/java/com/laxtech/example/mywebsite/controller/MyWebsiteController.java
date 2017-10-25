package com.laxtech.example.mywebsite.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestOperations;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Web MVC Controller serving two pages:
 *
 * - http://localhost:8080/ -> Shows 'Hello world!'
 * - http://localhost:8080/time -> Shows a page with the current time
 */
@Controller
public class MyWebsiteController {

    @Value("${time.url}")
    private String timeUrl;

    private static final Logger LOG = LoggerFactory.getLogger(MyWebsiteController.class);


    // Inject the RestOperations. This will be an instance of org.springframework.security.oauth2.client.OAuth2RestTemplate
    // which is made available as a Bean by our WebSecurityConfigurer.
    @Autowired
    private RestOperations restOperations;

    /**
     * Default index page to verify that our application works.
     */
    @RequestMapping("/")
    @ResponseBody
    public String helloWorld() {
        return "Hello world!";
    }

    /**
     * Return a ModelAndView which will cause the 'src/main/resources/templates/time.html' template to be rendered,
     * with the current time.
     */
    @RequestMapping("/time")
    public ModelAndView time() {
        ModelAndView mav = new ModelAndView("time");
        mav.addObject("currentTime", getCurrentTime());
        return mav;
    }

    /**
     * Get the time from an external REST API using the injected RestOperations.
     */
    private String getCurrentTime() {
        try {
            LOG.info("getCurrentTime(): Auth={}", ((OAuth2RestOperations)restOperations).getAccessToken());//log for testing only.

            return restOperations.getForObject(timeUrl, String.class);
            //return LocalTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME);
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
