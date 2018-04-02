package com.hyrax.microservice.project.service.api.impl;

import com.google.common.collect.Lists;
import com.hyrax.microservice.project.data.entity.TaskEntity;
import com.hyrax.microservice.project.data.entity.saveable.SaveableTaskEntity;
import com.hyrax.microservice.project.data.mapper.LabelMapper;
import com.hyrax.microservice.project.data.mapper.TaskMapper;
import com.hyrax.microservice.project.service.api.TaskService;
import com.hyrax.microservice.project.service.api.impl.checker.TaskOperationChecker;
import com.hyrax.microservice.project.service.domain.Task;
import com.hyrax.microservice.project.service.exception.ResourceNotFoundException;
import com.hyrax.microservice.project.service.exception.task.AssignUserToTaskOperationNotAllowedException;
import com.hyrax.microservice.project.service.exception.task.TaskAdditionOperationNotAllowedException;
import com.hyrax.microservice.project.service.exception.task.TaskRemovalOperationNotAllowedException;
import com.hyrax.microservice.project.service.exception.task.TaskUpdateOperationNotAllowedException;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.math.NumberUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskServiceImpl.class);

    private final TaskMapper taskMapper;

    private final TaskOperationChecker taskOperationChecker;

    private final LabelMapper labelMapper;

    private final ModelMapper modelMapper;

    @Override
    @Transactional(readOnly = true)
    public List<Task> findAllByBoardNameAndColumnName(final String boardName, final String columnName) {
        return taskMapper.selectAllByBoardNameAndColumnName(boardName, columnName)
                .stream()
                .map(taskEntity -> modelMapper.map(taskEntity, Task.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void create(final String boardName, final String columnName, final String taskName, final String description, final String requestedBy) {
        final boolean isOperationAllowed = taskOperationChecker.isOperationAllowed(boardName, requestedBy);

        if (isOperationAllowed) {
            try {
                LOGGER.info("Trying to save the task = [boardName={} columnName={} taskName={} description={}]", boardName, columnName, taskName, description);
                final SaveableTaskEntity saveableTaskEntity = SaveableTaskEntity.builder()
                        .boardName(boardName)
                        .columnName(columnName)
                        .taskName(taskName)
                        .description(description)
                        .build();
                taskMapper.insert(saveableTaskEntity);
                taskMapper.assignDefaultUserToTask(boardName, saveableTaskEntity.getTaskId());
                LOGGER.info("Task saving was successful [boardName={} columnName={} taskName={} description={}]", boardName, columnName, taskName, description);
            } catch (final DataIntegrityViolationException e) {
                final String errorMessage = String.format("Column does not exist [boardName=%s columnName=%s]", boardName, columnName);
                LOGGER.error(errorMessage, e);
                throw new ResourceNotFoundException(errorMessage);
            }
        } else {
            throw new TaskAdditionOperationNotAllowedException(requestedBy);
        }
    }

    @Override
    @Transactional
    public void assignUserToTask(final String boardName, final Long taskId, final String username, final String requestedBy) {
        final boolean isOperationAllowed = taskOperationChecker.isOperationAllowed(boardName, requestedBy);

        if (isOperationAllowed) {
            LOGGER.info("Trying to assign user to task [boardName={} taskId={} assignUsername={}]", boardName, taskId, username);
            taskMapper.assignUserToTask(boardName, taskId, username);
            LOGGER.info("Assigning user to task was successful [boardName={} taskId={} assignUsername={}]", boardName, taskId, username);
        } else {
            throw new AssignUserToTaskOperationNotAllowedException(requestedBy);
        }
    }

    @Override
    @Transactional
    public void update(final String boardName, final String columnName, final Long taskId, final String taskName, final String description, final String requestedBy) {
        final boolean isOperationAllowed = taskOperationChecker.isOperationAllowed(boardName, requestedBy);

        if (isOperationAllowed) {
            taskMapper.update(boardName, columnName, taskId, taskName, description);
        } else {
            throw new TaskUpdateOperationNotAllowedException(requestedBy);
        }
    }

    @Override
    @Transactional
    public void updateIndex(final String boardName, final String columnName, final Long taskId, final long from, final long to, final String requestedBy) {
        final boolean isOperationAllowed = taskOperationChecker.isOperationAllowed(boardName, requestedBy);

        if (isOperationAllowed) {

            final List<TaskEntity> up = Lists.newArrayList();
            final List<TaskEntity> down = Lists.newArrayList();
            if (from < to) {
                populateByFromUpToDown(boardName, columnName, taskId, to, up, down);
            } else if (from > to) {
                populateByFromDownToUp(boardName, columnName, taskId, to, up, down);
            }

            if (from != to) {
                updateTasksByIndex(boardName, columnName, up, NumberUtils.LONG_ZERO);
                updateTasksByIndex(boardName, columnName, down, to);
                taskMapper.updateIndex(boardName, columnName, taskId, to);
            }
        } else {
            throw new TaskUpdateOperationNotAllowedException(requestedBy);
        }
    }

    @Override
    @Transactional
    public void updatePositionInColumn(final String boardName, final String columnName, final Long taskId, final String newColumnName, final String requestedBy) {
        final boolean isOperationAllowed = taskOperationChecker.isOperationAllowed(boardName, requestedBy);

        if (isOperationAllowed) {
            if (!columnName.equals(newColumnName)) {
                taskMapper.updatePositionInColumn(boardName, columnName, taskId, newColumnName);
            }
        } else {
            throw new TaskUpdateOperationNotAllowedException(requestedBy);
        }
    }

    @Override
    @Transactional
    public void remove(final String boardName, final String columnName, final Long taskId, final String requestedBy) {
        final boolean isOperationAllowed = taskOperationChecker.isOperationAllowed(boardName, requestedBy);
        if (isOperationAllowed) {
            labelMapper.deleteAllLabelFromTask(boardName, taskId);
            taskMapper.delete(boardName, columnName, taskId);
        } else {
            throw new TaskRemovalOperationNotAllowedException(requestedBy);
        }
    }

    private void populateByFromUpToDown(final String boardName, final String columnName, final Long taskId, final Long newColumnIndex,
                                        final List<TaskEntity> up, final List<TaskEntity> down) {
        taskMapper.selectAllByBoardNameAndColumnName(boardName, columnName)
                .stream()
                .filter(task -> task.getTaskId().longValue() != taskId.longValue())
                .forEach(task -> {
                    if (task.getTaskIndex() <= newColumnIndex) {
                        up.add(task);
                    } else {
                        down.add(task);
                    }
                });
    }

    private void populateByFromDownToUp(final String boardName, final String columnName, final Long taskId, final Long newColumnIndex,
                                        final List<TaskEntity> up, final List<TaskEntity> down) {
        taskMapper.selectAllByBoardNameAndColumnName(boardName, columnName)
                .stream()
                .filter(task -> task.getTaskId().longValue() != taskId.longValue())
                .forEach(task -> {
                    if (task.getTaskIndex() < newColumnIndex) {
                        up.add(task);
                    } else {
                        down.add(task);
                    }
                });
    }

    private void updateTasksByIndex(final String boardName, final String columnName, final List<TaskEntity> tasks, final Long startIndex) {
        final AtomicLong index = new AtomicLong(startIndex);

        tasks.forEach(task -> taskMapper.updateIndex(boardName, columnName, task.getTaskId(), index.incrementAndGet()));
    }
}
