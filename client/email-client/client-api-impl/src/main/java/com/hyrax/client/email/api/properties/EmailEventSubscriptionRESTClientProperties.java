package com.hyrax.client.email.api.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "email.event.subscription.rest.client")
public class EmailEventSubscriptionRESTClientProperties {

    private String headerName;
    private String token;

    private String serviceUrl;
}
