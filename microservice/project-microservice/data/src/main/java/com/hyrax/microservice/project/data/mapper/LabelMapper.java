package com.hyrax.microservice.project.data.mapper;

import com.hyrax.microservice.project.data.entity.LabelEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LabelMapper {

    List<LabelEntity> selectAllByBoardName(@Param("boardName") String boardName);

    void insert(@Param("boardName") String boardName, @Param("labelName") String labelName,
                @Param("red") int red, @Param("green") int green, @Param("blue") int blue);

    void addLabelToTask(@Param("boardName") String boardName, @Param("taskId") Long taskId, @Param("labelId") Long labelId);

    void delete(@Param("boardName") String boardName, @Param("labelId") Long labelId);

    void deleteLabelFromTask(@Param("boardName") String boardName, @Param("taskId") Long taskId, @Param("labelId") Long labelId);

    void deleteLabelsFromTasks(@Param("boardName") String boardName, @Param("labelId") Long labelId);

    void deleteAllLabelFromTask(@Param("boardName") String boardName, @Param("taskId") Long taskId);

    void deleteAllLabelFromTasksByColumn(@Param("boardName") String boardName, @Param("columnName") String columnName);

    void deleteAllLabelFromTasksByBoard(@Param("boardName") String boardName);
}
