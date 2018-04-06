package com.hyrax.client.email.api.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

@Data
@JsonDeserialize(builder = BoardEventSubscriptionRequest.Builder.class)
public class BoardEventSubscriptionRequest extends BaseEventSubscriptionRequest {

    private final boolean boardCreationAction;

    private final boolean boardRemovalAction;

    private final boolean boardMemberAdditionAction;

    private final boolean boardMemberRemovalAction;

    private BoardEventSubscriptionRequest(final String username, final boolean boardCreationAction, final boolean boardRemovalAction,
                                          final boolean boardMemberAdditionAction, final boolean boardMemberRemovalAction) {
        super(username);
        this.boardCreationAction = boardCreationAction;
        this.boardRemovalAction = boardRemovalAction;
        this.boardMemberAdditionAction = boardMemberAdditionAction;
        this.boardMemberRemovalAction = boardMemberRemovalAction;
    }

    public static Builder builder() {
        return new Builder();
    }


    public static final class Builder {
        private String username;
        private boolean boardCreationAction;
        private boolean boardRemovalAction;
        private boolean boardMemberAdditionAction;
        private boolean boardMemberRemovalAction;

        private Builder() {
        }

        public Builder withUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder withBoardCreationAction(boolean boardCreationAction) {
            this.boardCreationAction = boardCreationAction;
            return this;
        }

        public Builder withBoardRemovalAction(boolean boardRemovalAction) {
            this.boardRemovalAction = boardRemovalAction;
            return this;
        }

        public Builder withBoardMemberAdditionAction(boolean boardMemberAdditionAction) {
            this.boardMemberAdditionAction = boardMemberAdditionAction;
            return this;
        }

        public Builder withBoardMemberRemovalAction(boolean boardMemberRemovalAction) {
            this.boardMemberRemovalAction = boardMemberRemovalAction;
            return this;
        }

        public BoardEventSubscriptionRequest build() {
            return new BoardEventSubscriptionRequest(username, boardCreationAction, boardRemovalAction, boardMemberAdditionAction, boardMemberRemovalAction);
        }
    }
}
