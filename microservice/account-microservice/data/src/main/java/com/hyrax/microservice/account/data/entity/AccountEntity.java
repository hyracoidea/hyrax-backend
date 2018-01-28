package com.hyrax.microservice.account.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true, exclude = "passwordHash")
public class AccountEntity extends AbstractEntity<Long> {

    private String firstName;

    private String lastName;

    private String email;

    private String passwordHash;

    @Builder
    public AccountEntity(final Long id, final String firstName, final String lastName, final String email, final String passwordHash) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.passwordHash = passwordHash;
    }
}
