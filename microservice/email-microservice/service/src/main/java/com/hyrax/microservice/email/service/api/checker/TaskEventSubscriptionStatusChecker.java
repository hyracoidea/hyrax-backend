package com.hyrax.microservice.email.service.api.checker;

import com.hyrax.microservice.email.service.api.TaskEventSubscriptionService;
import com.hyrax.microservice.email.service.api.model.TaskEventSubscription;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class TaskEventSubscriptionStatusChecker {

    private final TaskEventSubscriptionService taskEventSubscriptionService;

    public boolean isSubscribedByTaskEventCategory(final String username, final SubEventCategory subEventCategory) {
        boolean result = false;
        final Optional<TaskEventSubscription> taskEventSubscription = taskEventSubscriptionService.findByUsername(username);
        if (taskEventSubscription.isPresent()) {
            switch (subEventCategory) {
                case CREATION:
                    result = taskEventSubscription.get().isTaskCreationAction();
                    break;
                case REMOVAL:
                    result = taskEventSubscription.get().isTaskRemovalAction();
                    break;
                default:
                    break;
            }
        }
        return result;
    }
}
