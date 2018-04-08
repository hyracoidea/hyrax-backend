package com.hyrax.microservice.email.service.api.checker;

import com.hyrax.microservice.email.service.api.ColumnEventSubscriptionService;
import com.hyrax.microservice.email.service.api.model.ColumnEventSubscription;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class ColumnEventSubscriptionStatusChecker {

    private final ColumnEventSubscriptionService columnEventSubscriptionService;

    public boolean isSubscribedByColumnEventCategory(final String username, final SubEventCategory subEventCategory) {
        boolean result = false;
        final Optional<ColumnEventSubscription> columnEventSubscription = columnEventSubscriptionService.findByUsername(username);
        if (columnEventSubscription.isPresent()) {
            switch (subEventCategory) {
                case CREATION:
                    result = columnEventSubscription.get().isColumnCreationAction();
                    break;
                case REMOVAL:
                    result = columnEventSubscription.get().isColumnRemovalAction();
                    break;
                default:
                    break;
            }
        }
        return result;
    }
}
