package com.hyrax.client.email.api.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

@Data
@JsonDeserialize(builder = TaskEventSubscriptionRequest.Builder.class)
public class TaskEventSubscriptionRequest extends BaseEventSubscriptionRequest {

    private final boolean taskCreationAction;

    private final boolean taskRemovalAction;

    private TaskEventSubscriptionRequest(final String username, final boolean taskCreationAction, final boolean taskRemovalAction) {
        super(username);
        this.taskCreationAction = taskCreationAction;
        this.taskRemovalAction = taskRemovalAction;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private boolean taskCreationAction;
        private String username;
        private boolean taskRemovalAction;

        private Builder() {
        }

        public Builder withTaskCreationAction(boolean taskCreationAction) {
            this.taskCreationAction = taskCreationAction;
            return this;
        }

        public Builder withUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder withTaskRemovalAction(boolean taskRemovalAction) {
            this.taskRemovalAction = taskRemovalAction;
            return this;
        }

        public TaskEventSubscriptionRequest build() {
            return new TaskEventSubscriptionRequest(username, taskCreationAction, taskRemovalAction);
        }
    }
}
