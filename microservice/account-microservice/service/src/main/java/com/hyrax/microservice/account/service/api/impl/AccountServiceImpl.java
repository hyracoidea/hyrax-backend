package com.hyrax.microservice.account.service.api.impl;

import com.google.common.base.Preconditions;
import com.hyrax.microservice.account.data.entity.AccountEntity;
import com.hyrax.microservice.account.data.mapper.AccountMapper;
import com.hyrax.microservice.account.service.api.AccountService;
import com.hyrax.microservice.account.service.domain.Account;
import com.hyrax.microservice.account.service.exception.EmailAlreadyExistsException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountServiceImpl.class);

    private static final String ERROR_MESSAGE_EMAIL_TEMPLATE = "There is already an account with this email address=%s";

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
    public Optional<Account> findAccountByEmail(final String email) {
        Account account = null;
        final AccountEntity accountEntity = accountMapper.selectByEmail(email);
        if (Objects.nonNull(accountEntity)) {
            account = modelMapper.map(accountEntity, Account.class);
        }
        return Optional.ofNullable(account);
    }

    @Override
    @Transactional
    public void saveAccount(final Account account) throws EmailAlreadyExistsException {
        Preconditions.checkArgument(Objects.nonNull(account));

        try {
            if (!existAccountByEmail(account.getEmail())) {
                LOGGER.info("Account={} seems to be valid, trying to save...", account);
                final AccountEntity accountEntity = modelMapper.map(account, AccountEntity.class);
                accountMapper.insert(accountEntity);
                LOGGER.info("Account={} saving was successful", account);
            } else {
                throwEmailAlreadyExistsException(account.getEmail());
            }
        } catch (final DuplicateKeyException e) {
            throwEmailAlreadyExistsException(account.getEmail(), e);
        }
    }

    private EmailAlreadyExistsException throwEmailAlreadyExistsException(final String email, final Exception... e) {
        final String errorMessage = String.format(ERROR_MESSAGE_EMAIL_TEMPLATE, email);
        LOGGER.error(errorMessage, e);
        throw new EmailAlreadyExistsException(errorMessage);
    }
}
