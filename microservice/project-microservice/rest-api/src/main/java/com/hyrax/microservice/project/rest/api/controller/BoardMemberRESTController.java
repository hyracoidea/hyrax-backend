package com.hyrax.microservice.project.rest.api.controller;

import com.hyrax.microservice.project.rest.api.domain.response.ErrorResponse;
import com.hyrax.microservice.project.rest.api.domain.response.wrapper.BoardMemberResponseWrapper;
import com.hyrax.microservice.project.rest.api.security.AuthenticationUserDetailsHelper;
import com.hyrax.microservice.project.service.api.BoardMemberService;
import com.hyrax.microservice.project.service.api.BoardService;
import com.hyrax.microservice.project.service.exception.ResourceNotFoundException;
import com.hyrax.microservice.project.service.exception.board.member.BoardMemberIsAlreadyAddedException;
import com.hyrax.microservice.project.service.exception.board.member.BoardMemberOperationNotAllowedException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(description = "Operations about board members")
@RestController
@AllArgsConstructor
public class BoardMemberRESTController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BoardMemberRESTController.class);

    private final AuthenticationUserDetailsHelper authenticationUserDetailsHelper;

    private final BoardService boardService;

    private final BoardMemberService boardMemberService;

    @GetMapping(path = "/board/{boardName}/member/details")
    @ApiOperation(httpMethod = "GET", value = "Resource to list all members by the given board name")
    public ResponseEntity<BoardMemberResponseWrapper> retrieveAll(@PathVariable final String boardName) {
        final String ownerUsername = boardService.findByBoardName(boardName)
                .map(board -> board.getOwnerUsername())
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Board does not exist with name=%s", boardName)));

        return ResponseEntity.ok()
                .body(BoardMemberResponseWrapper.builder()
                        .ownerUsername(ownerUsername)
                        .boardMemberUsernames(boardMemberService.findAllUsernameByBoardName(boardName))
                        .build());
    }

    @PostMapping(path = "/board/{boardName}/member/{username}")
    @ApiOperation(httpMethod = "POST", value = "Resource to add a member to the given board")
    public ResponseEntity<Void> addMemberToBoard(@PathVariable final String boardName, @PathVariable final String username) {
        final String requestedBy = authenticationUserDetailsHelper.getUsername();
        LOGGER.info("Received board member addition request [boardName={} username={} requestedBy={}]", boardName, username, requestedBy);

        boardMemberService.add(username, boardName, requestedBy);

        return ResponseEntity.noContent().build();
    }

    @PostMapping(path = "/board/{boardName}/member/team/{teamName}")
    @ApiOperation(httpMethod = "POST", value = "Resource to add the members of the given team to the given board")
    public ResponseEntity<Void> addTeamMembersToBoard(@PathVariable final String boardName, @PathVariable final String teamName) {
        final String requestedBy = authenticationUserDetailsHelper.getUsername();
        LOGGER.info("Received team members addition request [boardName={} teamName={} requestedBy={}]", boardName, teamName, requestedBy);

        boardMemberService.addTeamMembersToBoard(boardName, teamName, requestedBy);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(path = "/board/{boardName}/member/{username}")
    @ApiOperation(httpMethod = "DELETE", value = "Resource to remove a board member from the board team")
    public ResponseEntity<Void> removeBoardMember(@PathVariable final String boardName, @PathVariable final String username) {
        final String requestedBy = authenticationUserDetailsHelper.getUsername();
        LOGGER.info("Received board member deletion request=[username={} boardName={} requestedBy={}]", username, boardName, requestedBy);

        boardMemberService.removeMemberFromBoard(boardName, username, requestedBy);

        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(BoardMemberIsAlreadyAddedException.class)
    protected ResponseEntity<ErrorResponse> handleBoardMemberIsAlreadyAddedException(final BoardMemberIsAlreadyAddedException e) {
        logException(e);
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ErrorResponse.builder()
                        .message(e.getMessage())
                        .build()
                );
    }

    @ExceptionHandler(BoardMemberOperationNotAllowedException.class)
    protected ResponseEntity<ErrorResponse> handleBoardMemberOperationNotAllowedException(final BoardMemberOperationNotAllowedException e) {
        logException(e);
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ErrorResponse.builder()
                        .message(e.getMessage())
                        .build()
                );
    }

    private void logException(final Exception e) {
        LOGGER.error(e.getMessage(), e);
    }
}
