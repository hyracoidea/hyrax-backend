package com.hyrax.microservice.project.rest.api.domain.response;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class SingleTaskResponse {

    private final Long taskId;

    private final String taskName;

    private final String description;

    private final String assignedUsername;

    private final Set<LabelResponse> labels;

    private final Set<String> watchedUsers;
}
