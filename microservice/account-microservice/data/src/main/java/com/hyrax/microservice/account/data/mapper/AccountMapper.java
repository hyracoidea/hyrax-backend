package com.hyrax.microservice.account.data.mapper;

import com.hyrax.microservice.account.data.entity.AccountEntity;

public interface AccountMapper {

    AccountEntity selectByEmail(String email);

    void insert(AccountEntity accountEntity);
}
