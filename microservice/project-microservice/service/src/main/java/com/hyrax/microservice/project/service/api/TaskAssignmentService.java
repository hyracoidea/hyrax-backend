package com.hyrax.microservice.project.service.api;

public interface TaskAssignmentService {

    void assignUserToTask(String boardName, Long taskId, String username, String requestedBy);
}
