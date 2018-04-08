package com.hyrax.microservice.email.service.api.checker;

import com.hyrax.microservice.email.service.api.BoardEventSubscriptionService;
import com.hyrax.microservice.email.service.api.model.BoardEventSubscription;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class BoardEventSubscriptionStatusChecker {

    private final BoardEventSubscriptionService boardEventSubscriptionService;

    public boolean isSubscribedByBoardEventCategory(final String username, final SubEventCategory subEventCategory) {
        boolean result = false;
        final Optional<BoardEventSubscription> boardEventSubscription = boardEventSubscriptionService.findByUsername(username);
        if (boardEventSubscription.isPresent()) {
            switch (subEventCategory) {
                case CREATION:
                    result = boardEventSubscription.get().isBoardCreationAction();
                    break;
                case MEMBER_ADDITION:
                    result = boardEventSubscription.get().isBoardMemberAdditionAction();
                    break;
                case MEMBER_REMOVAL:
                    result = boardEventSubscription.get().isBoardMemberRemovalAction();
                    break;
                case REMOVAL:
                    result = boardEventSubscription.get().isBoardRemovalAction();
                    break;
                default:
                    break;
            }
        }
        return result;
    }
}
