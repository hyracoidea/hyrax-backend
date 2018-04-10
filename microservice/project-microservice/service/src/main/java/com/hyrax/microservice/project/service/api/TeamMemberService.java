package com.hyrax.microservice.project.service.api;

import java.util.Set;

public interface TeamMemberService {

    Set<String> findAllUsernameByTeamName(String teamName);

    void add(String username, String teamName, String requestedBy);

    void remove(String username, String teamName, String requestedBy);
}
