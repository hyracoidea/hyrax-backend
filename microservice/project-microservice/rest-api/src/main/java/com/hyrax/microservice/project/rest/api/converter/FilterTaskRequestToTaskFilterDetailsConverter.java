package com.hyrax.microservice.project.rest.api.converter;

import com.hyrax.microservice.project.rest.api.domain.request.FilterTaskRequest;
import com.hyrax.microservice.project.service.domain.TaskFilterDetails;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class FilterTaskRequestToTaskFilterDetailsConverter implements Converter<FilterTaskRequest, TaskFilterDetails> {

    @Override
    public TaskFilterDetails convert(final FilterTaskRequest filterTaskRequest) {
        return TaskFilterDetails.builder()
                .assignedUsername(filterTaskRequest.getAssignedUsername())
                .labelNames(filterTaskRequest.getLabelNames())
                .build();
    }
}
