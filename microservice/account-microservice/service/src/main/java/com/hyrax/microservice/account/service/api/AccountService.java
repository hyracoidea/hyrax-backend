package com.hyrax.microservice.account.service.api;

import com.hyrax.microservice.account.service.domain.Account;
import com.hyrax.microservice.account.service.exception.AccountAlreadyExistsException;

import java.util.Optional;

public interface AccountService {

    boolean existAccountByEmail(String email);

    boolean existAccountByUsername(String username);

    Optional<Account> findAccountByEmail(String email);

    Optional<Account> findAccountByUsername(String username);

    void saveAccount(Account account) throws AccountAlreadyExistsException;
}
