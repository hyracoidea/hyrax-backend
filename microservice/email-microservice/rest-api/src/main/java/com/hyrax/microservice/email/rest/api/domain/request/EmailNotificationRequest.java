package com.hyrax.microservice.email.rest.api.domain.request;

import com.hyrax.microservice.email.rest.api.domain.BaseTemplate;
import com.hyrax.microservice.email.rest.api.domain.SubTemplate;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class EmailNotificationRequest {

    private final String senderEmailAddress;

    private final List<String> recipientUsernames;

    private final String subject;

    private final BaseTemplate baseTemplate;

    private final SubTemplate subTemplate;

    private final Map<String, String> model;
}
