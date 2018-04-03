package com.hyrax.microservice.project.service.api;

import com.hyrax.microservice.project.service.domain.Task;

import java.util.List;

public interface TaskService {

    List<Task> findAllByBoardNameAndColumnName(String boardName, String columnName);

    void create(String boardName, String columnName, String taskName, String description, String requestedBy);

    void assignUserToTask(String boardName, Long taskId, String username, String requestedBy);

    void watchTask(String boardName, Long taskId, String requestedBy);

    void unwatch(String boardName, Long taskId, String requestedBy);

    void update(String boardName, String columnName, Long taskId, String taskName, String description, String requestedBy);

    void updatePosition(String boardName, String columnName, Long taskId, long from, long to, String requestedBy);

    void updatePositionBetweenColumns(String boardName, String columnName, Long taskId, String newColumnName, String requestedBy);

    void remove(String boardName, String columnName, Long taskId, String requestedBy);
}
