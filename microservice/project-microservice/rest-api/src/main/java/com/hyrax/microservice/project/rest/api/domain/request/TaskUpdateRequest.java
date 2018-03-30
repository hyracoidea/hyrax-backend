package com.hyrax.microservice.project.rest.api.domain.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaskUpdateRequest {

    private final String taskName;

    private final String description;
}
