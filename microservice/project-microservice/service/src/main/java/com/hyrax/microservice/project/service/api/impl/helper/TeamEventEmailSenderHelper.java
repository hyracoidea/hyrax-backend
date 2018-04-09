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
public class TeamEventEmailSenderHelper extends AbstractEmailSenderHelper {

    private static final String TEAM_CREATION_EMAIL_SUBJECT = "Team creation email";

    private static final String TEAM_REMOVAL_EMAIL_SUBJECT = "Team removal email";

    private static final String TEAM_MEMBER_ADDITION_EMAIL_SUBJECT = "Team member addition email";

    private static final String TEAM_MEMBER_REMOVAL_EMAIL_SUBJECT = "Team member removal email";

    private static final String TEAM_NAME = "teamName";

    private static final String NEW_TEAM_MEMBER_USERNAME = "newTeamMemberUsername";

    private static final String DELETED_TEAM_MEMBER_USERNAME = "deletedTeamMemberUsername";

    private final EmailSenderRESTService emailSenderRESTService;

    @Async
    public void sendTeamCreationEmail(final String username, final String teamName) {
        final EmailNotificationRequest emailNotificationRequest = EmailNotificationRequest.builder()
                .senderEmailAddress(SENDER_EMAIL_ADDRESS)
                .recipientUsernames(Lists.newArrayList(username))
                .subject(TEAM_CREATION_EMAIL_SUBJECT)
                .baseTemplate(BaseTemplate.TEAM)
                .subTemplate(SubTemplate.CREATION)
                .model(createModelForTeamCreation(teamName))
                .build();
        emailSenderRESTService.sendEmail(emailNotificationRequest);
    }

    @Async
    public void sendTeamRemovalEmail(final Set<String> teamMemberUsernames, final String teamName, final String requestedBy) {
        final EmailNotificationRequest emailNotificationRequest = EmailNotificationRequest.builder()
                .senderEmailAddress(SENDER_EMAIL_ADDRESS)
                .recipientUsernames(teamMemberUsernames)
                .subject(TEAM_REMOVAL_EMAIL_SUBJECT)
                .baseTemplate(BaseTemplate.TEAM)
                .subTemplate(SubTemplate.REMOVAL)
                .model(createModelForTeamDeletion(teamName, requestedBy))
                .build();
        emailSenderRESTService.sendEmail(emailNotificationRequest);
    }

    @Async
    public void sendTeamMemberAdditionEmail(final Set<String> teamMemberUsernames, final String teamName, final String newTeamMemberUsername, final String requestedBy) {
        final EmailNotificationRequest emailNotificationRequest = EmailNotificationRequest.builder()
                .senderEmailAddress(SENDER_EMAIL_ADDRESS)
                .recipientUsernames(teamMemberUsernames)
                .subject(TEAM_MEMBER_ADDITION_EMAIL_SUBJECT)
                .baseTemplate(BaseTemplate.TEAM)
                .subTemplate(SubTemplate.MEMBER_ADDITION)
                .model(createModelForTeamMemberAddition(teamName, newTeamMemberUsername, requestedBy))
                .build();
        emailSenderRESTService.sendEmail(emailNotificationRequest);
    }

    @Async
    public void sendTeamMemberRemovalEmail(final Set<String> teamMemberUsernames, final String teamName, final String deletedTeamMemberUsername, final String requestedBy) {
        final EmailNotificationRequest emailNotificationRequest = EmailNotificationRequest.builder()
                .senderEmailAddress(SENDER_EMAIL_ADDRESS)
                .recipientUsernames(teamMemberUsernames)
                .subject(TEAM_MEMBER_REMOVAL_EMAIL_SUBJECT)
                .baseTemplate(BaseTemplate.TEAM)
                .subTemplate(SubTemplate.MEMBER_REMOVAL)
                .model(createModelForTeamMemberRemoval(teamName, deletedTeamMemberUsername, requestedBy))
                .build();
        emailSenderRESTService.sendEmail(emailNotificationRequest);
    }


    private Map<String, String> createModelForTeamCreation(final String teamName) {
        final Map<String, String> model = Maps.newHashMap();
        model.put(TEAM_NAME, teamName);
        return model;
    }

    private Map<String, String> createModelForTeamDeletion(final String teamName, final String requestedBy) {
        final Map<String, String> model = Maps.newHashMap();
        model.put(TEAM_NAME, teamName);
        model.put(REQUESTED_BY, requestedBy);
        return model;
    }

    private Map<String, String> createModelForTeamMemberAddition(final String teamName, final String newTeamMemberUsername, final String requestedBy) {
        final Map<String, String> model = Maps.newHashMap();
        model.put(TEAM_NAME, teamName);
        model.put(REQUESTED_BY, requestedBy);
        model.put(NEW_TEAM_MEMBER_USERNAME, newTeamMemberUsername);
        return model;
    }

    private Map<String, String> createModelForTeamMemberRemoval(final String teamName, final String deletedTeamMemberUsername, final String requestedBy) {
        final Map<String, String> model = Maps.newHashMap();
        model.put(TEAM_NAME, teamName);
        model.put(REQUESTED_BY, requestedBy);
        model.put(DELETED_TEAM_MEMBER_USERNAME, deletedTeamMemberUsername);
        return model;
    }


}
