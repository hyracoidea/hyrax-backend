package com.hyrax.microservice.account.rest.api.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RequestValidationResponse {

    private final List<RequestValidationDetail> requestValidationDetails;
}