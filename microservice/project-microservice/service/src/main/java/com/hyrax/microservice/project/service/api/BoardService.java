package com.hyrax.microservice.project.service.api;

import com.hyrax.microservice.project.service.domain.Board;

import java.util.List;
import java.util.Optional;

public interface BoardService {

    Optional<Board> findByBoardName(String boardName);

    List<Board> findAllByUsername(String username);

    void create(String boardName, String ownerUsername);

    void remove(String boardName, String requestedBy);
}
