package com.hyrax.microservice.project.service.api.impl.checker;

import com.hyrax.microservice.project.data.dao.BoardDAO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class BoardOperationChecker {

    private final BoardDAO boardDAO;

    public boolean isAdditionOperationAllowed(final String boardName, final String requestedBy) {
        final Optional<String> ownerUsername = boardDAO.findByBoardName(boardName).map(board -> board.getOwnerUsername());
        final List<String> memberUsernames = boardDAO.findAllBoardMemberNameByBoardName(boardName);

        return ownerUsername.isPresent() && (ownerUsername.get().equals(requestedBy)) || (memberUsernames.contains(requestedBy));
    }

    public boolean canRemove(final String boardName, final String username, final String requestedBy) {
        final Optional<String> ownerUsername = boardDAO.findByBoardName(boardName).map(board -> board.getOwnerUsername());
        final List<String> memberUsernames = boardDAO.findAllBoardMemberNameByBoardName(boardName);

        return ownerUsername.isPresent() && (ownerUsername.get().equals(requestedBy)) || (memberUsernames.contains(username));
    }
}
