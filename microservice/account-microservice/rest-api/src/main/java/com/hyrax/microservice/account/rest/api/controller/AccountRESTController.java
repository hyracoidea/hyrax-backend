package com.hyrax.microservice.account.rest.api.controller;

import com.hyrax.microservice.account.rest.api.domain.request.AccountRequest;
import com.hyrax.microservice.account.rest.api.domain.response.ErrorResponse;
import com.hyrax.microservice.account.rest.api.domain.response.RequestValidationResponse;
import com.hyrax.microservice.account.rest.api.domain.response.SecuredAccountResponse;
import com.hyrax.microservice.account.rest.api.domain.response.UsernameWrapperResponse;
import com.hyrax.microservice.account.rest.api.exception.RequestValidationException;
import com.hyrax.microservice.account.rest.api.exception.ResourceNotFoundException;
import com.hyrax.microservice.account.rest.api.validation.bindingresult.BindingResultProcessor;
import com.hyrax.microservice.account.rest.api.validation.bindingresult.ProcessedBindingResult;
import com.hyrax.microservice.account.service.api.AccountService;
import com.hyrax.microservice.account.service.domain.Account;
import com.hyrax.microservice.account.service.exception.AccountAlreadyExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.List;
import java.util.Optional;

@RestController
public class AccountRESTController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountRESTController.class);

    private final AccountService accountService;

    private final ConversionService conversionService;

    private final BindingResultProcessor bindingResultProcessor;

    @Autowired
    public AccountRESTController(final AccountService accountService, final ConversionService conversionService, final BindingResultProcessor bindingResultProcessor) {
        this.accountService = accountService;
        this.conversionService = conversionService;
        this.bindingResultProcessor = bindingResultProcessor;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(path = "/admin/account/{username}")
    public ResponseEntity<SecuredAccountResponse> retrieveSecuredAccountResponse(@PathVariable final String username) {
        LOGGER.info("Received username={} for retrieving secured account response", username);

        final Optional<Account> account = accountService.findAccountByUsername(username);

        if (account.isPresent()) {
            return ResponseEntity.ok(conversionService.convert(account.get(), SecuredAccountResponse.class));
        } else {
            final String message = String.format("Account not found with this username=%s", username);
            LOGGER.error(message);
            throw new ResourceNotFoundException(message);
        }
    }

    @GetMapping(path = "/account/usernames")
    public ResponseEntity<UsernameWrapperResponse> retrieveAllUsernames() {
        ResponseEntity<UsernameWrapperResponse> response = ResponseEntity.noContent().build();

        final List<String> usernames = accountService.findAllUsernames();
        if (!usernames.isEmpty()) {
            response = ResponseEntity.ok(UsernameWrapperResponse.builder()
                    .usernames(usernames)
                    .build()
            );
        }
        return response;
    }

    @PostMapping(path = "/account")
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

