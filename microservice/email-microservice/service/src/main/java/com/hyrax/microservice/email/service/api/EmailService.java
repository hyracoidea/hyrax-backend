package com.hyrax.microservice.email.service.api;

import com.hyrax.microservice.email.service.api.model.EmailDetail;

public interface EmailService {

    void sendEmail(EmailDetail emailDetail);

}
