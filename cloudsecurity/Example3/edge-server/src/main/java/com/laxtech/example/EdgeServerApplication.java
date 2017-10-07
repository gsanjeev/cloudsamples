package com.laxtech.example;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.stereotype.Controller;

@SpringBootApplication
@Controller
@EnableZuulProxy
public class EdgeServerApplication {

	public static void main(String[] args) {
		//SpringApplication.run(EdgeServerApplication.class, args);

		new SpringApplicationBuilder(EdgeServerApplication.class).web(true).run(args);
	}
}

