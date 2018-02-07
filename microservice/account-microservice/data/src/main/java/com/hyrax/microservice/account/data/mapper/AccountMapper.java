package com.hyrax.microservice.account.data.mapper;

import com.hyrax.microservice.account.data.entity.AccountEntity;

public interface AccountMapper {

    AccountEntity selectByEmail(String email);

    AccountEntity selectByUsername(String username);

    void insert(AccountEntity accountEntity);
}
