package com.hyrax.spring.boot.starter.authentication.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "authentication.user.jwt")
public class AuthenticationUserJWTProperties {

    private Long expirationTimeInMinutes;
}
