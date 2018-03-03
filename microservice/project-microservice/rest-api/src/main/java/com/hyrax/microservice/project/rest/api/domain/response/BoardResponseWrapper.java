package com.hyrax.microservice.project.rest.api.domain.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BoardResponseWrapper {

    private final List<BoardResponse> boardResponses;
}
