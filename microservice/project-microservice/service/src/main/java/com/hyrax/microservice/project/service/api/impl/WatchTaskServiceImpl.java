package com.hyrax.microservice.project.service.api.impl;

import com.hyrax.microservice.project.data.dao.TaskDAO;
import com.hyrax.microservice.project.data.entity.SingleTaskEntity;
import com.hyrax.microservice.project.service.api.WatchTaskService;
import com.hyrax.microservice.project.service.api.impl.helper.WatchedTaskEventEmailSenderHelper;
import com.hyrax.microservice.project.service.exception.task.WatchTaskException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class WatchTaskServiceImpl implements WatchTaskService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WatchTaskServiceImpl.class);

    private static final String TASK_DOES_NOT_EXIS_TEMPLATE_MESSAGE = "Task does not exist with id=%s";

    private final TaskDAO taskDAO;

    private final WatchedTaskEventEmailSenderHelper watchedTaskEventEmailSenderHelper;

    @Override
    @Transactional
    public void watchTask(final String boardName, final Long taskId, final String requestedBy) {
        try {
            LOGGER.info("Trying to watch the task [boardName={} taskId={} requestedBy={}]", boardName, taskId, requestedBy);
            taskDAO.watchTask(boardName, taskId, requestedBy);
            taskDAO.findSingleTask(boardName, taskId).ifPresent(
                    taskEntity -> watchedTaskEventEmailSenderHelper.sendWatchedTaskWatchEmail(taskEntity.getWatchedUsers(), boardName,
                            taskId, taskEntity.getTaskName(), requestedBy)
            );
            LOGGER.info("Watch the task was successful [boardName={} taskId={} requestedBy={}]", boardName, taskId, requestedBy);
        } catch (final DuplicateKeyException e) {
            LOGGER.error("User={} already watched the task with id={} on board={}", requestedBy, taskId, boardName);
        } catch (final DataIntegrityViolationException e) {
            final String errorMessage = String.format(TASK_DOES_NOT_EXIS_TEMPLATE_MESSAGE, taskId);
            LOGGER.error(errorMessage, e);
            throw new WatchTaskException(errorMessage);
        }
    }

    @Override
    @Transactional
    public void unwatch(final String boardName, final Long taskId, final String requestedBy) {
        LOGGER.info("Trying to unwatch the task [boardName={} taskId={} requestedBy={}]", boardName, taskId, requestedBy);
        final Optional<SingleTaskEntity> singleTaskEntity = taskDAO.findSingleTask(boardName, taskId);
        taskDAO.unwatchTask(boardName, taskId, requestedBy);
        singleTaskEntity.ifPresent(
                taskEntity -> watchedTaskEventEmailSenderHelper.sendWatchedTaskUnwatchEmail(taskEntity.getWatchedUsers(), boardName, taskId, taskEntity.getTaskName(), requestedBy)
        );
        LOGGER.info("Unwatch the task was successful [boardName={} taskId={} requestedBy={}]", boardName, taskId, requestedBy);
    }
}
