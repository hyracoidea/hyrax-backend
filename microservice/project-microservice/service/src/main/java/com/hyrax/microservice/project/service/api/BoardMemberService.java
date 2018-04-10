package com.hyrax.microservice.project.service.api;

import java.util.Set;

public interface BoardMemberService {

    Set<String> findAllUsernameByBoardName(String boardName);

    void add(String username, String boardName, String requestedBy);

    void addTeamMembersToBoard(String boardName, String teamName, String requestedBy);

    void removeMemberFromBoard(String boardName, String username, String requestedBy);
}
