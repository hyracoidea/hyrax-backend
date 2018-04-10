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
public class WatchedTaskEventEmailSenderHelper extends AbstractEmailSenderHelper {

    private static final String WATCHED_TASK_ASSIGN_LABEL_EMAIL_SUBJECT = "Watched task assign label email";

    private static final String WATCHED_TASK_ASSIGN_USER_EMAIL_SUBJECT = "Watched task assign user email";

    private static final String WATCHED_TASK_MOVE_BETWEEN_COLUMNS_EMAIL_SUBJECT = "Watched task move between columns email";

    private static final String WATCHED_TASK_REMOVE_LABEL_SUBJECT = "Watched task remove label email";

    private static final String WATCHED_TASK_UNWATCH_SUBJECT = "Watched task unwatch email";

    private static final String WATCHED_TASK_UPDATE_SUBJECT = "Watched task update email";

    private static final String WATCHED_TASK_WATCH_SUBJECT = "Watched task watch email";

    private static final String BOARD_NAME = "boardName";

    private static final String COLUMN_NAME = "columnName";

    private static final String TASK_ID = "taskId";

    private static final String PREVIOUS_TASK_TITLE = "previousTaskTitle";

    private static final String PREVIOUS_TASK_DESCRIPTION = "previousTaskDescription";

    private static final String NEW_TASK_TITLE = "newTaskTitle";

    private static final String NEW_TASK_DESCRIPTION = "newTaskDescription";

    private static final String TASK_TITLE = "taskTitle";

    private static final String REMOVED_LABEL_NAME = "removedLabelName";

    private static final String PREVIOUS_COLUMN_NAME = "previousColumnName";

    private static final String NEW_COLUMN_NAME = "newColumnName";

    private static final String PREVIOUSLY_ASSIGNED_USERNAME = "previouslyAssignedUsername";

    private static final String NEW_ASSIGNED_USERNAME = "newAssignedUsername";

    private static final String ASSIGNED_LABEL_NAME = "assignedLabelName";


    private final EmailSenderRESTService emailSenderRESTService;

    @Async
    public void sendWatchedTaskAssignLabelEmail(final Set<String> watchedUsernames, final String boardName, final Long taskId, final String taskTitle,
                                                final String assignedLabelName, final String requestedBy) {
        final EmailNotificationRequest emailNotificationRequest = EmailNotificationRequest.builder()
                .senderEmailAddress(SENDER_EMAIL_ADDRESS)
                .recipientUsernames(watchedUsernames)
                .subject(WATCHED_TASK_ASSIGN_LABEL_EMAIL_SUBJECT)
                .baseTemplate(BaseTemplate.WATCHED_TASK)
                .subTemplate(SubTemplate.ASSIGN_LABEL)
                .model(createModelForWatchedTaskAssignLabel(boardName, taskId, taskTitle, assignedLabelName, requestedBy))
                .build();
        emailSenderRESTService.sendEmail(emailNotificationRequest);
    }

    @Async
    public void sendWatchedTaskAssignUserEmail(final Set<String> watchedUsernames, final String boardName, final Long taskId, final String taskTitle,
                                               final String previouslyAssignedUsername, final String newAssignedUsername, final String requestedBy) {
        final EmailNotificationRequest emailNotificationRequest = EmailNotificationRequest.builder()
                .senderEmailAddress(SENDER_EMAIL_ADDRESS)
                .recipientUsernames(watchedUsernames)
                .subject(WATCHED_TASK_ASSIGN_USER_EMAIL_SUBJECT)
                .baseTemplate(BaseTemplate.WATCHED_TASK)
                .subTemplate(SubTemplate.ASSIGN_USER)
                .model(createModelForWatchedTaskAssignUser(boardName, taskId, taskTitle, previouslyAssignedUsername, newAssignedUsername, requestedBy))
                .build();
        emailSenderRESTService.sendEmail(emailNotificationRequest);
    }

