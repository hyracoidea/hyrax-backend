package com.hyrax.microservice.email.service.api.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmailDetail {

    private final String senderEmailAddress;

    private final String recipientEmailAddress;

    private final String subject;

    private final String contentAsHtml;
}
