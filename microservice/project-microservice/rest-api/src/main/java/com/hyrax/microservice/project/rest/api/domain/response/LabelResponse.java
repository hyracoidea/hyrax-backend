package com.hyrax.microservice.project.rest.api.domain.response;

import com.hyrax.microservice.project.service.domain.LabelColor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LabelResponse {

    private final Long labelId;

    private final String labelName;

    private final LabelColor labelColor;
}
