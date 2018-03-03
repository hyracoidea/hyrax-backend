package com.hyrax.microservice.project.service.api.impl;

import com.hyrax.microservice.project.data.mapper.BoardMapper;
import com.hyrax.microservice.project.service.api.BoardService;
import com.hyrax.microservice.project.service.exception.BoardAlreadyExistsException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class BoardServiceImpl implements BoardService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BoardServiceImpl.class);

    private final BoardMapper boardMapper;

    @Override
    @Transactional
    public void create(final String boardName, final String ownerUsername) {
        try {
            LOGGER.info("Trying to save the board = [boardName={} ownerUsername={}]", boardName, ownerUsername);
            boardMapper.insert(boardName, ownerUsername);
            LOGGER.info("Board saving was successful [boardName={} ownerUsername={}]", boardName, ownerUsername);
        } catch (final DuplicateKeyException e) {
            final String errorMessage = String.format("Board already exists with this name=%s", boardName);
            LOGGER.error(errorMessage, e);
            throw new BoardAlreadyExistsException(errorMessage);
        }

    }
}
