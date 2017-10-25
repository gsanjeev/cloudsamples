package com.laxtech.example.server.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class MyApiController {

	private static final Logger LOG = LoggerFactory.getLogger(MyWebsiteController.class);

	@RequestMapping({ "/user", "/me" })
	public Map<String, String> user(Principal principal) {
		LOG.info("user(): principal={}", principal);
		Map<String, String> map = new LinkedHashMap<>();
		map.put("name", principal.getName());
		LOG.info("user(): principal.name={}", principal.getName());
		return map;
	}
}