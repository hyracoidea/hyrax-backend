package com.hyrax.microservice.project.data.mapper;

import com.hyrax.microservice.project.data.entity.ColumnEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ColumnMapper {

    List<ColumnEntity> selectAllByBoardName(@Param("boardName") String boardName);

    void insert(@Param("boardName") String boardName, @Param("columnName") String columnName);

    void updateIndex(@Param("boardName") String boardName, @Param("columnName") String columnName, @Param("newColumnIndex") Long newColumnIndex);
}
