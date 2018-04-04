package com.hyrax.microservice.email.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "board_event_subscriptions")
public class BoardEventSubscriptionEntity {

    @Id
    private String username;

    private boolean boardCreationAction;

    private boolean boardRemovalAction;

    private boolean boardMemberAdditionAction;

    private boolean boardMemberRemovalAction;
}
