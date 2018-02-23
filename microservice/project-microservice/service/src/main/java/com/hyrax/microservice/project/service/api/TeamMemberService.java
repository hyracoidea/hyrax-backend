package com.hyrax.microservice.project.service.api;

public interface TeamMemberService {

    void add(String username, String teamName, String requestedBy);
}
