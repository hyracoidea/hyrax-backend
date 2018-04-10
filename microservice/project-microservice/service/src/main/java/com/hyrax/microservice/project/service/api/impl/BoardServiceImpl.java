package com.hyrax.microservice.project.service.api.impl;

import com.hyrax.microservice.project.data.dao.BoardDAO;
import com.hyrax.microservice.project.data.entity.BoardEntity;
import com.hyrax.microservice.project.service.api.BoardService;
import com.hyrax.microservice.project.service.api.impl.helper.BoardEventEmailSenderHelper;
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
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BoardServiceImpl implements BoardService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BoardServiceImpl.class);

    private final BoardDAO boardDAO;

    private final ModelMapper modelMapper;

    private final BoardEventEmailSenderHelper boardEventEmailSenderHelper;

    @Override
    @Transactional(readOnly = true)
    public Optional<Board> findByBoardName(final String boardName) {
        Board board = null;
        final Optional<BoardEntity> boardEntity = boardDAO.findByBoardName(boardName);
        if (boardEntity.isPresent()) {
            board = modelMapper.map(boardEntity.get(), Board.class);
        }
        return Optional.ofNullable(board);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Board> findAllByUsername(final String username) {
        return boardDAO.findAllByUsername(username)
                .stream()
                .map(boardEntity -> modelMapper.map(boardEntity, Board.class))
                .collect(Collectors.toList());
    }


    @Override
    @Transactional
    public void create(final String boardName, final String ownerUsername) {
        try {
            LOGGER.info("Trying to save the board = [boardName={} ownerUsername={}]", boardName, ownerUsername);
            boardDAO.save(boardName, ownerUsername);
            boardEventEmailSenderHelper.sendBoardCreationEmail(ownerUsername, boardName);
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
                final Set<String> boardMemberUsernames = boardDAO.findAllBoardMemberNameByBoardName(boardName);
                boardDAO.deleteByBoardName(boardName);
                boardEventEmailSenderHelper.sendBoarddRemovalEmail(boardMemberUsernames, boardName, requestedBy);
            } else {
                throw new BoardRemovalOperationNotAllowedException(requestedBy);
            }
        }
    }
}
