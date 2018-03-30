package com.hyrax.microservice.project.rest.api.domain.response.wrapper;

import com.hyrax.microservice.project.rest.api.domain.response.BoardResponse;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BoardResponseWrapper {

    private final List<BoardResponse> boardResponses;
}
