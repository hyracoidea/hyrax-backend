package com.hyrax.microservice.project.service.api.impl.helper;


import com.google.common.collect.Maps;
import com.hyrax.client.email.api.domain.BaseTemplate;
import com.hyrax.client.email.api.domain.SubTemplate;
import com.hyrax.client.email.api.request.EmailNotificationRequest;
import com.hyrax.client.email.api.service.EmailSenderRESTService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

@Component
@AllArgsConstructor
public class LabelEventEmailSenderHelper extends AbstractEmailSenderHelper {

    private static final String LABEL_CREATION_EMAIL_SUBJECT = "Label creation email";

    private static final String LABEL_REMOVAL_EMAIL_SUBJECT = "Label removal email";

    private static final String BOARD_NAME = "boardName";

    private static final String LABEL_NAME = "labelName";

    private final EmailSenderRESTService emailSenderRESTService;

    @Async
    public void sendLabelCreationEmail(final Set<String> boardMemberUsernames, final String boardName, final String labelName, final String requestedBy) {
        final EmailNotificationRequest emailNotificationRequest = EmailNotificationRequest.builder()
                .senderEmailAddress(SENDER_EMAIL_ADDRESS)
                .recipientUsernames(boardMemberUsernames)
                .subject(LABEL_CREATION_EMAIL_SUBJECT)
                .baseTemplate(BaseTemplate.LABEL)
                .subTemplate(SubTemplate.CREATION)
                .model(createModelForLabel(boardName, labelName, requestedBy))
                .build();
        emailSenderRESTService.sendEmail(emailNotificationRequest);
    }

    @Async
    public void sendLabelRemovalEmail(final Set<String> boardMemberUsernames, final String boardName, final String labelName, final String requestedBy) {
        final EmailNotificationRequest emailNotificationRequest = EmailNotificationRequest.builder()
                .senderEmailAddress(SENDER_EMAIL_ADDRESS)
                .recipientUsernames(boardMemberUsernames)
                .subject(LABEL_REMOVAL_EMAIL_SUBJECT)
                .baseTemplate(BaseTemplate.LABEL)
                .subTemplate(SubTemplate.REMOVAL)
                .model(createModelForLabel(boardName, labelName, requestedBy))
                .build();
        emailSenderRESTService.sendEmail(emailNotificationRequest);
    }

    private Map<String, String> createModelForLabel(final String boardName, final String labelName, final String requestedBy) {
        final Map<String, String> model = Maps.newHashMap();
        model.put(BOARD_NAME, boardName);
        model.put(LABEL_NAME, labelName);
        model.put(REQUESTED_BY, requestedBy);
        return model;
    }
}
