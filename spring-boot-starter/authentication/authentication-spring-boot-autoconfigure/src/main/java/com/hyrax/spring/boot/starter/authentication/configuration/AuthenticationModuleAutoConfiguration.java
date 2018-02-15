package com.hyrax.spring.boot.starter.authentication.configuration;

import com.hyrax.spring.boot.starter.authentication.properties.AuthenticationAdminJWTProperties;
import com.hyrax.spring.boot.starter.authentication.properties.AuthenticationAdminProperties;
import com.hyrax.spring.boot.starter.authentication.properties.AuthenticationProperties;
import com.hyrax.spring.boot.starter.authentication.properties.AuthenticationUserJWTProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableConfigurationProperties(
        {AuthenticationProperties.class, AuthenticationAdminProperties.class, AuthenticationAdminJWTProperties.class, AuthenticationUserJWTProperties.class}
)
@Import({AuthenticationModuleConfiguration.class, WebSecurityConfiguration.class})
public class AuthenticationModuleAutoConfiguration {
}
