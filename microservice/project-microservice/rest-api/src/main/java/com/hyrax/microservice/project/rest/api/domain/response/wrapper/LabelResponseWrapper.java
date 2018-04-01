package com.hyrax.microservice.project.rest.api.domain.response.wrapper;

import com.hyrax.microservice.project.rest.api.domain.response.LabelResponse;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class LabelResponseWrapper {

    private final List<LabelResponse> labelResponses;
}
