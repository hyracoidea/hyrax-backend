package com.hyrax.microservice.account.data.mapper;

import com.hyrax.microservice.account.data.entity.AccountEntity;

import java.util.Collection;

public interface AccountMapper {

    Collection<AccountEntity> selectAll();

    AccountEntity selectByEmail(String email);

    AccountEntity selectByUsername(String username);

    void insert(AccountEntity accountEntity);
}
