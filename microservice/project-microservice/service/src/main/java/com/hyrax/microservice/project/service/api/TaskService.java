package com.hyrax.microservice.project.service.api;

import com.hyrax.microservice.project.service.domain.Task;

import java.util.List;

public interface TaskService {

    List<Task> findAllByBoardNameAndColumnName(String boardName, String columnName);

    void create(String boardName, String columnName, String taskName, String description, String requestedBy);
}
