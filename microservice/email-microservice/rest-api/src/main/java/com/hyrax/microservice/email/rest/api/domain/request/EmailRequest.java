package com.hyrax.microservice.email.rest.api.domain.request;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class EmailRequest {

    private final String from;

    private final String to;

    private final String subject;

    private final String templateName;
    
    private final Map<String, String> model;

}