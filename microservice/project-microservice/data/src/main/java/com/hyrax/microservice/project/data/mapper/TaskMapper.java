package com.hyrax.microservice.project.data.mapper;

import com.hyrax.microservice.project.data.entity.TaskEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TaskMapper {

    List<TaskEntity> selectAllByBoardNameAndColumnName(@Param("boardName") String boardName, @Param("columnName") String columnName);

    void insert(@Param("boardName") String boardName, @Param("columnName") String columnName,
                @Param("taskName") String taskName, @Param("description") String description);
}
