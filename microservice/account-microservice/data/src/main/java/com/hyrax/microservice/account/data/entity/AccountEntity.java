package com.hyrax.microservice.account.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString(exclude = "passwordHash")
public class AccountEntity {

    private Long accountId;

    private String firstName;

    private String lastName;

    private String email;

    private String passwordHash;
}
