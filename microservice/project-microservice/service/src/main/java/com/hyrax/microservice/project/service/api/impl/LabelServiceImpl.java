package com.hyrax.microservice.project.service.api.impl;

import com.hyrax.microservice.project.data.dao.BoardDAO;
import com.hyrax.microservice.project.data.dao.LabelDAO;
import com.hyrax.microservice.project.data.dao.TaskDAO;
import com.hyrax.microservice.project.data.entity.LabelEntity;
import com.hyrax.microservice.project.data.entity.SingleTaskEntity;
import com.hyrax.microservice.project.service.api.LabelService;
import com.hyrax.microservice.project.service.api.impl.checker.LabelOperationChecker;
import com.hyrax.microservice.project.service.api.impl.helper.LabelEventEmailSenderHelper;
import com.hyrax.microservice.project.service.api.impl.helper.WatchedTaskEventEmailSenderHelper;
import com.hyrax.microservice.project.service.domain.Label;
import com.hyrax.microservice.project.service.domain.LabelColor;
import com.hyrax.microservice.project.service.exception.label.LabelAdditionOperationNotAllowedException;
import com.hyrax.microservice.project.service.exception.label.LabelAdditionToTaskException;
import com.hyrax.microservice.project.service.exception.label.LabelAlreadyAddedToTaskException;
import com.hyrax.microservice.project.service.exception.label.LabelAlreadyExistsException;
import com.hyrax.microservice.project.service.exception.label.LabelRemovalOperationNotAllowedException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LabelServiceImpl implements LabelService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LabelServiceImpl.class);

    private final LabelOperationChecker labelOperationChecker;

    private final LabelDAO labelDAO;

    private final BoardDAO boardDAO;

    private final TaskDAO taskDAO;

    private final ModelMapper modelMapper;

    private final LabelEventEmailSenderHelper labelEventEmailSenderHelper;

    private final WatchedTaskEventEmailSenderHelper watchedTaskEventEmailSenderHelper;

    @Override
    @Transactional(readOnly = true)
    public List<Label> findAllByBoardName(final String boardName) {
        return labelDAO.findAllByBoardName(boardName)
                .stream()
                .map(labelEntity -> modelMapper.map(labelEntity, Label.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void create(final String boardName, final String labelName, final LabelColor labelColor, final String requestedBy) {
        final boolean isOperationAllowed = labelOperationChecker.isOperationAllowed(boardName, requestedBy);

        if (isOperationAllowed) {
            try {
                LOGGER.info("Trying to save the label = [boardName={} labelName={} labelColor={} requestedBy={}]", boardName, labelName, labelColor, requestedBy);
                labelDAO.save(boardName, labelName, labelColor.getRed(), labelColor.getGreen(), labelColor.getBlue());
                labelEventEmailSenderHelper.sendLabelCreationEmail(boardDAO.findAllBoardMemberNameByBoardName(boardName), boardName, labelName, requestedBy);
                LOGGER.info("Label saving was successful [boardName={} labelName={} labelColor={} requestedBy={}]", boardName, labelName, labelColor, requestedBy);
            } catch (final DuplicateKeyException e) {
                final String errorMessage = String.format("Label already exists with this name=%s on this board=%s", labelName, boardName);
                LOGGER.error(errorMessage, e);
                throw new LabelAlreadyExistsException(errorMessage);
            }
        } else {
            throw new LabelAdditionOperationNotAllowedException(requestedBy);
        }
    }

    @Override
    @Transactional
    public void addLabelToTask(final String boardName, final Long taskId, final Long labelId, final String requestedBy) {
        final boolean isOperationAllowed = labelOperationChecker.isOperationAllowed(boardName, requestedBy);

        if (isOperationAllowed) {
            try {
                LOGGER.info("Trying to add label to task [boardName={} labelId={} taskId={} requestedBy={}]", boardName, labelId, taskId, requestedBy);
                labelDAO.addToTask(boardName, taskId, labelId);

                final Optional<SingleTaskEntity> singleTaskEntity = taskDAO.findSingleTask(boardName, taskId);
                final Optional<String> assignedLabelName = labelDAO.findByLabelId(boardName, labelId).map(LabelEntity::getLabelName);
                if (singleTaskEntity.isPresent() && assignedLabelName.isPresent()) {
                    watchedTaskEventEmailSenderHelper.sendWatchedTaskAssignLabelEmail(singleTaskEntity.get().getWatchedUsers(), boardName, taskId,
                            singleTaskEntity.get().getTaskName(), assignedLabelName.get(), requestedBy);
                }
                LOGGER.info("Adding label to task was successful [boardName={} labelId={} taskId={} requestedBy={}]",
                        boardName, labelId, taskId, requestedBy);
            } catch (final DuplicateKeyException e) {
                final String errorMessage = String.format("Label with id=%s already added to task with id=%s", labelId, taskId);
                LOGGER.error(errorMessage, e);
                throw new LabelAlreadyAddedToTaskException(errorMessage);
            } catch (final DataIntegrityViolationException e) {
                final String errorMessage = String.format("Label with id=%s or task with id=%s does not exist", labelId, taskId);
                LOGGER.error(errorMessage, e);
                throw new LabelAdditionToTaskException(errorMessage);
            }
        } else {
            throw new LabelAdditionOperationNotAllowedException(requestedBy);
        }
    }

    @Override
    @Transactional
    public void remove(final String boardName, final Long labelId, final String requestedBy) {
        final boolean isOperationAllowed = labelOperationChecker.isOperationAllowed(boardName, requestedBy);
        if (isOperationAllowed) {
            final Optional<LabelEntity> labelEntity = labelDAO.findByLabelId(boardName, labelId);
            labelDAO.delete(boardName, labelId);
            if (labelEntity.isPresent()) {
                labelEventEmailSenderHelper.sendLabelRemovalEmail(boardDAO.findAllBoardMemberNameByBoardName(boardName), boardName, labelEntity.get().getLabelName(), requestedBy);
            }
        } else {
            throw new LabelRemovalOperationNotAllowedException(requestedBy);
        }
    }

    @Override
    @Transactional
    public void removeLabelFromTask(final String boardName, final Long taskId, final Long labelId, final String requestedBy) {
        final boolean isOperationAllowed = labelOperationChecker.isOperationAllowed(boardName, requestedBy);
        if (isOperationAllowed) {
            final Optional<String> removedLabelName = labelDAO.findByLabelId(boardName, labelId).map(LabelEntity::getLabelName);
            labelDAO.deleteFromTask(boardName, taskId, labelId);

            final Optional<SingleTaskEntity> singleTaskEntity = taskDAO.findSingleTask(boardName, taskId);
            if (removedLabelName.isPresent() && singleTaskEntity.isPresent()) {
                watchedTaskEventEmailSenderHelper.sendWatchedTaskRemoveLabelEmail(singleTaskEntity.get().getWatchedUsers(), boardName, taskId, singleTaskEntity.get().getTaskName(),
                        removedLabelName.get(), requestedBy);
            }

        } else {
            throw new LabelRemovalOperationNotAllowedException(requestedBy);
        }
    }
}
