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
public class TaskEventEmailSenderHelper extends AbstractEmailSenderHelper {

    private static final String TASK_CREATION_EMAIL_SUBJECT = "Task creation email";

    private static final String TASK_REMOVAL_EMAIL_SUBJECT = "Task removal email";

    private static final String BOARD_NAME = "boardName";

    private static final String COLUMN_NAME = "columnName";

    private static final String TASK_ID = "taskId";

    private static final String TASK_TITLE = "taskTitle";

    private final EmailSenderRESTService emailSenderRESTService;

    @Async
    public void sendTaskCreationEmail(final Set<String> boardMemberUsernames, final String boardName, final String columnName, final Long taskId,
                                      final String taskTitle, final String requestedBy) {
        final EmailNotificationRequest emailNotificationRequest = EmailNotificationRequest.builder()
                .senderEmailAddress(SENDER_EMAIL_ADDRESS)
                .recipientUsernames(boardMemberUsernames)
                .subject(TASK_CREATION_EMAIL_SUBJECT)
                .baseTemplate(BaseTemplate.TASK)
                .subTemplate(SubTemplate.CREATION)
                .model(createModelForTask(boardName, columnName, taskId, taskTitle, requestedBy))
                .build();
        emailSenderRESTService.sendEmail(emailNotificationRequest);
    }

    @Async
    public void sendTaskRemovalEmail(final Set<String> boardMemberUsernames, final String boardName, final String columnName, final Long taskId,
                                     final String taskTitle, final String requestedBy) {
        final EmailNotificationRequest emailNotificationRequest = EmailNotificationRequest.builder()
                .senderEmailAddress(SENDER_EMAIL_ADDRESS)
                .recipientUsernames(boardMemberUsernames)
                .subject(TASK_REMOVAL_EMAIL_SUBJECT)
                .baseTemplate(BaseTemplate.TASK)
                .subTemplate(SubTemplate.REMOVAL)
                .model(createModelForTask(boardName, columnName, taskId, taskTitle, requestedBy))
                .build();
        emailSenderRESTService.sendEmail(emailNotificationRequest);
    }

    private Map<String, String> createModelForTask(final String boardName, final String columnName, final Long taskId, final String taskTitle, final String requestedBy) {
        final Map<String, String> model = Maps.newHashMap();
        model.put(BOARD_NAME, boardName);
        model.put(COLUMN_NAME, columnName);
        model.put(TASK_ID, String.valueOf(taskId));
        model.put(TASK_TITLE, taskTitle);
        model.put(REQUESTED_BY, requestedBy);
        return model;
    }
}
