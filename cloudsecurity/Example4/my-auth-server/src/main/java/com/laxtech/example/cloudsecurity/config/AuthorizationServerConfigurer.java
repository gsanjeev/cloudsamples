package com.laxtech.example.cloudsecurity.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

/**
 * Our configuration for the OAuth2 Authorization Server.
 */
@Configuration
@EnableAuthorizationServer
// The Resource Server configuration creates a security filter with '@Order(3)' by default,
// so by moving the authorization server to '@Order(6)' we ensure that the rule for '/user' takes precedence.
@Order(6)
public class AuthorizationServerConfigurer extends AuthorizationServerConfigurerAdapter {

    /**
     * We configure the client details in-memory. Alternatively, we could fetch this information from a database.
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                // Credentials for the client: myauthserver / verysecretpassword
                .withClient("myauthserver")
                .secret("verysecretpassword")
                // Only allow redirecting to 'http://localhost:8080/**' when logging in
                .redirectUris("http://localhost:8080/")
                // We only use the authorization_code grant type, with support for refresh tokens
                .authorizedGrantTypes("authorization_code", "refresh_token")
                // We can define our own scopes here
                .scopes("myscope")
                // Auto approve the required scopes.
                // If we do not auto approve, the user is asked upon login if (s)he approves
                .autoApprove(true);
    }

}

