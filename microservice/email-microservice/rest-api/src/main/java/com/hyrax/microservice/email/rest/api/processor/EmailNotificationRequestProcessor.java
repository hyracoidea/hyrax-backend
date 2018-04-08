package com.hyrax.microservice.email.rest.api.processor;

import com.hyrax.microservice.email.rest.api.domain.request.EmailNotificationRequest;
import com.hyrax.microservice.email.service.api.EmailService;
import com.hyrax.microservice.email.service.api.checker.SubscriptionStatusChecker;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class EmailNotificationRequestProcessor {

    private final EmailNotificationDetailsConverter emailNotificationDetailsConverter;

    private final EmailDetailConverter emailDetailConverter;

    private final SubscriptionStatusChecker subscriptionStatusChecker;

    private final EmailService emailService;

    public void process(final EmailNotificationRequest emailNotificationRequest) {
        emailNotificationDetailsConverter.convert(emailNotificationRequest)
                .parallelStream()
                .filter(emailNotificationDetail -> subscriptionStatusChecker.isSubscribed(emailNotificationDetail.getRecipientUsername(),
                        emailNotificationDetail.getBaseEventCategory(), emailNotificationDetail.getSubEventCategory()))
                .map(emailDetailConverter::convert)
                .forEach(emailService::sendEmail);
    }


}
