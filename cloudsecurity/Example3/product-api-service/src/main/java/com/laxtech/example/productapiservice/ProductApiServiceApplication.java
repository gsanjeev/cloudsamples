package com.laxtech.example.productapiservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableOAuth2Resource
public class ProductApiServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductApiServiceApplication.class, args);
	}
}
