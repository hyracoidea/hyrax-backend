package com.hyrax.microservice.project.rest.api.domain.response.wrapper;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BoardMemberResponseWrapper {

    private final String ownerUsername;

    private final List<String> boardMemberUsernames;
}
