package com.hyrax.microservice.email.service.api.checker;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SubscriptionStatusChecker {

    private final TeamEventSubscriptionStatusChecker teamEventSubscriptionStatusChecker;

    private final BoardEventSubscriptionStatusChecker boardEventSubscriptionStatusChecker;

    private final LabelEventSubscriptionStatusChecker labelEventSubscriptionStatusChecker;

    private final ColumnEventSubscriptionStatusChecker columnEventSubscriptionStatusChecker;

    private final TaskEventSubscriptionStatusChecker taskEventSubscriptionStatusChecker;

    public boolean isSubscribed(final String username, final BaseEventCategory baseEventCategory, final SubEventCategory subEventCategory) {
        boolean result = false;
        switch (baseEventCategory) {
            case ACCOUNT:
                result = true;
                break;
            case TEAM:
                result = teamEventSubscriptionStatusChecker.isSubscribedByTeamEventCategory(username, subEventCategory);
                break;
            case BOARD:
                result = boardEventSubscriptionStatusChecker.isSubscribedByBoardEventCategory(username, subEventCategory);
                break;
            case LABEL:
                result = labelEventSubscriptionStatusChecker.isSubscribedByLabelEventCategory(username, subEventCategory);
                break;
            case COLUMN:
                result = columnEventSubscriptionStatusChecker.isSubscribedByColumnEventCategory(username, subEventCategory);
                break;
            case TASK:
                result = taskEventSubscriptionStatusChecker.isSubscribedByTaskEventCategory(username, subEventCategory);
                break;
            case WATCHED_TASK:
                result = true;
                break;
            default:
                break;
        }
        return result;
    }
}
