package com.hyrax.microservice.account.service.exception;

public class AccountAlreadyExistsException extends RuntimeException {

    public AccountAlreadyExistsException(final String message) {
        super(message);
    }
}
