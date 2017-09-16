package com.hyrax.microservice.sample.service.exception;

import lombok.Data;

@Data
public class EchoNotFoundException extends RuntimeException {

    private final String message;
}