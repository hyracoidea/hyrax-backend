package com.hyrax.microservice.account.rest.api.controller;

import com.hyrax.microservice.account.rest.api.domain.request.AccountDetailsRequest;
import com.hyrax.microservice.account.rest.api.domain.request.AccountRequest;
import com.hyrax.microservice.account.rest.api.domain.response.AccountDetailsResponse;
import com.hyrax.microservice.account.rest.api.domain.response.AccountDetailsResponseWrapper;
import com.hyrax.microservice.account.rest.api.domain.response.ErrorResponse;
import com.hyrax.microservice.account.rest.api.domain.response.RequestValidationResponse;
import com.hyrax.microservice.account.rest.api.domain.response.SecuredAccountResponse;
import com.hyrax.microservice.account.rest.api.exception.RequestValidationException;
import com.hyrax.microservice.account.rest.api.exception.ResourceNotFoundException;
import com.hyrax.microservice.account.rest.api.security.AuthenticationUserDetailsHelper;
import com.hyrax.microservice.account.rest.api.validation.bindingresult.BindingResultProcessor;
import com.hyrax.microservice.account.rest.api.validation.bindingresult.ProcessedBindingResult;
import com.hyrax.microservice.account.service.api.AccountService;
import com.hyrax.microservice.account.service.domain.Account;
import com.hyrax.microservice.account.service.exception.AccountAlreadyExistsException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;
import java.util.stream.Collectors;

@Api(description = "Operations about accounts")
@RestController
@AllArgsConstructor
public class AccountRESTController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountRESTController.class);

    private static final String ACCOUNT_NOT_FOUND_TEMPLATE_MESSAGE = "Account not found with this username=%s";

    private final AccountService accountService;

    private final AuthenticationUserDetailsHelper authenticationUserDetailsHelper;

    private final ConversionService conversionService;

    private final BindingResultProcessor bindingResultProcessor;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(path = "/admin/account/{username}")
    public ResponseEntity<SecuredAccountResponse> retrieveSecuredAccountResponse(@PathVariable final String username) {
        LOGGER.info("Received username={} for retrieving secured account response", username);

        final Optional<Account> account = accountService.findAccountByUsername(username);

        if (account.isPresent()) {
            return ResponseEntity.ok(conversionService.convert(account.get(), SecuredAccountResponse.class));
        } else {
            final String message = String.format(ACCOUNT_NOT_FOUND_TEMPLATE_MESSAGE, username);
            LOGGER.error(message);
            throw new ResourceNotFoundException(message);
        }
    }

    @GetMapping(path = "/account/details/me")
    @ApiOperation(httpMethod = "GET", value = "Resource to get details about me")
    public ResponseEntity<AccountDetailsResponse> retrieveDetailsAboutMe() {
        final String requestedBy = authenticationUserDetailsHelper.getUsername();
        final AccountDetailsResponse response = accountService.findAccountByUsername(requestedBy)
                .map(account -> conversionService.convert(account, AccountDetailsResponse.class))
                .orElseThrow(() -> new ResourceNotFoundException(String.format(ACCOUNT_NOT_FOUND_TEMPLATE_MESSAGE, requestedBy)));

        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/account/details/{username}")
    @ApiOperation(httpMethod = "GET", value = "Resource to get details about the given user")
    public ResponseEntity<AccountDetailsResponse> retrieveDetails(@PathVariable final String username) {
        final AccountDetailsResponse response = accountService.findAccountByUsername(username)
                .map(account -> conversionService.convert(account, AccountDetailsResponse.class))
                .orElseThrow(() -> new ResourceNotFoundException(String.format(ACCOUNT_NOT_FOUND_TEMPLATE_MESSAGE, username)));

        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "/account/details")
    @ApiOperation(httpMethod = "POST", value = "Resource to get details about the given user")
    public ResponseEntity<AccountDetailsResponseWrapper> retrieveDetails(@RequestBody final AccountDetailsRequest accountDetailsRequest) {
        final AccountDetailsResponseWrapper responseWrapper = AccountDetailsResponseWrapper.builder()
                .accountDetailsResponses(accountService.findAllByUsernames(accountDetailsRequest.getUsernames())
                .parallelStream()
                .map(account -> conversionService.convert(account, AccountDetailsResponse.class))
                .collect(Collectors.toList()))
                .build();
        return ResponseEntity.ok(responseWrapper);
    }

    @PostMapping(path = "/account")
    @ApiOperation(httpMethod = "POST", value = "Resource to create a new account")
    public ResponseEntity<Void> createAccount(@Valid @RequestBody final AccountRequest accountRequest, final BindingResult bindingResult) {
        LOGGER.info("Received account request for creation: {}", accountRequest);

        final ProcessedBindingResult processedBindingResult = bindingResultProcessor.process(bindingResult);

        if (!processedBindingResult.hasValidationErrors()) {
            accountService.saveAccount(conversionService.convert(accountRequest, Account.class));
        } else {
            throw new RequestValidationException(processedBindingResult);
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @ExceptionHandler(RequestValidationException.class)
    protected ResponseEntity<RequestValidationResponse> handleRequestValidationException(final RequestValidationException e) {
        logException(e);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(RequestValidationResponse.builder()
                        .requestValidationDetails(e.getProcessedBindingResult().getRequestValidationDetails())
                        .build()
                );
    }

    @ExceptionHandler(AccountAlreadyExistsException.class)
    protected ResponseEntity<ErrorResponse> handleAccountAlreadyExistsException(final AccountAlreadyExistsException e) {
        logException(e);
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ErrorResponse.builder()
                        .message(e.getMessage())
                        .build()
                );
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    protected ResponseEntity<ErrorResponse> handleResourceNotFoundException(final ResourceNotFoundException e) {
        logException(e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.builder()
                        .message(e.getMessage())
                        .build()
                );
    }

    private void logException(final Exception e) {
        LOGGER.error(e.getMessage(), e);
    }

}

