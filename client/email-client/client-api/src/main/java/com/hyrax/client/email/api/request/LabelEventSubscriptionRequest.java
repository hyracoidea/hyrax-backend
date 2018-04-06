package com.hyrax.client.email.api.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

@Data
@JsonDeserialize(builder = LabelEventSubscriptionRequest.Builder.class)
public class LabelEventSubscriptionRequest extends BaseEventSubscriptionRequest {

    private final boolean labelCreationAction;

    private final boolean labelRemovalAction;

    private LabelEventSubscriptionRequest(final String username, final boolean labelCreationAction, final boolean labelRemovalAction) {
        super(username);
        this.labelCreationAction = labelCreationAction;
        this.labelRemovalAction = labelRemovalAction;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private boolean labelCreationAction;
        private String username;
        private boolean labelRemovalAction;

        private Builder() {
        }


        public Builder withLabelCreationAction(boolean labelCreationAction) {
            this.labelCreationAction = labelCreationAction;
            return this;
        }

        public Builder withUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder withLabelRemovalAction(boolean labelRemovalAction) {
            this.labelRemovalAction = labelRemovalAction;
            return this;
        }

        public LabelEventSubscriptionRequest build() {
            return new LabelEventSubscriptionRequest(username, labelCreationAction, labelRemovalAction);
        }
    }
}
