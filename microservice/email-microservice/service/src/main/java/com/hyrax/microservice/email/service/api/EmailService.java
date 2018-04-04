package com.hyrax.microservice.email.service.api;

import com.hyrax.microservice.email.service.api.model.EmailDetails;

import javax.mail.MessagingException;

public interface EmailService {

    void sendEmail(EmailDetails emailDetails) throws MessagingException;

}
