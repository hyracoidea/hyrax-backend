package com.hyrax.microservice.email.service.api.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmailDetails {

    private final String from;

    private final String to;

    private final String subject;

    private final String contentAsHtml;
}
