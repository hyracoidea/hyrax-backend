package com.hyrax.microservice.project.service.api.impl;

import com.hyrax.microservice.project.data.dao.TaskDAO;
import com.hyrax.microservice.project.data.entity.SingleTaskEntity;
import com.hyrax.microservice.project.service.api.TaskAssignmentService;
import com.hyrax.microservice.project.service.api.impl.checker.TaskOperationChecker;
import com.hyrax.microservice.project.service.api.impl.helper.WatchedTaskEventEmailSenderHelper;
import com.hyrax.microservice.project.service.exception.task.AssignUserToTaskException;
import com.hyrax.microservice.project.service.exception.task.AssignUserToTaskOperationNotAllowedException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class TaskAssignmentServiceImpl implements TaskAssignmentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskServiceImpl.class);

    private static final String TASK_DOES_NOT_EXIS_TEMPLATE_MESSAGE = "Task does not exist with id=%s";

    private final TaskOperationChecker taskOperationChecker;

    private final TaskDAO taskDAO;

    private final WatchedTaskEventEmailSenderHelper watchedTaskEventEmailSenderHelper;

    @Override
    @Transactional
    public void assignUserToTask(final String boardName, final Long taskId, final String username, final String requestedBy) {
        final boolean isOperationAllowed = taskOperationChecker.isOperationAllowed(boardName, requestedBy);

        if (isOperationAllowed) {
            try {
                LOGGER.info("Trying to assign user to task [boardName={} taskId={} assignUsername={}]", boardName, taskId, username);
                final Optional<SingleTaskEntity> singleTaskEntity = taskDAO.findSingleTask(boardName, taskId);
                taskDAO.assignUserToTask(boardName, taskId, username);


                if (singleTaskEntity.isPresent()) {
                    watchedTaskEventEmailSenderHelper.sendWatchedTaskAssignUserEmail(singleTaskEntity.get().getWatchedUsers(), boardName, taskId,
                            singleTaskEntity.get().getTaskName(), singleTaskEntity.get().getAssignedUsername(), username, requestedBy);
                }

                LOGGER.info("Assigning user to task was successful [boardName={} taskId={} assignUsername={}]", boardName, taskId, username);
            } catch (final DataIntegrityViolationException e) {
                final String errorMessage = String.format(TASK_DOES_NOT_EXIS_TEMPLATE_MESSAGE, taskId);
                LOGGER.error(errorMessage, e);
                throw new AssignUserToTaskException(errorMessage);
            }
        } else {
            throw new AssignUserToTaskOperationNotAllowedException(requestedBy);
        }
    }
}
