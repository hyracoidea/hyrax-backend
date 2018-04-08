package com.hyrax.microservice.email.rest.api.controller;

import com.hyrax.microservice.email.rest.api.domain.request.EmailNotificationRequest;
import com.hyrax.microservice.email.rest.api.processor.EmailNotificationRequestProcessor;
import freemarker.template.TemplateException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.io.IOException;

@RestController
@RequestMapping(path = "/email")
@Api(description = "Operations about emails")
@AllArgsConstructor
public class EmailSenderRESTController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailSenderRESTController.class);

    private final EmailNotificationRequestProcessor emailNotificationRequestProcessor;

    @PostMapping
    @ApiOperation(httpMethod = "POST", value = "Resource to send email")
    public ResponseEntity<Void> sendEmail(@RequestBody final EmailNotificationRequest emailNotificationRequest) {
        LOGGER.info("Received emailNotificationRequest={}", emailNotificationRequest);

        emailNotificationRequestProcessor.process(emailNotificationRequest);

        LOGGER.info("Sending emails were successful");

        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler({IOException.class, TemplateException.class, MessagingException.class, RuntimeException.class})
    protected ResponseEntity<Void> handleBadRequest(final Exception e) {
        logException(e);
        return ResponseEntity.badRequest().build();
    }

    private void logException(final Exception e) {
        LOGGER.error(e.getMessage(), e);
    }
}
