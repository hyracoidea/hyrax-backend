package com.hyrax.microservice.project.rest.api.domain.response.wrapper;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TeamMemberUsernameWrapperResponse {

    private final List<String> teamMemberUsernames;
}
