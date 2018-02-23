package com.hyrax.microservice.project.service.api;

import java.util.List;

public interface TeamMemberService {

    List<String> findAllUsernameByTeamName(String teamName);

    void add(String username, String teamName, String requestedBy);
}
