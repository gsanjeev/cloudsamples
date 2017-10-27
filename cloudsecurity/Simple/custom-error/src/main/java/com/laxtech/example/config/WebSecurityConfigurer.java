package com.laxtech.example.config;

import com.laxtech.example.controller.MyWebsiteController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import java.util.List;
import java.util.Map;

/**
 * The Web Security configuration for My Website.
 */
@Configuration
@EnableOAuth2Sso
@Order(3)
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {

    private static final Logger LOG = LoggerFactory.getLogger(MyWebsiteController.class);

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.antMatcher("/**").authorizeRequests().antMatchers("/", "/login**", "/webjars/**").permitAll().anyRequest()
//                .authenticated();
        // @formatter:off
        http.antMatcher("/**").authorizeRequests().antMatchers("/", "/login**", "/webjars/**").permitAll().anyRequest()
                .authenticated().and().logout().logoutSuccessUrl("/").permitAll().and().csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
        // @formatter:on
    }


    @Bean
    public OAuth2RestTemplate oauth2RestTemplate(OAuth2ProtectedResourceDetails resource, OAuth2ClientContext context) {
        return new OAuth2RestTemplate(resource, context);
    }

/*    @Bean
    public AuthoritiesExtractor authoritiesExtractor(OAuth2RestOperations template) {
        return map -> {
            String url = (String) map.get("organizations_url");
            LOG.info("authoritiesExtractor(): url={}", url);
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> orgs = template.getForObject(url, List.class);
            LOG.info("authoritiesExtractor(): orgs={}", orgs);
            if (orgs.stream()
                    .anyMatch(org -> "spring-projects".equals(org.get("login")))) {
                LOG.info("authoritiesExtractor(): orgs={}", orgs);
                LOG.info("authoritiesExtractor(): AuthorityUtils.commaSeparatedStringToAuthorityList()={}", AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER"));
                return AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");
            }
            throw new BadCredentialsException("Not in Spring Projects origanization");
        };
    }*/

}

