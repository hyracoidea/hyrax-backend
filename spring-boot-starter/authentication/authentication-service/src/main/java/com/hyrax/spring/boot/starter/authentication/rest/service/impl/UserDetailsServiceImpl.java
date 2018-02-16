package com.hyrax.spring.boot.starter.authentication.rest.service.impl;

import com.hyrax.spring.boot.starter.authentication.model.SecuredAccountDetails;
import com.hyrax.spring.boot.starter.authentication.rest.service.SecuredAccountRESTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final SecuredAccountRESTService securedAccountRESTService;

    @Autowired
    public UserDetailsServiceImpl(final SecuredAccountRESTService securedAccountRESTService) {
        this.securedAccountRESTService = securedAccountRESTService;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final SecuredAccountDetails accountDetails = securedAccountRESTService.retrieveSecuredAccount(username).orElseThrow(() -> new UsernameNotFoundException(username));
        return new User(accountDetails.getUsername(), accountDetails.getPassword(), Collections.singletonList(() -> accountDetails.getAuthority()));
    }
}