package com.hyrax.spring.boot.starter.authentication.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SecuredAccountDetails {

    private String username;
    private String password;
    private String authority;
}
