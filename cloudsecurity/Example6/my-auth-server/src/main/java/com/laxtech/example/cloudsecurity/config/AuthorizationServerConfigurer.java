package com.laxtech.example.cloudsecurity.config;

import java.security.KeyPair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

/**
 * Our configuration for the OAuth2 Authorization Server.
 */
@Configuration
@EnableAuthorizationServer
// The Resource Server configuration creates a security filter with '@Order(3)' by default,
// so by moving the authorization server to '@Order(6)' we ensure that the rule for '/user' takes precedence.
@Order(6)
public class AuthorizationServerConfigurer extends AuthorizationServerConfigurerAdapter {

    @Value("${jwt.certificate.store.file}")
    private Resource keystore;

    @Value("${jwt.certificate.store.password}")
    private String keystorePassword;

    @Value("${jwt.certificate.key.alias}")
    private String keyAlias;

    @Value("${jwt.certificate.key.password}")
    private String keyPassword;

    @Autowired
    private UserDetailsService userDetailsService;

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
                .autoApprove(true)
                // Make the access tokens short-lived, to force token refreshing every 30 seconds
                .accessTokenValiditySeconds(30)
                // Allow refresh of the access tokens for 30 minutes (session duration)
                .refreshTokenValiditySeconds(1800);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .accessTokenConverter(jwtAccessTokenConverter())
                .userDetailsService(userDetailsService);
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(keystore, keystorePassword.toCharArray());
        KeyPair keyPair = keyStoreKeyFactory.getKeyPair(keyAlias, keyPassword.toCharArray());
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setKeyPair(keyPair);
        return converter;
    }
/*
    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.accessTokenConverter(jwtAccessTokenConverter())
                .authenticationManager(authenticationManager);
    } this code also works*/


}
