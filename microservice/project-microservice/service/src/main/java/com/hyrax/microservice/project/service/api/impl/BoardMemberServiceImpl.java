package com.hyrax.microservice.project.service.api.impl;

import com.hyrax.microservice.project.data.dao.BoardDAO;
import com.hyrax.microservice.project.data.dao.TeamDAO;
import com.hyrax.microservice.project.service.api.BoardMemberService;
import com.hyrax.microservice.project.service.api.impl.checker.BoardOperationChecker;
import com.hyrax.microservice.project.service.api.impl.helper.BoardEventEmailSenderHelper;
import com.hyrax.microservice.project.service.exception.board.member.BoardMemberAdditionOperationNotAllowedException;
import com.hyrax.microservice.project.service.exception.board.member.BoardMemberIsAlreadyAddedException;
import com.hyrax.microservice.project.service.exception.board.member.BoardMemberRemovalOperationNotAllowedException;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Set;

@Service
@AllArgsConstructor
public class BoardMemberServiceImpl implements BoardMemberService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BoardMemberServiceImpl.class);

    private static final String BOARD_MEMBER_ALREADY_ADDED_ERROR_MESSAGE_TEMPLATE = "Board member is already added: %s";

    private final BoardOperationChecker boardOperationChecker;

    private final BoardDAO boardDAO;

    private final TeamDAO teamDAO;

    private final BoardEventEmailSenderHelper boardEventEmailSenderHelper;

    @Override
    @Transactional(readOnly = true)
    public Set<String> findAllUsernameByBoardName(final String boardName) {
        return boardDAO.findAllBoardMemberNameByBoardName(boardName);
    }

    @Override
    @Transactional
    public void add(final String username, final String boardName, final String requestedBy) {
        final boolean isOperationAllowed = boardOperationChecker.isAdditionOperationAllowed(boardName, requestedBy);

        if (isOperationAllowed) {
            try {
                boardDAO.addMemberToBoard(boardName, username);
                boardEventEmailSenderHelper.sendBoardMemberAdditionEmail(boardDAO.findAllBoardMemberNameByBoardName(boardName), boardName, username, requestedBy);
            } catch (final DuplicateKeyException e) {
                final String errorMessage = String.format(BOARD_MEMBER_ALREADY_ADDED_ERROR_MESSAGE_TEMPLATE, username);
                LOGGER.error(errorMessage, e);
                throw new BoardMemberIsAlreadyAddedException(errorMessage);
            }
        } else {
            throw new BoardMemberAdditionOperationNotAllowedException(requestedBy);
        }
    }

    @Override
    @Transactional
    public void addTeamMembersToBoard(final String boardName, final String teamName, final String requestedBy) {
        final boolean isOperationAllowed = boardOperationChecker.isAdditionOperationAllowed(boardName, requestedBy);

        if (isOperationAllowed) {
            final Set<String> teamMembers = teamDAO.findAllTeamMemberNameByTeamName(teamName);
            final Set<String> boardMembers = boardDAO.findAllBoardMemberNameByBoardName(boardName);

            final Collection<String> nonBoardMembers = CollectionUtils.subtract(teamMembers, boardMembers);

            nonBoardMembers.forEach(username -> boardDAO.addMemberToBoard(boardName, username));
            final Set<String> updatedBoardMembers = boardDAO.findAllBoardMemberNameByBoardName(boardName);
            nonBoardMembers.forEach(username -> boardEventEmailSenderHelper.sendBoardMemberAdditionEmail(updatedBoardMembers, boardName, username, requestedBy));
        } else {
            throw new BoardMemberAdditionOperationNotAllowedException(requestedBy);
        }
    }

    @Override
    @Transactional
    public void removeMemberFromBoard(final String boardName, final String username, final String requestedBy) {
        final boolean isOperationAllowed = boardOperationChecker.canRemove(boardName, username, requestedBy);

        if (isOperationAllowed) {
            final Set<String> boardMemberUsernames = boardDAO.findAllBoardMemberNameByBoardName(boardName);
            boardDAO.deleteMemberFromBoard(boardName, username);
            boardEventEmailSenderHelper.sendBoardMemberRemovalEmail(boardMemberUsernames, boardName, username, requestedBy);
        } else {
            throw new BoardMemberRemovalOperationNotAllowedException(requestedBy);
        }
    }
}
