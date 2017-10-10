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
// use url http://localhost:9999/uaa/oauth/authorize?response_type=code&client_id=acme&redirect_uri=http://example.com&scope=webshop& state=97536
// step2: get token from auth-server.
//  i) use case: grant type = authorization code. Send post request with basic authentication (user = acme and password= acmesecret) , content type application/x-www-form-urlencoded and set following field in body
//      grant_type=authorization_code
//      client_id=acme
//      redirect_uri=http://example.com
//      code=code received in step 1.
//  ii) use case: grant type = implicit use url http://localhost:9999/uaa/oauth/authorize?response_type=token&client_id=acme&redirect_uri=http://example.com&scope=webshop&state=48532
//  iii) use case: grant type = password. Send post request with basic authentication (user = acme and password= acmesecret) , content type application/x-www-form-urlencoded and set following field in body
//      grant_type=password
//      client_id=acme
//      scope=webshop
//      username=user
//      password=password
//  iv) use case: grant type = client_credentials. Send post request with basic authentication (user = acme and password= acmesecret), content type application/x-www-form-urlencoded and set following field in body
//      grant_type=client_credentials
//      scope=webshop
// step3: access api using token from product-api-service (resource server) through zuul gate. Send get request to http://localhost:8765/api/product/123
// and set Authorization header to "Bearer token received in above step"  under Headers tab in postman(ex: Bearer 3d34258a-2aa3-481c-86a3-60a362447941).
// internal step: resource server contact auth-server for token verification and get the user detail using userInfoUri=http://localhost:9999/uaa/user
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

