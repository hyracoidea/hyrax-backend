package com.hyrax.microservice.project.rest.api.domain.response.wrapper;

import com.hyrax.microservice.project.rest.api.domain.response.TeamResponse;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TeamResponseWrapper {

    private final List<TeamResponse> teamResponses;
}
