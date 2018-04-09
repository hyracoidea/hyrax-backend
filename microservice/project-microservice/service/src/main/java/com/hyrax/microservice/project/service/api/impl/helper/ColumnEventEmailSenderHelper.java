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
public class ColumnEventEmailSenderHelper extends AbstractEmailSenderHelper {

    private static final String COLUMN_CREATION_EMAIL_SUBJECT = "Column creation email";

    private static final String COLUMN_REMOVAL_EMAIL_SUBJECT = "Column removal email";

    private static final String BOARD_NAME = "boardName";

    private static final String COLUMN_NAME = "columnName";

    private final EmailSenderRESTService emailSenderRESTService;

    @Async
    public void sendColumnCreationEmail(final Set<String> boardMemberUsernames, final String boardName, final String columnName, final String requestedBy) {
        final EmailNotificationRequest emailNotificationRequest = EmailNotificationRequest.builder()
                .senderEmailAddress(SENDER_EMAIL_ADDRESS)
                .recipientUsernames(boardMemberUsernames)
                .subject(COLUMN_CREATION_EMAIL_SUBJECT)
                .baseTemplate(BaseTemplate.COLUMN)
                .subTemplate(SubTemplate.CREATION)
                .model(createModelForColumn(boardName, columnName, requestedBy))
                .build();
        emailSenderRESTService.sendEmail(emailNotificationRequest);
    }

    @Async
    public void sendColumnRemovalEmail(final Set<String> boardMemberUsernames, final String boardName, final String columnName, final String requestedBy) {
        final EmailNotificationRequest emailNotificationRequest = EmailNotificationRequest.builder()
                .senderEmailAddress(SENDER_EMAIL_ADDRESS)
                .recipientUsernames(boardMemberUsernames)
                .subject(COLUMN_REMOVAL_EMAIL_SUBJECT)
                .baseTemplate(BaseTemplate.COLUMN)
                .subTemplate(SubTemplate.REMOVAL)
                .model(createModelForColumn(boardName, columnName, requestedBy))
                .build();
        emailSenderRESTService.sendEmail(emailNotificationRequest);
    }

    private Map<String, String> createModelForColumn(final String boardName, final String columnName, final String requestedBy) {
        final Map<String, String> model = Maps.newHashMap();
        model.put(BOARD_NAME, boardName);
        model.put(COLUMN_NAME, columnName);
        model.put(REQUESTED_BY, requestedBy);
        return model;
    }
}
