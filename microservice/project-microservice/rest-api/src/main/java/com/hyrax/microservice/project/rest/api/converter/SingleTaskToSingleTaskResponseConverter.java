package com.hyrax.microservice.project.rest.api.converter;

import com.hyrax.microservice.project.rest.api.domain.response.LabelResponse;
import com.hyrax.microservice.project.rest.api.domain.response.SingleTaskResponse;
import com.hyrax.microservice.project.service.domain.Label;
import com.hyrax.microservice.project.service.domain.SingleTask;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class SingleTaskToSingleTaskResponseConverter implements Converter<SingleTask, SingleTaskResponse> {

    private final LabelToLabelResponseConverter labelToLabelResponseConverter;

    @Override
    public SingleTaskResponse convert(final SingleTask singleTask) {
        return SingleTaskResponse.builder()
                .taskId(singleTask.getTaskId())
                .taskName(singleTask.getTaskName())
                .description(singleTask.getDescription())
                .assignedUsername(singleTask.getAssignedUsername())
                .watchedUsers(singleTask.getWatchedUsers())
                .labels(convertLabels(singleTask.getLabels()))
                .build();
    }

    private Set<LabelResponse> convertLabels(final Set<Label> labels) {
        return labels.parallelStream().map(label -> labelToLabelResponseConverter.convert(label)).collect(Collectors.toSet());
    }
}
