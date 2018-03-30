package com.hyrax.microservice.project.service.api;

public interface TaskService {

    void create(String boardName, String columnName, String taskName, String description, String requestedBy);
}
