package com.laxtech.example.OAuth2ResourceServer.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

/**
 * Created by on 01.02.16.
 *
 * @author David Steiman
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class GlobalMethodSecurityConfiguration {
}
