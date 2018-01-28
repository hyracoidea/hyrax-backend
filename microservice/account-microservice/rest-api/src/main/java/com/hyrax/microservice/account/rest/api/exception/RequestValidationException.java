package com.hyrax.microservice.account.rest.api.exception;

import com.hyrax.microservice.account.rest.api.validation.bindingresult.ProcessedBindingResult;
import lombok.Getter;

@Getter
public class RequestValidationException extends RuntimeException {

    private static final String MESSAGE_TEMPLATE = "There are some validation errors: %s";

    private static final long serialVersionUID = -618049045702723137L;

    private final ProcessedBindingResult processedBindingResult;

    public RequestValidationException(final ProcessedBindingResult processedBindingResult) {
        super(String.format(MESSAGE_TEMPLATE, processedBindingResult.getRequestValidationDetails()));
        this.processedBindingResult = processedBindingResult;
    }
}
