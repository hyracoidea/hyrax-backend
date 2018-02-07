package com.hyrax.microservice.account.data.mapper;

import com.hyrax.microservice.account.data.annotation.IntegrationTest;
import com.hyrax.microservice.account.data.entity.AccountEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@IntegrationTest
@RunWith(SpringRunner.class)
public class AccountMapperIT {

    private static final String NON_EXISTING_EMAIL = "non_existing_email@email.com";
    private static final String NON_EXISTING_USERNAME = "nonExistingUserName";

    private static final String USERNAME = "testUsername";
    private static final String EMAIL = "integration_test@email.com";
    private static final String FIRST_NAME = "TestFirstName";
    private static final String LAST_NAME = "TestLastName";
    private static final String PASSWORD_HASH = "$2a$10$UtAtQeSXNV7EFZJW/yc53ucDaKQxVI1HjYKTf9TdfGnz4hVsB1J4m";

    @Autowired
    private AccountMapper accountMapper;

    @Test
    public void selectByEmailShouldReturnNullWhenParameterEmailIsNull() {
        // Given

        // When
        final AccountEntity result = accountMapper.selectByEmail(null);

        // Then
        assertThat(result, nullValue());
    }

    @Test
    public void selectByEmailShouldReturnNullWhenParameterEmailDoesNotExist() {
        // Given

        // When
        final AccountEntity result = accountMapper.selectByEmail(NON_EXISTING_EMAIL);

        // Then
        assertThat(result, nullValue());
    }

    @Test
    public void selectByEmailShouldReturnNonNullWhenParameterEmailExists() {
        // Given
        final AccountEntity expected = buildExistingAccountEntity();

        // When
        final AccountEntity result = accountMapper.selectByEmail(EMAIL);

        // Then
        assertThat(result, notNullValue());
        assertThat(result, equalTo(expected));
    }

    @Test
    public void selectByUsernameShouldReturnNullWhenParameterUsernameIsNull() {
        // Given

        // When
        final AccountEntity result = accountMapper.selectByUsername(null);

        // Then
        assertThat(result, nullValue());
    }

    @Test
    public void selectByUsernameShouldReturnNullWhenParameterUsernameDoesNotExist() {
        // Given

        // When
        final AccountEntity result = accountMapper.selectByUsername(NON_EXISTING_USERNAME);

        // Then
        assertThat(result, nullValue());
    }

    @Test
    public void selectByUsernameShouldReturnNonNullWhenParameterUsernameExists() {
        // Given
        final AccountEntity expected = buildExistingAccountEntity();

        // When
        final AccountEntity result = accountMapper.selectByUsername(USERNAME);

        // Then
        assertThat(result, notNullValue());
        assertThat(result, equalTo(expected));
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void insertShouldThrowDataIntegrityViolationExceptionWhenParameterAccountEntityIsNull() {
        // Given

        // When
        accountMapper.insert(null);

        // Then
    }

    @Test(expected = DuplicateKeyException.class)
    public void insertShouldThrowDuplicateKeyExceptionWhenParameterEmailAlreadyExists() {
        // Given
        final AccountEntity existingAccount = buildExistingAccountEntity();
        existingAccount.setUsername(NON_EXISTING_USERNAME);

        // When
        accountMapper.insert(existingAccount);

        // Then
    }

    @Test(expected = DuplicateKeyException.class)
    public void insertShouldThrowDuplicateKeyExceptionWhenParameterUsernameAlreadyExists() {
        // Given
        final AccountEntity existingAccount = buildExistingAccountEntity();
        existingAccount.setEmail(NON_EXISTING_EMAIL);

        // When
        accountMapper.insert(existingAccount);

        // Then
    }

    @Test
    public void insertShouldWorkWhenParameterAccountEntityDoesNotExist() {
        // Given
        final AccountEntity accountEntity = buildNonExistingAccountEntity();

        // When
        accountMapper.insert(accountEntity);

        // Then
        assertThat(accountMapper.selectByUsername(accountEntity.getUsername()), equalTo(accountEntity));
        assertThat(accountMapper.selectByEmail(accountEntity.getEmail()), equalTo(accountEntity));
    }

    private AccountEntity buildExistingAccountEntity() {
        return AccountEntity.builder()
                .accountId(1L)
                .username(USERNAME)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .email(EMAIL)
                .passwordHash(PASSWORD_HASH)
                .build();
    }

    private AccountEntity buildNonExistingAccountEntity() {
        return AccountEntity.builder()
                .username(NON_EXISTING_USERNAME)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .email(NON_EXISTING_EMAIL)
                .passwordHash(PASSWORD_HASH)
                .build();
    }
}