    @Async
    public void sendWatchedTaskMoveBetweenColumnsEmail(final Set<String> watchedUsernames, final String boardName, final Long taskId, final String taskTitle,
                                                       final String previousColumnName, final String newColumnName, final String requestedBy) {
        final EmailNotificationRequest emailNotificationRequest = EmailNotificationRequest.builder()
                .senderEmailAddress(SENDER_EMAIL_ADDRESS)
                .recipientUsernames(watchedUsernames)
                .subject(WATCHED_TASK_MOVE_BETWEEN_COLUMNS_EMAIL_SUBJECT)
                .baseTemplate(BaseTemplate.WATCHED_TASK)
                .subTemplate(SubTemplate.MOVE_BETWEEN_COLUMNS)
                .model(createModelForWatchedTaskMoveBetweenColumns(boardName, taskId, taskTitle, previousColumnName, newColumnName, requestedBy))
                .build();
        emailSenderRESTService.sendEmail(emailNotificationRequest);
    }

    @Async
    public void sendWatchedTaskRemoveLabelEmail(final Set<String> watchedUsernames, final String boardName, final Long taskId, final String taskTitle,
                                                final String removedLabelName, final String requestedBy) {
        final EmailNotificationRequest emailNotificationRequest = EmailNotificationRequest.builder()
                .senderEmailAddress(SENDER_EMAIL_ADDRESS)
                .recipientUsernames(watchedUsernames)
                .subject(WATCHED_TASK_REMOVE_LABEL_SUBJECT)
                .baseTemplate(BaseTemplate.WATCHED_TASK)
                .subTemplate(SubTemplate.REMOVE_LABEL)
                .model(createModelForWatchedTaskRemoveLabel(boardName, taskId, taskTitle, removedLabelName, requestedBy))
                .build();
        emailSenderRESTService.sendEmail(emailNotificationRequest);
    }

    @Async
    public void sendWatchedTaskWatchEmail(final Set<String> watchedUsernames, final String boardName, final Long taskId, final String taskTitle, final String requestedBy) {
        final EmailNotificationRequest emailNotificationRequest = EmailNotificationRequest.builder()
                .senderEmailAddress(SENDER_EMAIL_ADDRESS)
                .recipientUsernames(watchedUsernames)
                .subject(WATCHED_TASK_WATCH_SUBJECT)
                .baseTemplate(BaseTemplate.WATCHED_TASK)
                .subTemplate(SubTemplate.WATCH)
                .model(createModelForWatchedTaskWatchOrUnwatch(boardName, taskId, taskTitle, requestedBy))
                .build();
        emailSenderRESTService.sendEmail(emailNotificationRequest);
    }

    @Async
    public void sendWatchedTaskUnwatchEmail(final Set<String> watchedUsernames, final String boardName, final Long taskId, final String taskTitle, final String requestedBy) {
        final EmailNotificationRequest emailNotificationRequest = EmailNotificationRequest.builder()
                .senderEmailAddress(SENDER_EMAIL_ADDRESS)
                .recipientUsernames(watchedUsernames)
                .subject(WATCHED_TASK_UNWATCH_SUBJECT)
                .baseTemplate(BaseTemplate.WATCHED_TASK)
                .subTemplate(SubTemplate.UNWATCH)
                .model(createModelForWatchedTaskWatchOrUnwatch(boardName, taskId, taskTitle, requestedBy))
                .build();
        emailSenderRESTService.sendEmail(emailNotificationRequest);
    }

    @Async
    public void sendWatchedTaskUpdateEmail(final Set<String> watchedUsernames, final String boardName, final String columnName, final Long taskId, final String previousTaskTitle,
                                           final String previousTaskDescription, final String newTaskTitle, final String newTaskDescription, final String requestedBy) {
        final EmailNotificationRequest emailNotificationRequest = EmailNotificationRequest.builder()
                .senderEmailAddress(SENDER_EMAIL_ADDRESS)
                .recipientUsernames(watchedUsernames)
                .subject(WATCHED_TASK_UPDATE_SUBJECT)
                .baseTemplate(BaseTemplate.WATCHED_TASK)
                .subTemplate(SubTemplate.UPDATE)
                .model(createModelForWatchedTaskUpdate(boardName, columnName, taskId, previousTaskTitle, previousTaskDescription, newTaskTitle, newTaskDescription, requestedBy))
                .build();
        emailSenderRESTService.sendEmail(emailNotificationRequest);
    }

