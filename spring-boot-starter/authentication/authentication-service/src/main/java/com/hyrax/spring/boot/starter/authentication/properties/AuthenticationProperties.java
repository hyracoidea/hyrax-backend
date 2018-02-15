package com.hyrax.spring.boot.starter.authentication.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "authentication")
public class AuthenticationProperties {

    private String signUpUrl;
    private String headerName;
    private String jwtTokenPrefix;
    private String jwtSecretKey;

}
