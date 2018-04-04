package com.hyrax.microservice.email.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "column_event_subscriptions")
public class ColumnEventSubscriptionEntity {

    @Id
    private String username;

    private boolean columnCreationAction;

    private boolean columnRemovalAction;
}
