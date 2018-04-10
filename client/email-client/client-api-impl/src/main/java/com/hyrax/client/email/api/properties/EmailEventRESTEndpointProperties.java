package com.hyrax.client.email.api.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "email.event.rest.endpoint")
public class EmailEventRESTEndpointProperties {

    private String pathToBoard;

    private String pathToColumn;

    private String pathToLabel;

    private String pathToTask;

    private String pathToTeam;

    private String pathToSendEmail;
}
