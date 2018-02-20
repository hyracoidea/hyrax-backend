package com.hyrax.client.account.api.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "account.rest.client")
public class AccountRESTClientProperties {

    private String headerName;
    private String token;

    private String serviceUrl;
    private String path;
    private String usernamesEndpoint;
}
