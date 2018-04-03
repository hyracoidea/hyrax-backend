package com.hyrax.microservice.project.data.dao;

import com.hyrax.microservice.project.data.entity.TaskEntity;

import java.util.List;

public interface TaskDAO {

    List<TaskEntity> findAllByBoardNameAndColumnName(String boardName, String columnName);

    void save(String boardName, String columnName, String taskName, String description, String requestedBy);

    void update(String boardName, String columnName, Long taskId, String taskName, String description);

    void updatePosition(String boardName, String columnName, Long taskId, Long newTaskIndex);

    void updatePositionBetweenColumns(String boardName, String columnName, Long taskId, String newColumnName);

    void assignUserToTask(String boardName, Long taskId, String username);

    void watchTask(String boardName, Long taskId, String username);

    void unwatchTask(String boardName, Long taskId, String username);

    void delete(String boardName, String columnName, Long taskId);
}
