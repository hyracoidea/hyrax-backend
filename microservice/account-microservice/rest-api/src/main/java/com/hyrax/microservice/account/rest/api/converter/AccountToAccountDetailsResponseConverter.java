package com.hyrax.microservice.account.rest.api.converter;

import com.hyrax.microservice.account.rest.api.domain.response.AccountDetailsResponse;
import com.hyrax.microservice.account.service.domain.Account;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AccountToAccountDetailsResponseConverter implements Converter<Account, AccountDetailsResponse> {

    @Override
    public AccountDetailsResponse convert(final Account account) {
        return AccountDetailsResponse.builder()
                .firstName(account.getFirstName())
                .lastName(account.getLastName())
                .username(account.getUsername())
                .email(account.getEmail())
                .build();
    }
}
