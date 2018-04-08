package com.hyrax.microservice.email.service.api.model;

import com.hyrax.microservice.email.service.api.checker.BaseEventCategory;
import com.hyrax.microservice.email.service.api.checker.SubEventCategory;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class EmailNotificationDetail {

    private final String senderEmailAddress;

    private final String recipientUsername;

    private final String recipientEmailAddress;

    private final String subject;

    private final BaseEventCategory baseEventCategory;

    private final SubEventCategory subEventCategory;

    private final Map<String, String> model;
}
