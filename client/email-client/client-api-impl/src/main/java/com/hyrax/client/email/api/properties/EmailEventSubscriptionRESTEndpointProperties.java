package com.hyrax.client.email.api.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "email.event.subscription.rest.endpoint")
public class EmailEventSubscriptionRESTEndpointProperties {

    private String pathToBoard;

    private String pathToColumn;

    private String pathToLabel;

    private String pathToTask;

    private String pathToTeam;
}
