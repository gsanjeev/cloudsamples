package com.laxtech.example.cloudsecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/*
*******************************Example 2************************************************************************************************************
This example uses authorization_code grant type and Spring MVC client(my-website) not the test clients like browser for authentication code and postman.
Authentication code, and access token requests from my-website are taken care by following
    i)WebSecurityConfigurerAdapter implementation is annotated with @EnableOAuth2Sso
    ii) Authorization Server and User Info properties in application.yml. These properties configure my-website to use the URLs provided by my-auth-server
Three main part of example
    i) client - my-website client application which uses spring mvc and spring security ii) resource server - /auth/user resource. ResourceServerConfiguration uses @EnableResourceServer annotation
    iii) authentication server - AuthorizationServerConfigurer uses @EnableAuthorizationServer annotation. This uses Spring MVC to display user pages. iv) users are are user and admin.
    Spring MVC is secured with Spring Security
Remember OAuth2 does not provide authentication. We are using Spring Security for this. OAuth2 is delegation protocol. Third party does not share credentials with client(my-website).
They share credentials with Authentication Provider (like spring security with memory/rdbm/ldap). OAuth2 facilitate this.
Login page and user consensus page resides in auth-server application not in client application.
reference: https://blog.jdriven.com/2016/09/securing-application-landscape-spring-cloud-security-part-1/
and https://bitbucket.org/rlippolis/cloud-security-example.git tag secure-website
*******************************Example 4 (this) ************************************************************************************************************
**tag -- secure-website-and-rest-api -- Make sure My Website passes the OAuth2 access token to My REST API when fetching the time******************************
New application, my-rest-api is resource server annotated with @EnableResourceServer which is using Spring Security(@EnableWebSecurity) and OAuth2 protocol(application.yml)
OAuth2RestTemplate in my-website application is passing user and access token from OAuth2ClientContext to my-rest-api. This is Single Sign On(SSO) Configuration. In example 3 we didn't pass
access token to other application. In fact we manually passed access token from postman to resource server api.
 */
@SpringBootApplication
public class MyAuthServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyAuthServerApplication.class, args);
	}
}
