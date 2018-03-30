package com.hyrax.microservice.project.service.api.impl.checker;

import com.hyrax.microservice.project.service.api.BoardMemberService;
import com.hyrax.microservice.project.service.api.BoardService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class TaskOperationChecker {

    private final BoardService boardService;

    private final BoardMemberService boardMemberService;

    @Transactional(readOnly = true)
    public boolean isOperationAllowed(final String boardName, final String username) {
        final Optional<String> ownerUsername = boardService.findByBoardName(boardName).map(board -> board.getOwnerUsername());
        final List<String> memberUsernames = boardMemberService.findAllUsernameByBoardName(boardName);

        return (ownerUsername.isPresent() && ownerUsername.get().equals(username)) || (memberUsernames.contains(username));
    }


}
