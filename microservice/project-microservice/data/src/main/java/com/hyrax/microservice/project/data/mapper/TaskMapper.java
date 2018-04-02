package com.hyrax.microservice.project.data.mapper;

import com.hyrax.microservice.project.data.entity.TaskEntity;
import com.hyrax.microservice.project.data.entity.saveable.SaveableTaskEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TaskMapper {

    List<TaskEntity> selectAllByBoardNameAndColumnName(@Param("boardName") String boardName, @Param("columnName") String columnName);

    void insert(@Param("task") SaveableTaskEntity saveableTaskEntity);

    void insert(@Param("boardName") String boardName, @Param("columnName") String columnName,
                @Param("taskName") String taskName, @Param("description") String description);

    void update(@Param("boardName") String boardName, @Param("columnName") String columnName, @Param("taskId") Long taskId,
                @Param("taskName") String taskName, @Param("description") String description);

    void updateIndex(@Param("boardName") String boardName, @Param("columnName") String columnName,
                     @Param("taskId") Long taskId, @Param("newTaskIndex") Long newTaskIndex);

    void updatePositionInColumn(@Param("boardName") String boardName, @Param("columnName") String columnName,
                                @Param("taskId") Long taskId, @Param("newColumnName") String newColumnName);

    void assignDefaultUserToTask(@Param("boardName") String boardName, @Param("taskId") Long taskId);

    void assignUserToTask(@Param("boardName") String boardName, @Param("taskId") Long taskId, @Param("username") String username);

    void delete(@Param("boardName") String boardName, @Param("columnName") String columnName, @Param("taskId") Long taskId);

    void deleteAllByBoardName(@Param("boardName") String boardName);

    void deleteAllByBoardNameAndColumnName(@Param("boardName") String boardName, @Param("columnName") String columnName);

    void deleteAssignedUserFromTask(@Param("boardName") String boardName, @Param("taskId") Long taskId);

    void deleteAssignedUsersFromTasksByColumnName(@Param("boardName") String boardName, @Param("columnName") String columnName);

    void deleteAssignedUsersFromTasksByBoardName(@Param("boardName") String boardName);
}
