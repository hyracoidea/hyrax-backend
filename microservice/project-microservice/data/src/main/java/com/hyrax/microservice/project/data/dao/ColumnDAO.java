package com.hyrax.microservice.project.data.dao;

import com.hyrax.microservice.project.data.entity.ColumnEntity;

import java.util.List;

public interface ColumnDAO {

    List<ColumnEntity> findAllByBoardName(String boardName);

    void save(String boardName, String columnName);

    void updateColumnPosition(String boardName, String columnName, Long newColumnIndex);

    void deleteByBoardNameAndColumnName(String boardName, String columnName);
}
