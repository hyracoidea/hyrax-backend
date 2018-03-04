package com.hyrax.microservice.project.data.mapper;

import org.apache.ibatis.annotations.Param;

public interface ColumnMapper {

    void insert(@Param("boardName") String boardName, @Param("columnName") String columnName);
}
