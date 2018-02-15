package com.hyrax.spring.boot.starter.authentication.rest.service;

import com.hyrax.spring.boot.starter.authentication.model.SecuredAccountDetails;

import java.util.Optional;

public interface SecuredAccountRESTService {

    Optional<SecuredAccountDetails> retrieveSecuredAccount(String username);
}
