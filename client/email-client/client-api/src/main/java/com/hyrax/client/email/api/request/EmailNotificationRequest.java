package com.hyrax.client.email.api.request;

import com.hyrax.client.email.api.domain.BaseTemplate;
import com.hyrax.client.email.api.domain.SubTemplate;
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
