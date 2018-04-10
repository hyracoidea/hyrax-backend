package com.hyrax.microservice.project.data.dao;

import com.hyrax.microservice.project.data.entity.BoardEntity;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface BoardDAO {

    Optional<BoardEntity> findByBoardName(String boardName);

    List<BoardEntity> findAllByUsername(String username);

    Set<String> findAllBoardMemberNameByBoardName(String boardName);

    void save(String boardName, String ownerUsername);

    void addMemberToBoard(String boardName, String username);

    void deleteMemberFromBoard(String boardName, String username);

    void deleteByBoardName(String boardName);
}
