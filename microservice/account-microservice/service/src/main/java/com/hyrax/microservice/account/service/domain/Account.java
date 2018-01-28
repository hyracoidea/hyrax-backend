package com.hyrax.microservice.account.service.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "passwordHash")
public class Account {

    private String firstName;

    private String lastName;

    private String email;

    private String passwordHash;
}