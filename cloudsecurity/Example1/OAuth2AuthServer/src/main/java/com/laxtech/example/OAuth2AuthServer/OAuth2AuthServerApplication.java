package com.laxtech.example.OAuth2AuthServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//Explanation at http://stytex.de/blog/2016/02/01/spring-cloud-security-with-oauth2/
@SpringBootApplication
public class OAuth2AuthServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(OAuth2AuthServerApplication.class, args);
	}
}
