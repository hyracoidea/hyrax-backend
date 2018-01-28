package com.hyrax.microservice.account.service.api;

import com.hyrax.microservice.account.service.domain.Account;
import com.hyrax.microservice.account.service.exception.EmailAlreadyExistsException;

import java.util.Optional;

public interface AccountService {

    boolean existAccountByEmail(String email);

    Optional<Account> findAccountByEmail(String email);

    void saveAccount(Account account) throws EmailAlreadyExistsException;
}
