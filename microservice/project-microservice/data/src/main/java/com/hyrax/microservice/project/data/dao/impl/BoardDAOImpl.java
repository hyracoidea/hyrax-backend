package com.hyrax.microservice.project.data.dao.impl;

import com.hyrax.microservice.project.data.dao.BoardDAO;
import com.hyrax.microservice.project.data.entity.BoardEntity;
import com.hyrax.microservice.project.data.mapper.BoardMapper;
import com.hyrax.microservice.project.data.mapper.BoardMemberMapper;
import com.hyrax.microservice.project.data.mapper.ColumnMapper;
import com.hyrax.microservice.project.data.mapper.LabelMapper;
import com.hyrax.microservice.project.data.mapper.TaskMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class BoardDAOImpl implements BoardDAO {

    private final BoardMapper boardMapper;

    private final BoardMemberMapper boardMemberMapper;

    private final ColumnMapper columnMapper;

    private final TaskMapper taskMapper;

    private final LabelMapper labelMapper;

    @Override
    public Optional<BoardEntity> findByBoardName(final String boardName) {
        return Optional.ofNullable(boardMapper.selectByBoardName(boardName));
    }

    @Override
    public List<BoardEntity> findAllByUsername(final String username) {
        return boardMapper.selectAllBoardByUsername(username);
    }

    @Override
    public List<String> findAllBoardMemberNameByBoardName(final String boardName) {
        return boardMemberMapper.selectAllUsernameByBoardName(boardName);
    }

    @Override
    public void save(final String boardName, final String ownerUsername) {
        boardMapper.insert(boardName, ownerUsername);
    }

    @Override
    public void addMemberToBoard(final String boardName, final String username) {
        boardMemberMapper.insert(username, boardName);
    }

    @Override
    public void deleteByBoardName(final String boardName) {
        labelMapper.deleteAllLabelFromTasksByBoard(boardName);
        labelMapper.deleteAllLabelFromBoard(boardName);
        taskMapper.deleteAllByBoardName(boardName);
        columnMapper.deleteAllByBoardName(boardName);
        boardMapper.delete(boardName);
    }
}
