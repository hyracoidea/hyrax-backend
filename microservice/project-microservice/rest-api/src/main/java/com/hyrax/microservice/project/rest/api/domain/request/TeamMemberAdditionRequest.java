package com.hyrax.microservice.project.rest.api.domain.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TeamMemberAdditionRequest {

    private final String username;

    private final String teamName;
}
