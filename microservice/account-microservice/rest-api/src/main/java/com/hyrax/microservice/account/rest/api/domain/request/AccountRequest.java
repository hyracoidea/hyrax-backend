package com.hyrax.microservice.account.rest.api.domain.request;

import com.hyrax.microservice.account.rest.api.validation.annotation.Email;
import com.hyrax.microservice.account.rest.api.validation.annotation.FirstName;
import com.hyrax.microservice.account.rest.api.validation.annotation.LastName;
import com.hyrax.microservice.account.rest.api.validation.annotation.MatchesPassword;
import com.hyrax.microservice.account.rest.api.validation.annotation.Password;
import com.hyrax.microservice.account.rest.api.validation.annotation.Username;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString(exclude = {"password", "passwordConfirmation"})
@MatchesPassword
public class AccountRequest {

    @FirstName
    private final String firstName;

    @LastName
    private final String lastName;

    @Username
    private final String username;

    @Email
    private final String email;

    @Password
    private final String password;

    private final String passwordConfirmation;
}
