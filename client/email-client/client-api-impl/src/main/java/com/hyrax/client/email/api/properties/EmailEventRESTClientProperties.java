package com.hyrax.client.email.api.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "email.event.rest.client")
public class EmailEventRESTClientProperties {

    private String headerName;
    private String token;

    private String serviceUrl;
}
