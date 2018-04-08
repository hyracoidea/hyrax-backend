package com.hyrax.microservice.account.service.api.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hyrax.client.email.api.domain.BaseTemplate;
import com.hyrax.client.email.api.domain.SubTemplate;
import com.hyrax.client.email.api.request.EmailNotificationRequest;
import com.hyrax.client.email.api.service.EmailEventSubscriptionRESTService;
import com.hyrax.client.email.api.service.EmailSenderRESTService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@AllArgsConstructor
public class EmailEventSubscriptionHelper {

    private final Set<EmailEventSubscriptionRESTService> emailEventSubscriptionRESTServices;

    private final EmailSenderRESTService emailSenderRESTService;

    @Async
    public void createDefaultEmailEventSubscriptionsBy(final String username) {
        emailEventSubscriptionRESTServices.forEach(emailEventSubscriptionRESTService -> emailEventSubscriptionRESTService.createEventSubscription(username));
    }

    @Async
    public void sendRegistrationSuccessfulEmail(final String username) {
        final EmailNotificationRequest emailNotificationRequest = EmailNotificationRequest.builder()
                .senderEmailAddress("donotreplyhyrax@gmail.com")
                .recipientUsernames(Lists.newArrayList(username))
                .subject("Account registration email")
                .baseTemplate(BaseTemplate.ACCOUNT)
                .subTemplate(SubTemplate.REGISTRATION)
                .model(Maps.newHashMap())
                .build();
        emailSenderRESTService.sendEmail(emailNotificationRequest);
    }
}
