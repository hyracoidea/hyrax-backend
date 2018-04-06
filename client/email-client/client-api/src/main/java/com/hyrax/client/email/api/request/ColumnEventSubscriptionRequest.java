package com.hyrax.client.email.api.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

@Data
@JsonDeserialize(builder = ColumnEventSubscriptionRequest.Builder.class)
public class ColumnEventSubscriptionRequest extends BaseEventSubscriptionRequest {

    private final boolean columnCreationAction;

    private final boolean columnRemovalAction;

    private ColumnEventSubscriptionRequest(final String username, final boolean columnCreationAction, final boolean columnRemovalAction) {
        super(username);
        this.columnCreationAction = columnCreationAction;
        this.columnRemovalAction = columnRemovalAction;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String username;
        private boolean columnCreationAction;
        private boolean columnRemovalAction;

        private Builder() {
        }

        public Builder withUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder withColumnCreationAction(boolean columnCreationAction) {
            this.columnCreationAction = columnCreationAction;
            return this;
        }

        public Builder withColumnRemovalAction(boolean columnRemovalAction) {
            this.columnRemovalAction = columnRemovalAction;
            return this;
        }

        public ColumnEventSubscriptionRequest build() {
            return new ColumnEventSubscriptionRequest(username, columnCreationAction, columnRemovalAction);
        }
    }
}
