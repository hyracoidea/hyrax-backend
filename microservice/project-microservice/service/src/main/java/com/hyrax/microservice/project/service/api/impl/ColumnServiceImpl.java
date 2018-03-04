package com.hyrax.microservice.project.service.api.impl;

import com.hyrax.microservice.project.data.mapper.ColumnMapper;
import com.hyrax.microservice.project.service.api.BoardService;
import com.hyrax.microservice.project.service.api.ColumnService;
import com.hyrax.microservice.project.service.domain.Board;
import com.hyrax.microservice.project.service.domain.Column;
import com.hyrax.microservice.project.service.exception.board.BoardNotFoundException;
import com.hyrax.microservice.project.service.exception.column.ColumnAdditionOperationNotAllowedException;
import com.hyrax.microservice.project.service.exception.column.ColumnAlreadyExistsException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ColumnServiceImpl implements ColumnService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ColumnServiceImpl.class);

    private final BoardService boardService;

    private final ColumnMapper columnMapper;

    private final ModelMapper modelMapper;

    @Override
    @Transactional(readOnly = true)
    public List<Column> findAllByBoardName(final String boardName) {
        return columnMapper.selectAllByBoardName(boardName)
                .stream()
                .map(columnEntity -> modelMapper.map(columnEntity, Column.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void create(final String boardName, final String columnName, final String requestedBy) {
        final Board board = boardService.findByBoardName(boardName).orElseThrow(() -> new BoardNotFoundException(boardName));

        if (board.getOwnerUsername().equals(requestedBy)) {
            try {
                LOGGER.info("Trying to save the column = [boardName={} columnName={}]", boardName, columnName);
                columnMapper.insert(boardName, columnName);
                LOGGER.info("Column saving was successful [boardName={} columnName={}]", boardName, columnName);
            } catch (final DuplicateKeyException e) {
                final String errorMessage = String.format("Column already exists with this name=%s on this board=%s", columnName, boardName);
                LOGGER.error(errorMessage, e);
                throw new ColumnAlreadyExistsException(errorMessage);
            }
        } else {
            throw new ColumnAdditionOperationNotAllowedException(requestedBy);
        }
    }
}
