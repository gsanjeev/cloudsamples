package com.laxtech.example.config;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoRestTemplateFactory;
import org.springframework.context.annotation.Bean;
        import org.springframework.context.annotation.Configuration;
        import org.springframework.security.config.annotation.web.builders.HttpSecurity;
        import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
        import org.springframework.security.oauth2.client.OAuth2ClientContext;
        import org.springframework.security.oauth2.client.OAuth2RestOperations;
        import org.springframework.security.oauth2.client.OAuth2RestTemplate;
        import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;

/**
 * The Security configuration for Token Relay.
 */
@Configuration
public class SecurityConfigurer {

    @Bean
    public OAuth2RestTemplate restTemplate(UserInfoRestTemplateFactory factory) {
        return factory.getUserInfoRestTemplate();
    }
}



