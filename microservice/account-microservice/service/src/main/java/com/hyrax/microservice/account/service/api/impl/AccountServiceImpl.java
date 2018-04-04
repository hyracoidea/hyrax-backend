package com.hyrax.microservice.account.service.api.impl;

import com.google.common.base.Preconditions;
import com.hyrax.microservice.account.data.entity.AccountEntity;
import com.hyrax.microservice.account.data.mapper.AccountMapper;
import com.hyrax.microservice.account.service.api.AccountService;
import com.hyrax.microservice.account.service.domain.Account;
import com.hyrax.microservice.account.service.exception.AccountAlreadyExistsException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountServiceImpl.class);

    private static final String ERROR_MESSAGE_TEMPLATE = "Account already exists with this username=%s or this email=%s";

    private final AccountMapper accountMapper;

    private final ModelMapper modelMapper;

    @Autowired
    public AccountServiceImpl(final AccountMapper accountMapper, final ModelMapper modelMapper) {
        this.accountMapper = accountMapper;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existAccountByEmail(final String email) {
        return findAccountByEmail(email).isPresent();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existAccountByUsername(final String username) {
        return findAccountByUsername(username).isPresent();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Account> findAccountByEmail(final String email) {
        Account account = null;
        final AccountEntity accountEntity = accountMapper.selectByEmail(email);
        if (Objects.nonNull(accountEntity)) {
            account = modelMapper.map(accountEntity, Account.class);
        }
        return Optional.ofNullable(account);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Account> findAccountByUsername(final String username) {
        Account account = null;
        final AccountEntity accountEntity = accountMapper.selectByUsername(username);
        if (Objects.nonNull(accountEntity)) {
            account = modelMapper.map(accountEntity, Account.class);
        }
        return Optional.ofNullable(account);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Account> findAllByUsernames(final List<String> usernames) {
        return accountMapper.selectAllByUsernames(usernames)
                .parallelStream()
                .map(accountEntity -> modelMapper.map(accountEntity, Account.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void saveAccount(final Account account) throws AccountAlreadyExistsException {
        Preconditions.checkArgument(Objects.nonNull(account));

        try {
            if (!exist(account)) {
                LOGGER.info("Account={} seems to be valid, trying to save...", account);
                final AccountEntity accountEntity = modelMapper.map(account, AccountEntity.class);
                accountMapper.insert(accountEntity);
                LOGGER.info("Account={} saving was successful", account);
            } else {
                throwAccountAlreadyExistsException(account.getUsername(), account.getEmail());
            }
        } catch (final DuplicateKeyException e) {
            throwAccountAlreadyExistsException(account.getUsername(), account.getEmail(), e);
        }
    }

    private boolean exist(final Account account) {
        return existAccountByEmail(account.getEmail()) || existAccountByUsername(account.getUsername());
    }

    private AccountAlreadyExistsException throwAccountAlreadyExistsException(final String username, final String email, final Exception... e) {
        final String errorMessage = String.format(ERROR_MESSAGE_TEMPLATE, username, email);
        LOGGER.error(errorMessage, e);
        throw new AccountAlreadyExistsException(errorMessage);
    }
}
