package com.hyrax.microservice.project.service.api.impl;

import com.hyrax.microservice.project.data.mapper.LabelMapper;
import com.hyrax.microservice.project.service.api.LabelService;
import com.hyrax.microservice.project.service.api.impl.checker.LabelOperationChecker;
import com.hyrax.microservice.project.service.domain.LabelColor;
import com.hyrax.microservice.project.service.exception.label.LabelAdditionOperationNotAllowedException;
import com.hyrax.microservice.project.service.exception.label.LabelAdditionToTaskException;
import com.hyrax.microservice.project.service.exception.label.LabelAlreadyExistsException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class LabelServiceImpl implements LabelService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LabelServiceImpl.class);

    private final LabelMapper labelMapper;

    private final LabelOperationChecker labelOperationChecker;

    @Override
    @Transactional
    public void create(final String boardName, final String labelName, final LabelColor labelColor, final String requestedBy) {
        final boolean isOperationAllowed = labelOperationChecker.isOperationAllowed(boardName, requestedBy);

        if (isOperationAllowed) {
            try {
                LOGGER.info("Trying to save the label = [boardName={} labelName={} labelColor={} requestedBy={}]", boardName, labelName, labelColor, requestedBy);
                labelMapper.insert(boardName, labelName, labelColor.getRed(), labelColor.getGreen(), labelColor.getBlue());
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
                labelMapper.addLabelToTask(boardName, taskId, labelId);
                LOGGER.info("Adding label to task was successful [boardName={} labelId={} taskId={} requestedBy={}]",
                        boardName, labelId, taskId, requestedBy);
            } catch (final DataIntegrityViolationException e) {
                final String errorMessage = String.format("Label addition to task was not successful [ labelId=%s taskId=%s]", labelId, taskId);
                LOGGER.error(errorMessage, e);
                throw new LabelAdditionToTaskException(errorMessage);
            }
        } else {
            throw new LabelAdditionOperationNotAllowedException(requestedBy);
        }
    }
}
