package com.hyrax.client.email.api.service;

import com.hyrax.client.email.api.request.EmailNotificationRequest;

public interface EmailSenderRESTService {

    void sendEmail(EmailNotificationRequest emailNotificationRequest);
}
