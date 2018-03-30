package com.hyrax.microservice.project.rest.api.domain.response.wrapper;

import com.hyrax.microservice.project.rest.api.domain.response.ColumnResponse;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ColumnResponseWrapper {

    private final List<ColumnResponse> columnResponses;
}
