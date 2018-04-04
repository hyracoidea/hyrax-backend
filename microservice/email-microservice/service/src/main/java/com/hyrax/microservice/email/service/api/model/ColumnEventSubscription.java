package com.hyrax.microservice.email.service.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ColumnEventSubscription {

    private String username;

    private boolean columnCreationAction;

    private boolean columnRemovalAction;
}
