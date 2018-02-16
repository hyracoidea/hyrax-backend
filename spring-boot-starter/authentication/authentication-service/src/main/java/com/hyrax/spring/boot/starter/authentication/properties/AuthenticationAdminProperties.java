package com.hyrax.spring.boot.starter.authentication.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "authentication.admin")
public class AuthenticationAdminProperties {

    private String username;
    private String password;
    private String authority;
    private String token;
}
