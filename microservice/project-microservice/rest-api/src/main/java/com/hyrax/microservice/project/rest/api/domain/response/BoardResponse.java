package com.hyrax.microservice.project.rest.api.domain.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BoardResponse {

    private final String boardName;

    private final String ownerUsername;
}
