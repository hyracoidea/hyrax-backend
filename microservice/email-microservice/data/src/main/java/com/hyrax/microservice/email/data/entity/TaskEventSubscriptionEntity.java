package com.hyrax.microservice.email.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "task_event_subscriptions")
public class TaskEventSubscriptionEntity {

    @Id
    private String username;

    private boolean taskCreationAction;

    private boolean taskRemovalAction;
}
