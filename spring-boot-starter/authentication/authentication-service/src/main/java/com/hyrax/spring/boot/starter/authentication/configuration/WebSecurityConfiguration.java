package com.hyrax.spring.boot.starter.authentication.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyrax.spring.boot.starter.authentication.exceptionhandling.Http401AuthenticationEntryPoint;
import com.hyrax.spring.boot.starter.authentication.exceptionhandling.Http403AccessDeniedHandler;
import com.hyrax.spring.boot.starter.authentication.jwt.JWTTokenGenerator;
import com.hyrax.spring.boot.starter.authentication.jwt.JWTTokenParser;
import com.hyrax.spring.boot.starter.authentication.jwt.filter.JWTAuthenticationFilter;
import com.hyrax.spring.boot.starter.authentication.jwt.filter.JWTAuthorizationFilter;
import com.hyrax.spring.boot.starter.authentication.properties.AuthenticationAdminProperties;
import com.hyrax.spring.boot.starter.authentication.properties.AuthenticationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final String[] AUTH_WHITELIST = {
            // -- swagger ui
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**"
    };

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private AuthenticationProperties authenticationProperties;

    @Autowired
    private AuthenticationAdminProperties authenticationAdminProperties;

    @Autowired
    private JWTTokenGenerator jwtTokenGenerator;

    @Autowired
    private JWTTokenParser jwtTokenParser;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers(AUTH_WHITELIST).permitAll()
                .antMatchers(HttpMethod.POST, authenticationProperties.getSignUpUrl()).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager(), authenticationProperties, jwtTokenGenerator, objectMapper))
                .addFilter(new JWTAuthorizationFilter(authenticationManager(), authenticationProperties, jwtTokenParser))
                .exceptionHandling().authenticationEntryPoint(new Http401AuthenticationEntryPoint()).accessDeniedHandler(new Http403AccessDeniedHandler())
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }


    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser(authenticationAdminProperties.getUsername())
                .password(authenticationAdminProperties.getPassword())
                .authorities(authenticationAdminProperties.getAuthority());
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }
}