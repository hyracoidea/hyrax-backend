package com.hyrax.microservice.email.service.api.checker;

import com.hyrax.microservice.email.service.api.LabelEventSubscriptionService;
import com.hyrax.microservice.email.service.api.model.LabelEventSubscription;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class LabelEventSubscriptionStatusChecker {

    private final LabelEventSubscriptionService labelEventSubscriptionService;

    public boolean isSubscribedByLabelEventCategory(final String username, final SubEventCategory subEventCategory) {
        boolean result = false;
        final Optional<LabelEventSubscription> labelEventSubscription = labelEventSubscriptionService.findByUsername(username);
        if (labelEventSubscription.isPresent()) {
            switch (subEventCategory) {
                case CREATION:
                    result = labelEventSubscription.get().isLabelCreationAction();
                    break;
                case REMOVAL:
                    result = labelEventSubscription.get().isLabelRemovalAction();
                    break;
                default:
                    break;
            }
        }
        return result;
    }
}
