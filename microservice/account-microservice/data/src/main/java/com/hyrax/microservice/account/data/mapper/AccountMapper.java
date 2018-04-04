package com.hyrax.microservice.account.data.mapper;

import com.hyrax.microservice.account.data.entity.AccountEntity;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

public interface AccountMapper {

    Collection<AccountEntity> selectAllByUsernames(@Param("usernames") List<String> usernames);

    AccountEntity selectByEmail(String email);

    AccountEntity selectByUsername(String username);

    void insert(AccountEntity accountEntity);
}
