package com.laxtech.example.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class MyApiController {

	private static final Logger LOG = LoggerFactory.getLogger(MyWebsiteController.class);

	@RequestMapping("/user")
	public Principal user(Principal principal) {

		LOG.info("user(): principal1={}", principal);

		return principal;
	}
}