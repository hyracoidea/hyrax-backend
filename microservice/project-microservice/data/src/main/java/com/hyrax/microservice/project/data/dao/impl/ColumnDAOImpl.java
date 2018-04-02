package com.hyrax.microservice.project.data.dao.impl;

import com.hyrax.microservice.project.data.dao.ColumnDAO;
import com.hyrax.microservice.project.data.entity.ColumnEntity;
import com.hyrax.microservice.project.data.mapper.ColumnMapper;
import com.hyrax.microservice.project.data.mapper.LabelMapper;
import com.hyrax.microservice.project.data.mapper.TaskMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class ColumnDAOImpl implements ColumnDAO {

    private final ColumnMapper columnMapper;

    private final TaskMapper taskMapper;

    private final LabelMapper labelMapper;

    @Override
    public List<ColumnEntity> findAllByBoardName(final String boardName) {
        return columnMapper.selectAllByBoardName(boardName);
    }

    @Override
    public void save(final String boardName, final String columnName) {
        columnMapper.insert(boardName, columnName);
    }

    @Override
    public void updateColumnPosition(final String boardName, final String columnName, final Long newColumnIndex) {
        columnMapper.updateIndex(boardName, columnName, newColumnIndex);
    }

    @Override
    public void deleteByBoardNameAndColumnName(final String boardName, final String columnName) {
        labelMapper.deleteAllLabelFromTasksByColumn(boardName, columnName);
        taskMapper.deleteAllByBoardNameAndColumnName(boardName, columnName);
        columnMapper.delete(boardName, columnName);
    }
}
