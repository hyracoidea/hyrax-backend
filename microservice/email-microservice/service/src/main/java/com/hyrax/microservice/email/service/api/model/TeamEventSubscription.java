package com.hyrax.microservice.email.service.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeamEventSubscription {

    private String username;

    private boolean teamCreationAction;

    private boolean teamRemovalAction;

    private boolean teamMemberAdditionAction;

    private boolean teamMemberRemovalAction;
}
