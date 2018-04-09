package com.hyrax.microservice.email.rest.api.processor;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.hyrax.client.account.api.response.AccountDetailsResponse;
import com.hyrax.client.account.api.service.AccountRESTService;
import com.hyrax.microservice.email.rest.api.domain.request.EmailNotificationRequest;
import com.hyrax.microservice.email.service.api.checker.BaseEventCategory;
import com.hyrax.microservice.email.service.api.checker.SubEventCategory;
import com.hyrax.microservice.email.service.api.model.EmailNotificationDetail;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class EmailNotificationDetailsConverter {

    private final AccountRESTService accountRESTService;

    public Set<EmailNotificationDetail> convert(final EmailNotificationRequest emailNotificationRequest) {
        final Map<String, String> usernameAndEmailAddressMap = getUsernameAndEmailAddressMap(emailNotificationRequest.getRecipientUsernames());

        final Set<EmailNotificationDetail> emailNotificationDetails = Sets.newHashSet();
        emailNotificationRequest.getRecipientUsernames()
                .forEach(username -> emailNotificationDetails.add(build(emailNotificationRequest, username, usernameAndEmailAddressMap.get(username))));
        return emailNotificationDetails;
    }

    private Map<String, String> getUsernameAndEmailAddressMap(final List<String> usernames) {
        return accountRESTService.getAccountDetailsResponses(usernames)
                .map(accountDetailsResponseWrapper -> accountDetailsResponseWrapper.getAccountDetailsResponses())
                .orElse(Collections.emptyList())
                .parallelStream()
                .collect(Collectors.toMap(AccountDetailsResponse::getUsername, AccountDetailsResponse::getEmail));
    }

    private EmailNotificationDetail build(final EmailNotificationRequest emailNotificationRequest, final String username, final String emailAddress) {
        return EmailNotificationDetail.builder()
                .senderEmailAddress(emailNotificationRequest.getSenderEmailAddress())
                .recipientUsername(username)
                .recipientEmailAddress(emailAddress)
                .subject(emailNotificationRequest.getSubject())
                .model(populateAndGetModel(username, emailNotificationRequest.getModel()))
                .baseEventCategory(BaseEventCategory.valueOf(emailNotificationRequest.getBaseTemplate().name()))
                .subEventCategory(SubEventCategory.valueOf(emailNotificationRequest.getSubTemplate().name()))
                .build();
    }

    private Map<String, String> populateAndGetModel(final String username, final Map<String, String> model) {
        final Map<String, String> result = Maps.newHashMap(model);
        result.put("username", username);
        return result;
    }


}
