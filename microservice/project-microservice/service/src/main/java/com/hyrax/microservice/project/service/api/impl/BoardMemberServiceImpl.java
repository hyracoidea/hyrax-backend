package com.hyrax.microservice.project.service.api.impl;

import com.hyrax.microservice.project.data.dao.BoardDAO;
import com.hyrax.microservice.project.service.api.BoardMemberService;
import com.hyrax.microservice.project.service.api.BoardService;
import com.hyrax.microservice.project.service.domain.Board;
import com.hyrax.microservice.project.service.exception.board.BoardNotFoundException;
import com.hyrax.microservice.project.service.exception.board.member.BoardMemberAdditionOperationNotAllowedException;
import com.hyrax.microservice.project.service.exception.board.member.BoardMemberIsAlreadyAddedException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class BoardMemberServiceImpl implements BoardMemberService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BoardMemberServiceImpl.class);

    private static final String BOARD_MEMBER_ALREADY_ADDED_ERROR_MESSAGE_TEMPLATE = "Board member is already added: %s";

    private final BoardDAO boardDAO;

    private final BoardService boardService;

    @Override
    @Transactional(readOnly = true)
    public List<String> findAllUsernameByBoardName(final String boardName) {
        return boardDAO.findAllBoardMemberNameByBoardName(boardName);
    }

    @Override
    @Transactional
    public void add(final String username, final String boardName, final String requestedBy) {
        final List<String> boardMemberUsernames = findAllUsernameByBoardName(boardName);
        final Board board = boardService.findByBoardName(boardName).orElseThrow(() -> new BoardNotFoundException(boardName));

        validateByRole(requestedBy, board.getOwnerUsername(), boardMemberUsernames);

        try {
            boardDAO.addMemberToBoard(boardName, username);
        } catch (final DuplicateKeyException e) {
            final String errorMessage = String.format(BOARD_MEMBER_ALREADY_ADDED_ERROR_MESSAGE_TEMPLATE, username);
            LOGGER.error(errorMessage, e);
            throw new BoardMemberIsAlreadyAddedException(errorMessage);
        }

    }

    private void validateByRole(final String requestedBy, final String boardOwnerUsername, final List<String> boardMemberUsernames) {
        final boolean isBoardOwner = boardOwnerUsername.equals(requestedBy);
        final boolean isBoardMember = boardMemberUsernames.contains(requestedBy);
        if (!isBoardOwner && !isBoardMember) {
            throw new BoardMemberAdditionOperationNotAllowedException(requestedBy);
        }
    }
}
