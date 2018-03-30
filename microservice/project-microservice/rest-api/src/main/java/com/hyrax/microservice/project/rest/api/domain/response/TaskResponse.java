package com.hyrax.microservice.project.rest.api.domain.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaskResponse {

    private final Long taskId;

    private final String taskName;

    private final String description;

    private final Long taskIndex;

}
