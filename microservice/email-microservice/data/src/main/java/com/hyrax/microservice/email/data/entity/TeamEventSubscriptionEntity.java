package com.hyrax.microservice.email.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "team_event_subscriptions")
public class TeamEventSubscriptionEntity {

    @Id
    private String username;

    private boolean teamCreationAction;

    private boolean teamRemovalAction;

    private boolean teamMemberAdditionAction;

    private boolean teamMemberRemovalAction;
}
