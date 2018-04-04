package com.hyrax.microservice.email.service.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardEventSubscription {

    private String username;

    private boolean boardCreationAction;

    private boolean boardRemovalAction;

    private boolean boardMemberAdditionAction;

    private boolean boardMemberRemovalAction;
}
