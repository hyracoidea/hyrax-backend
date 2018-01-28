package com.hyrax.microservice.account.rest.api.request;

import com.hyrax.microservice.account.rest.api.validation.annotation.Email;
import com.hyrax.microservice.account.rest.api.validation.annotation.FirstName;
import com.hyrax.microservice.account.rest.api.validation.annotation.LastName;
import com.hyrax.microservice.account.rest.api.validation.annotation.MatchesPassword;
import com.hyrax.microservice.account.rest.api.validation.annotation.Password;
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

    @Email
    private final String email;

    @Password
    private final String password;

    private final String passwordConfirmation;
}
