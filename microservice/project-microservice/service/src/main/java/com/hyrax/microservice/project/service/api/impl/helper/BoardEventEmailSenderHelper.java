package com.hyrax.microservice.project.service.api.impl.helper;


import com.google.common.collect.Lists;
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
public class BoardEventEmailSenderHelper extends AbstractEmailSenderHelper {

    private static final String BOARD_CREATION_EMAIL_SUBJECT = "Board creation email";

    private static final String BOARD_REMOVAL_EMAIL_SUBJECT = "Board removal email";

    private static final String BOARD_MEMBER_ADDITION_EMAIL_SUBJECT = "Board member addition email";

    private static final String BOARD_MEMBER_REMOVAL_EMAIL_SUBJECT = "Board member removal email";

    private static final String BOARD_NAME = "boardName";

    private static final String NEW_BOARD_MEMBER_USERNAME = "newBoardMemberUsername";

    private static final String DELETED_BOARD_MEMBER_USERNAME = "deletedBoardMemberUsername";

    private final EmailSenderRESTService emailSenderRESTService;

    @Async
    public void sendBoardCreationEmail(final String username, final String boardName) {
        final EmailNotificationRequest emailNotificationRequest = EmailNotificationRequest.builder()
                .senderEmailAddress(SENDER_EMAIL_ADDRESS)
                .recipientUsernames(Lists.newArrayList(username))
                .subject(BOARD_CREATION_EMAIL_SUBJECT)
                .baseTemplate(BaseTemplate.BOARD)
                .subTemplate(SubTemplate.CREATION)
                .model(createModelForBoardCreation(boardName))
                .build();
        emailSenderRESTService.sendEmail(emailNotificationRequest);
    }

    @Async
    public void sendBoarddRemovalEmail(final Set<String> boardMemberUsernames, final String boardName, final String requestedBy) {
        final EmailNotificationRequest emailNotificationRequest = EmailNotificationRequest.builder()
                .senderEmailAddress(SENDER_EMAIL_ADDRESS)
                .recipientUsernames(boardMemberUsernames)
                .subject(BOARD_REMOVAL_EMAIL_SUBJECT)
                .baseTemplate(BaseTemplate.BOARD)
                .subTemplate(SubTemplate.REMOVAL)
                .model(createModelForBoardDeletion(boardName, requestedBy))
                .build();
        emailSenderRESTService.sendEmail(emailNotificationRequest);
    }

    @Async
    public void sendBoardMemberAdditionEmail(final Set<String> boardMemberUsernames, final String boardName, final String newBoardMemberUsername, final String requestedBy) {
        final EmailNotificationRequest emailNotificationRequest = EmailNotificationRequest.builder()
                .senderEmailAddress(SENDER_EMAIL_ADDRESS)
                .recipientUsernames(boardMemberUsernames)
                .subject(BOARD_MEMBER_ADDITION_EMAIL_SUBJECT)
                .baseTemplate(BaseTemplate.BOARD)
                .subTemplate(SubTemplate.MEMBER_ADDITION)
                .model(createModelForBoardMemberAddition(boardName, newBoardMemberUsername, requestedBy))
                .build();
        emailSenderRESTService.sendEmail(emailNotificationRequest);
    }

    @Async
    public void sendBoardMemberRemovalEmail(final Set<String> boardMemberUsernames, final String boardName, final String deletedBoardMemberUsername, final String requestedBy) {
        final EmailNotificationRequest emailNotificationRequest = EmailNotificationRequest.builder()
                .senderEmailAddress(SENDER_EMAIL_ADDRESS)
                .recipientUsernames(boardMemberUsernames)
                .subject(BOARD_MEMBER_REMOVAL_EMAIL_SUBJECT)
                .baseTemplate(BaseTemplate.BOARD)
                .subTemplate(SubTemplate.MEMBER_REMOVAL)
                .model(createModelForBoardMemberRemoval(boardName, deletedBoardMemberUsername, requestedBy))
                .build();
        emailSenderRESTService.sendEmail(emailNotificationRequest);
    }


    private Map<String, String> createModelForBoardCreation(final String boardName) {
        final Map<String, String> model = Maps.newHashMap();
        model.put(BOARD_NAME, boardName);
        return model;
    }

    private Map<String, String> createModelForBoardDeletion(final String boardName, final String requestedBy) {
        final Map<String, String> model = Maps.newHashMap();
        model.put(BOARD_NAME, boardName);
        model.put(REQUESTED_BY, requestedBy);
        return model;
    }

    private Map<String, String> createModelForBoardMemberAddition(final String boardName, final String newBoardMemberUsername, final String requestedBy) {
        final Map<String, String> model = Maps.newHashMap();
        model.put(BOARD_NAME, boardName);
        model.put(REQUESTED_BY, requestedBy);
        model.put(NEW_BOARD_MEMBER_USERNAME, newBoardMemberUsername);
        return model;
    }

    private Map<String, String> createModelForBoardMemberRemoval(final String boardName, final String deletedBoardMemberUsername, final String requestedBy) {
        final Map<String, String> model = Maps.newHashMap();
        model.put(BOARD_NAME, boardName);
        model.put(REQUESTED_BY, requestedBy);
        model.put(DELETED_BOARD_MEMBER_USERNAME, deletedBoardMemberUsername);
        return model;
    }


}
