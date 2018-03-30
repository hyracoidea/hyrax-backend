package com.hyrax.microservice.project.rest.api.domain.response.wrapper;

import com.hyrax.microservice.project.rest.api.domain.response.TaskResponse;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TaskResponseWrapper {

    private final List<TaskResponse> taskResponses;
}
