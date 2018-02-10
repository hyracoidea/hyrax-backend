package com.hyrax.microservice.account.rest.api.converter;

import com.hyrax.microservice.account.rest.api.domain.Authority;
import com.hyrax.microservice.account.rest.api.domain.response.SecuredAccountResponse;
import com.hyrax.microservice.account.service.domain.Account;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class AccountToSecuredAccountResponseConverter implements Converter<Account, SecuredAccountResponse> {

    @Override
    public SecuredAccountResponse convert(final Account account) {
        SecuredAccountResponse response = null;

        if (Objects.nonNull(account)) {
            response = SecuredAccountResponse.builder()
                    .username(account.getUsername())
                    .password(account.getPasswordHash())
                    .authority(Authority.valueOf(account.getAuthority()))
                    .build();
        }

        return response;
    }
}
