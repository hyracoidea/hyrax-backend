package com.hyrax.microservice.project.rest.api.domain.request;

import com.hyrax.microservice.project.rest.api.validation.annotation.TeamName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TeamCreationRequest {

    @TeamName
    private final String name;

    private final String description;
}
