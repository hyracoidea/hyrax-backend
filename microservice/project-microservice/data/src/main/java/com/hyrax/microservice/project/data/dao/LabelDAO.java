package com.hyrax.microservice.project.data.dao;

import com.hyrax.microservice.project.data.entity.LabelEntity;

import java.util.List;

public interface LabelDAO {

    List<LabelEntity> findAllByBoardName(String boardName);

    void save(String boardName, String labelName, int red, int green, int blue);

    void addToTask(String boardName, Long taskId, Long labelId);

    void delete(String boardName, Long labelId);

    void deleteFromTask(String boardName, Long taskId, Long labelId);
}