    private Map<String, String> createModelForWatchedTaskAssignLabel(final String boardName, final Long taskId, final String taskTitle,
                                                                     final String assignedLabelName, final String requestedBy) {
        final Map<String, String> model = Maps.newHashMap();
        model.put(BOARD_NAME, boardName);
        model.put(TASK_ID, String.valueOf(taskId));
        model.put(TASK_TITLE, taskTitle);
        model.put(ASSIGNED_LABEL_NAME, assignedLabelName);
        model.put(REQUESTED_BY, requestedBy);
        return model;
    }

    private Map<String, String> createModelForWatchedTaskAssignUser(final String boardName, final Long taskId, final String taskTitle,
                                                                    final String previouslyAssignedUsername, final String newAssignedUsername, final String requestedBy) {
        final Map<String, String> model = Maps.newHashMap();
        model.put(BOARD_NAME, boardName);
        model.put(TASK_ID, String.valueOf(taskId));
        model.put(TASK_TITLE, taskTitle);
        model.put(PREVIOUSLY_ASSIGNED_USERNAME, previouslyAssignedUsername);
        model.put(NEW_ASSIGNED_USERNAME, newAssignedUsername);
        model.put(REQUESTED_BY, requestedBy);
        return model;
    }

    private Map<String, String> createModelForWatchedTaskMoveBetweenColumns(final String boardName, final Long taskId, final String taskTitle,
                                                                            final String previousColumnName, final String newColumnName, final String requestedBy) {
        final Map<String, String> model = Maps.newHashMap();
        model.put(BOARD_NAME, boardName);
        model.put(TASK_ID, String.valueOf(taskId));
        model.put(TASK_TITLE, taskTitle);
        model.put(PREVIOUS_COLUMN_NAME, previousColumnName);
        model.put(NEW_COLUMN_NAME, newColumnName);
        model.put(REQUESTED_BY, requestedBy);
        return model;
    }

    private Map<String, String> createModelForWatchedTaskRemoveLabel(final String boardName, final Long taskId, final String taskTitle,
                                                                     final String removedLabelName, final String requestedBy) {
        final Map<String, String> model = Maps.newHashMap();
        model.put(BOARD_NAME, boardName);
        model.put(TASK_ID, String.valueOf(taskId));
        model.put(TASK_TITLE, taskTitle);
        model.put(REMOVED_LABEL_NAME, removedLabelName);
        model.put(REQUESTED_BY, requestedBy);
        return model;
    }

    private Map<String, String> createModelForWatchedTaskWatchOrUnwatch(final String boardName, final Long taskId, final String taskTitle, final String requestedBy) {
        final Map<String, String> model = Maps.newHashMap();
        model.put(BOARD_NAME, boardName);
        model.put(TASK_ID, String.valueOf(taskId));
        model.put(TASK_TITLE, taskTitle);
        model.put(REQUESTED_BY, requestedBy);
        return model;
    }

    private Map<String, String> createModelForWatchedTaskUpdate(final String boardName, final String columnName, final Long taskId, final String previousTaskTitle,
                                                                final String previousTaskDescription, final String newTaskTitle,
                                                                final String newTaskDescription, final String requestedBy) {
        final Map<String, String> model = Maps.newHashMap();
        model.put(BOARD_NAME, boardName);
        model.put(COLUMN_NAME, columnName);
        model.put(TASK_ID, String.valueOf(taskId));
        model.put(PREVIOUS_TASK_TITLE, previousTaskTitle);
        model.put(PREVIOUS_TASK_DESCRIPTION, previousTaskDescription);
        model.put(NEW_TASK_TITLE, newTaskTitle);
        model.put(NEW_TASK_DESCRIPTION, newTaskDescription);
        model.put(REQUESTED_BY, requestedBy);
        return model;
    }
}
