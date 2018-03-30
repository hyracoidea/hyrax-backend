package com.hyrax.microservice.project.data.mapper;

import org.apache.ibatis.annotations.Param;

public interface TaskMapper {

    void insert(@Param("boardName") String boardName, @Param("columnName") String columnName,
                @Param("taskName") String taskName, @Param("description") String description);
}
