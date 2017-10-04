package com.laxtech.example.edgeserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy
//Simplest Gateway example explains how Zuul can route to request to different microservices
// path could be mapped to either url or serviceid
// From browser call http://localhost:8079/api/server/rest/hello/server
//And http://localhost:8079/api/client/rest/hello/client

public class EdgeServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EdgeServerApplication.class, args);
	}
}
