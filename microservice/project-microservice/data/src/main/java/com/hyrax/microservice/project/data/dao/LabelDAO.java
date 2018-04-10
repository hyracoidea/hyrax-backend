package com.hyrax.microservice.project.data.dao;

import com.hyrax.microservice.project.data.entity.LabelEntity;

import java.util.List;
import java.util.Optional;

public interface LabelDAO {

    List<LabelEntity> findAllByBoardName(String boardName);

    Optional<LabelEntity> findByLabelId(String boardName, Long labelId);

    void save(String boardName, String labelName, int red, int green, int blue);

    void addToTask(String boardName, Long taskId, Long labelId);

    void delete(String boardName, Long labelId);

    void deleteFromTask(String boardName, Long taskId, Long labelId);
}
