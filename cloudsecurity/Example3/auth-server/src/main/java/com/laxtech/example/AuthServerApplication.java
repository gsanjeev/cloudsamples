package com.laxtech.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

//OAuth2 Auth Server - auth-server (also enabled as resource?), OAuth2 Client Postman and browser,
// OAuth2 Resource Server - product-api-service, product-service is core service which is not exposed through Zuul
// There is no role of gateway zuul in OAuth2 in general but here zuul filtering the requests and client can only access
// resource server authenticated apis if only Zuul gateway is accessible to client. With the help of property (sensitiveHeaders) Zuul passes authentication header
// as is to resource server apis. Spring MVC inject authentication header and principal parameter in resource api method call
// step1: get authorization code(optional) from auth-server.
// step2: get token from auth-server.
// step3: access api using token from product-api-service (resource server).
// internal step: resource server contact auth-server for token verification and get the user detail using userInfoUri
//reference http://callistaenterprise.se/blogg/teknik/2015/04/27/building-microservices-part-3-secure-APIs-with-OAuth/
//question? why there is separate layer for resource server why can't annotate product-service as resource service.

@SpringBootApplication
@RestController
@EnableResourceServer
@EnableAuthorizationServer
public class AuthServerApplication {

    @RequestMapping("/user")
    public Principal user(Principal user) {
        return user;
    }

    public static void main(String[] args) {
        SpringApplication.run(AuthServerApplication.class, args);
    }


}/*    @Configuration
    @EnableAuthorizationServer
    protected static class OAuth2Config extends AuthorizationServerConfigurerAdapter {

        @Autowired
        private AuthenticationManager authenticationManager;

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
            endpoints.authenticationManager(authenticationManager);
        }

        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            clients.inMemory()
                    .withClient("acme")
                    .secret("acmesecret")
                    .authorizedGrantTypes("authorization_code", "refresh_token", "implicit", "password", "client_credentials")
                    .scopes("webshop");
        }
    }*/

