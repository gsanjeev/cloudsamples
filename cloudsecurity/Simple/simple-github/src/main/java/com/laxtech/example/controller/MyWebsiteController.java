package com.laxtech.example.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
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

    @Value("${user.url}")
    private String userUrl;


//    @Autowired
//    private RestOperations restOperations;

    private static final Logger LOG = LoggerFactory.getLogger(MyWebsiteController.class);
    /**
     * Default index page to verify that our application works.
     */
/*    @RequestMapping("/")
    @ResponseBody
    public String helloWorld() {
        return "Hello world!";
    }*/

    /**
     * Default index page to verify that our application works.
     */
    @RequestMapping("/")
    @ResponseBody
    public ModelAndView helloWorld(Principal principal, Authentication authentication) {
        LOG.info("helloWorld(): start.");
        ModelAndView mav = new ModelAndView("index");

/*        LOG.info("helloWorld(): userUrl={}", userUrl);
        OAuth2Authentication auth2Authentication1 = (OAuth2Authentication)restOperations.getForObject(userUrl, Principal.class);
        LOG.info("helloWorld(): auth2Authentication1={}", auth2Authentication1);
        RestTemplate restTemplate = new RestTemplate();
        Principal principal1 =restTemplate.getForObject(userUrl, Principal.class);
        OAuth2Authentication auth2Authentication = (OAuth2Authentication)principal1;
        LOG.info("helloWorld(): principal1={}", principal1);*/
/*        if (authentication!=null) {
            mav.addObject("user", authentication.getName());
            mav.addObject("isAuthenticated", authentication.isAuthenticated());
            LOG.info("helloWorld(): Auth={}", authentication.getName());//log for testing only.
            LOG.info("helloWorld(): Auth={}", authentication);
        }*/
        OAuth2Authentication auth2Authentication = (OAuth2Authentication)principal;

        if (auth2Authentication!=null) {
            //OAuth2Authentication auth2Authentication = (OAuth2Authentication) principal;
            mav.addObject("user", auth2Authentication.getUserAuthentication().getName());
            mav.addObject("isAuthenticated", auth2Authentication.isAuthenticated());
            LOG.info("helloWorld(): principal={}", auth2Authentication.getUserAuthentication().getName());
            LOG.info("helloWorld(): principal={}", auth2Authentication.isAuthenticated());

        }
        LOG.info("helloWorld(): end.");
        return mav;
    }
    /**
     * Return a ModelAndView which will cause the 'src/main/resources/templates/time.html' templates to be rendered,
     * with the current time.
     */
    @RequestMapping("/time")
    public ModelAndView time(Principal principal) {
        ModelAndView mav = new ModelAndView("time");
        mav.addObject("currentTime", getCurrentTime());
        mav.addObject("user", principal.getName());
        return mav;
    }

    private String getCurrentTime() {
        return LocalTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME);
    }
}
