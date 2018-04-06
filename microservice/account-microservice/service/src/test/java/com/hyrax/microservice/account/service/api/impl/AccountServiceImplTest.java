package com.hyrax.microservice.account.service.api.impl;

import com.hyrax.microservice.account.data.entity.AccountEntity;
import com.hyrax.microservice.account.data.mapper.AccountMapper;
import com.hyrax.microservice.account.service.api.AccountService;
import com.hyrax.microservice.account.service.domain.Account;
import com.hyrax.microservice.account.service.exception.AccountAlreadyExistsException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DuplicateKeyException;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceImplTest {

    private static final String NON_EXISTING_EMAIL = "nonExisting_email@email.com";
    private static final String NON_EXISTING_USERNAME = "nonExistingUsername";

    private static final String EXISTING_EMAIL = "existing_email@email.com";
    private static final String EXISTING_USERNAME = "username";
    private static final String FIRST_NAME = "FirstName";
    private static final String LAST_NAME = "LastName";
    private static final String PASSWORD_HASH = "ABCDEFGH1234";

    private final AccountEntity nonExistingAccountEntity = buildAccountEntityWith(NON_EXISTING_USERNAME, NON_EXISTING_EMAIL);
    private final Account nonExistingAccount = buildAccountWith(NON_EXISTING_USERNAME, NON_EXISTING_EMAIL);

    private final AccountEntity existingAccountEntity = buildAccountEntityWith(EXISTING_USERNAME, EXISTING_EMAIL);
    private final Account existingAccount = buildAccountWith(EXISTING_USERNAME, EXISTING_EMAIL);

    @Mock
    private AccountMapper accountMapper;

    @Mock
    private EmailEventSubscriptionHelper emailEventSubscriptionHelper;

    @Mock
    private ModelMapper modelMapper;

    private AccountService accountService;

    @Before
    public void init() {
        accountService = new AccountServiceImpl(accountMapper, emailEventSubscriptionHelper, modelMapper);
    }

    @Test
    public void existAccountByEmailShouldReturnFalseWhenAccountDoesNotExist() {
        // Given
        given(accountMapper.selectByEmail(NON_EXISTING_EMAIL)).willReturn(null);

        // When
        final boolean result = accountService.existAccountByEmail(NON_EXISTING_EMAIL);

        // Then
        assertThat(result, is(false));

        then(accountMapper).should().selectByEmail(NON_EXISTING_EMAIL);
        verifyNoMoreInteractions(accountMapper, modelMapper);
    }

    @Test
    public void existAccountByEmailShouldReturnTrueWhenAccountExists() {
        // Given
        given(accountMapper.selectByEmail(EXISTING_EMAIL)).willReturn(existingAccountEntity);
        given(modelMapper.map(existingAccountEntity, Account.class)).willReturn(existingAccount);

        // When
        final boolean result = accountService.existAccountByEmail(EXISTING_EMAIL);

        // Then
        assertThat(result, is(true));

        then(accountMapper).should().selectByEmail(EXISTING_EMAIL);
        then(modelMapper).should().map(existingAccountEntity, Account.class);
        verifyNoMoreInteractions(accountMapper, modelMapper);
    }

    @Test
    public void existAccountByUsernameShouldReturnFalseWhenAccountDoesNotExist() {
        // Given
        given(accountMapper.selectByUsername(NON_EXISTING_USERNAME)).willReturn(null);

        // When
        final boolean result = accountService.existAccountByUsername(NON_EXISTING_USERNAME);

        // Then
        assertThat(result, is(false));

        then(accountMapper).should().selectByUsername(NON_EXISTING_USERNAME);
        verifyNoMoreInteractions(accountMapper, modelMapper);
    }

    @Test
    public void existAccountByUsernameShouldReturnTrueWhenAccountExists() {
        // Given
        given(accountMapper.selectByUsername(EXISTING_USERNAME)).willReturn(existingAccountEntity);
        given(modelMapper.map(existingAccountEntity, Account.class)).willReturn(existingAccount);

        // When
        final boolean result = accountService.existAccountByUsername(EXISTING_USERNAME);

        // Then
        assertThat(result, is(true));

        then(accountMapper).should().selectByUsername(EXISTING_USERNAME);
        then(modelMapper).should().map(existingAccountEntity, Account.class);
        verifyNoMoreInteractions(accountMapper, modelMapper);
    }

    @Test
    public void findAccountByEmailShouldReturnEmptyOptionalWhenAccountDoesNotExist() {
        // Given
        given(accountMapper.selectByEmail(NON_EXISTING_EMAIL)).willReturn(null);

        // When
        final Optional<Account> result = accountService.findAccountByEmail(NON_EXISTING_EMAIL);

        // Then
        assertThat(result, notNullValue());
        assertThat(result.isPresent(), is(false));

        then(accountMapper).should().selectByEmail(NON_EXISTING_EMAIL);
        verifyNoMoreInteractions(accountMapper, modelMapper);
    }

    @Test
    public void findAccountByEmailShouldReturnNonEmptyOptionalWhenAccountExists() {
        // Given
        given(accountMapper.selectByEmail(EXISTING_EMAIL)).willReturn(existingAccountEntity);
        given(modelMapper.map(existingAccountEntity, Account.class)).willReturn(existingAccount);

        // When
        final Optional<Account> result = accountService.findAccountByEmail(EXISTING_EMAIL);

        // Then
        assertThat(result, notNullValue());
        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), equalTo(existingAccount));

        then(accountMapper).should().selectByEmail(EXISTING_EMAIL);
        then(modelMapper).should().map(existingAccountEntity, Account.class);
        verifyNoMoreInteractions(accountMapper, modelMapper);
    }

    @Test
    public void findAccountByUsernameShouldReturnEmptyOptionalWhenAccountDoesNotExist() {
        // Given
        given(accountMapper.selectByUsername(NON_EXISTING_USERNAME)).willReturn(null);

        // When
        final Optional<Account> result = accountService.findAccountByUsername(NON_EXISTING_USERNAME);

        // Then
        assertThat(result, notNullValue());
        assertThat(result.isPresent(), is(false));

        then(accountMapper).should().selectByUsername(NON_EXISTING_USERNAME);
        verifyNoMoreInteractions(accountMapper, modelMapper);
    }

    @Test
    public void findAccountByUsernameShouldReturnNonEmptyOptionalWhenAccountExists() {
        // Given
        given(accountMapper.selectByUsername(EXISTING_USERNAME)).willReturn(existingAccountEntity);
        given(modelMapper.map(existingAccountEntity, Account.class)).willReturn(existingAccount);

        // When
        final Optional<Account> result = accountService.findAccountByUsername(EXISTING_USERNAME);

        // Then
        assertThat(result, notNullValue());
        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), equalTo(existingAccount));

        then(accountMapper).should().selectByUsername(EXISTING_USERNAME);
        then(modelMapper).should().map(existingAccountEntity, Account.class);
        verifyNoMoreInteractions(accountMapper, modelMapper);
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveAccountShouldThrowIllegalArgumentExceptionWhenParameterAccountIsNull() {
        // Given

        // When
        accountService.saveAccount(null);

        // Then
    }

    @Test(expected = AccountAlreadyExistsException.class)
    public void saveAccountShouldThrowAccountAlreadyExistsExceptionWhenEmailAlreadyExists() {
        // Given
        given(accountMapper.selectByEmail(EXISTING_EMAIL)).willReturn(existingAccountEntity);
        given(modelMapper.map(existingAccountEntity, Account.class)).willReturn(existingAccount);

        // When
        accountService.saveAccount(existingAccount);

        // Then
    }

    @Test(expected = AccountAlreadyExistsException.class)
    public void saveAccountShouldThrowAccountAlreadyExistsExceptionWhenUsernameAlreadyExists() {
        // Given
        given(accountMapper.selectByUsername(EXISTING_USERNAME)).willReturn(existingAccountEntity);
        given(modelMapper.map(existingAccountEntity, Account.class)).willReturn(existingAccount);

        // When
        accountService.saveAccount(existingAccount);

        // Then
    }

    @Test(expected = AccountAlreadyExistsException.class)
    public void saveAccountShouldThrowEmailAlreadyExistsExceptionWhenAccountAlreadyExistsUnderTheInsertion() {
        // Given
        final DuplicateKeyException duplicateKeyException = new DuplicateKeyException("Duplicate key exception test message");

        given(accountMapper.selectByEmail(NON_EXISTING_EMAIL)).willReturn(null);
        given(modelMapper.map(nonExistingAccount, AccountEntity.class)).willReturn(nonExistingAccountEntity);
        doThrow(duplicateKeyException).when(accountMapper).insert(any(AccountEntity.class));

        // When
        accountService.saveAccount(nonExistingAccount);

        // Then
    }

    @Test
    public void saveAccountShouldSaveWhenAccountDoesNotExist() {
        // Given
        given(accountMapper.selectByEmail(NON_EXISTING_EMAIL)).willReturn(null);
        given(accountMapper.selectByUsername(NON_EXISTING_USERNAME)).willReturn(null);
        given(modelMapper.map(nonExistingAccount, AccountEntity.class)).willReturn(nonExistingAccountEntity);
        doNothing().when(accountMapper).insert(nonExistingAccountEntity);

        // When
        accountService.saveAccount(nonExistingAccount);

        // Then
        then(accountMapper).should().selectByEmail(NON_EXISTING_EMAIL);
        then(accountMapper).should().selectByUsername(NON_EXISTING_USERNAME);
        then(modelMapper).should().map(nonExistingAccount, AccountEntity.class);
        then(accountMapper).should().insert(nonExistingAccountEntity);
        verifyNoMoreInteractions(accountMapper, modelMapper);
    }


    private AccountEntity buildAccountEntityWith(final String username, final String email) {
        return AccountEntity.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .username(username)
                .email(email)
                .passwordHash(PASSWORD_HASH)
                .build();
    }

    private Account buildAccountWith(final String username, final String email) {
        return Account.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .username(username)
                .email(email)
                .passwordHash(PASSWORD_HASH)
                .build();
    }
}
