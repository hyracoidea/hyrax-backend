package com.hyrax.microservice.project.rest.api.converter;

import com.hyrax.microservice.project.rest.api.domain.response.TaskResponse;
import com.hyrax.microservice.project.service.domain.Task;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TaskToTaskResponseConverter implements Converter<Task, TaskResponse> {

    @Override
    public TaskResponse convert(final Task task) {
        return TaskResponse.builder()
                .taskId(task.getTaskId())
                .taskName(task.getTaskName())
                .taskIndex(task.getTaskIndex())
                .assignedUsername(task.getAssignedUsername())
                .build();
    }
}
