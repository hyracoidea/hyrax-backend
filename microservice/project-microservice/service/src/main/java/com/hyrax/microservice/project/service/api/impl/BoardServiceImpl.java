package com.hyrax.microservice.project.service.api.impl;

import com.hyrax.microservice.project.data.entity.BoardEntity;
import com.hyrax.microservice.project.data.mapper.BoardMapper;
import com.hyrax.microservice.project.data.mapper.ColumnMapper;
import com.hyrax.microservice.project.data.mapper.TaskMapper;
import com.hyrax.microservice.project.service.api.BoardService;
import com.hyrax.microservice.project.service.domain.Board;
import com.hyrax.microservice.project.service.exception.board.BoardAlreadyExistsException;
import com.hyrax.microservice.project.service.exception.board.BoardRemovalOperationNotAllowedException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BoardServiceImpl implements BoardService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BoardServiceImpl.class);

    private final BoardMapper boardMapper;

    private final ColumnMapper columnMapper;

    private final TaskMapper taskMapper;

    private final ModelMapper modelMapper;

    @Override
    @Transactional(readOnly = true)
    public Optional<Board> findByBoardName(final String boardName) {
        Board board = null;
        final BoardEntity boardEntity = boardMapper.selectByBoardName(boardName);
        if (Objects.nonNull(boardEntity)) {
            board = modelMapper.map(boardEntity, Board.class);
        }
        return Optional.ofNullable(board);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Board> findAllByUsername(final String username) {
        return boardMapper.selectAllBoardByUsername(username)
                .stream()
                .map(boardEntity -> modelMapper.map(boardEntity, Board.class))
                .collect(Collectors.toList());
    }


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

    @Override
    @Transactional
    public void remove(final String boardName, final String requestedBy) {
        final Optional<Board> result = findByBoardName(boardName);

        if (result.isPresent()) {
            if (result.get().getOwnerUsername().equals(requestedBy)) {
                taskMapper.deleteAllByBoardName(boardName);
                columnMapper.deleteAllByBoardName(boardName);
                boardMapper.delete(boardName);
            } else {
                throw new BoardRemovalOperationNotAllowedException(requestedBy);
            }
        }
    }
}
