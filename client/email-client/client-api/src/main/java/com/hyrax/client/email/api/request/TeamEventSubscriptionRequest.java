package com.hyrax.client.email.api.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

@Data
@JsonDeserialize(builder = TeamEventSubscriptionRequest.Builder.class)
public class TeamEventSubscriptionRequest extends BaseEventSubscriptionRequest {

    private final boolean teamCreationAction;

    private final boolean teamRemovalAction;

    private final boolean teamMemberAdditionAction;

    private final boolean teamMemberRemovalAction;

    private TeamEventSubscriptionRequest(final String username, final boolean teamCreationAction, final boolean teamRemovalAction,
                                         final boolean teamMemberAdditionAction, final boolean teamMemberRemovalAction) {
        super(username);
        this.teamCreationAction = teamCreationAction;
        this.teamRemovalAction = teamRemovalAction;
        this.teamMemberAdditionAction = teamMemberAdditionAction;
        this.teamMemberRemovalAction = teamMemberRemovalAction;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String username;
        private boolean teamCreationAction;
        private boolean teamRemovalAction;
        private boolean teamMemberAdditionAction;
        private boolean teamMemberRemovalAction;

        private Builder() {
        }

        public Builder withUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder withTeamCreationAction(boolean teamCreationAction) {
            this.teamCreationAction = teamCreationAction;
            return this;
        }

        public Builder withTeamRemovalAction(boolean teamRemovalAction) {
            this.teamRemovalAction = teamRemovalAction;
            return this;
        }

        public Builder withTeamMemberAdditionAction(boolean teamMemberAdditionAction) {
            this.teamMemberAdditionAction = teamMemberAdditionAction;
            return this;
        }

        public Builder withTeamMemberRemovalAction(boolean teamMemberRemovalAction) {
            this.teamMemberRemovalAction = teamMemberRemovalAction;
            return this;
        }

        public TeamEventSubscriptionRequest build() {
            return new TeamEventSubscriptionRequest(username, teamCreationAction, teamRemovalAction, teamMemberAdditionAction, teamMemberRemovalAction);
        }
    }
}
