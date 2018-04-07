package com.hyrax.microservice.project.rest.api.domain.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TeamResponse {

    private final String teamName;

    private final String description;

    private final String ownerUsername;
}
