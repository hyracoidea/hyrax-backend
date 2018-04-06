package com.hyrax.microservice.email.rest.api.converter;

import com.hyrax.client.email.api.response.TaskEventSubscriptionResponse;
import com.hyrax.microservice.email.service.api.model.TaskEventSubscription;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TaskEventSubscriptionToTaskEventSubscriptionResponseConverter implements Converter<TaskEventSubscription, TaskEventSubscriptionResponse> {

    @Override
    public TaskEventSubscriptionResponse convert(final TaskEventSubscription taskEventSubscription) {
        return TaskEventSubscriptionResponse.builder()
                .username(taskEventSubscription.getUsername())
                .taskCreationAction(taskEventSubscription.isTaskCreationAction())
                .taskRemovalAction(taskEventSubscription.isTaskRemovalAction())
                .build();
    }
}
