package com.hyrax.microservice.project.data.dao.impl;

import com.hyrax.microservice.project.data.dao.TaskDAO;
import com.hyrax.microservice.project.data.entity.TaskEntity;
import com.hyrax.microservice.project.data.entity.saveable.SaveableTaskEntity;
import com.hyrax.microservice.project.data.mapper.LabelMapper;
import com.hyrax.microservice.project.data.mapper.TaskMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class TaskDAOImpl implements TaskDAO {

    private final TaskMapper taskMapper;

    private final LabelMapper labelMapper;

    @Override
    public List<TaskEntity> findAllByBoardNameAndColumnName(String boardName, String columnName) {
        return taskMapper.selectAllByBoardNameAndColumnName(boardName, columnName);
    }

    @Override
    public void save(final String boardName, final String columnName, final String taskName, final String description) {
        final SaveableTaskEntity saveableTaskEntity = SaveableTaskEntity.builder()
                .boardName(boardName)
                .columnName(columnName)
                .taskName(taskName)
                .description(description)
                .build();
        taskMapper.insert(saveableTaskEntity);
        taskMapper.assignDefaultUserToTask(boardName, saveableTaskEntity.getTaskId());
    }

    @Override
    public void update(final String boardName, final String columnName, final Long taskId, final String taskName, final String description) {
        taskMapper.update(boardName, columnName, taskId, taskName, description);
    }

    @Override
    public void updatePosition(final String boardName, final String columnName, final Long taskId, final Long newTaskIndex) {
        taskMapper.updateIndex(boardName, columnName, taskId, newTaskIndex);
    }

    @Override
    public void updatePositionInColumn(final String boardName, final String columnName, final Long taskId, final String newColumnName) {
        taskMapper.updatePositionInColumn(boardName, columnName, taskId, newColumnName);
    }

    @Override
    public void assignUserToTask(final String boardName, final Long taskId, final String username) {
        taskMapper.assignUserToTask(boardName, taskId, username);
    }

    @Override
    public void delete(final String boardName, final String columnName, final Long taskId) {
        labelMapper.deleteAllLabelFromTask(boardName, taskId);
        taskMapper.delete(boardName, columnName, taskId);
    }
}
