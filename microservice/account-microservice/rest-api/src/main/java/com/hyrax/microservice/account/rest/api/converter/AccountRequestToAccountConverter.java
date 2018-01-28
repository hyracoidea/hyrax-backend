package com.hyrax.microservice.account.rest.api.converter;

import com.hyrax.microservice.account.rest.api.request.AccountRequest;
import com.hyrax.microservice.account.service.domain.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class AccountRequestToAccountConverter implements Converter<AccountRequest, Account> {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AccountRequestToAccountConverter(final PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Account convert(final AccountRequest accountRequest) {
        Account account = null;

        if (Objects.nonNull(accountRequest)) {
            account = Account.builder()
                    .firstName(accountRequest.getFirstName())
                    .lastName(accountRequest.getLastName())
                    .email(accountRequest.getEmail())
                    .passwordHash(passwordEncoder.encode(accountRequest.getPassword()))
                    .build();
        }

        return account;
    }
}
