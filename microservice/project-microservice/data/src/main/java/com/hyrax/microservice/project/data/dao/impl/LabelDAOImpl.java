package com.hyrax.microservice.project.data.dao.impl;

import com.hyrax.microservice.project.data.dao.LabelDAO;
import com.hyrax.microservice.project.data.entity.LabelEntity;
import com.hyrax.microservice.project.data.mapper.LabelMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class LabelDAOImpl implements LabelDAO {

    private final LabelMapper labelMapper;

    @Override
    public List<LabelEntity> findAllByBoardName(final String boardName) {
        return labelMapper.selectAllByBoardName(boardName);
    }

    @Override
    public void save(final String boardName, final String labelName, final int red, final int green, final int blue) {
        labelMapper.insert(boardName, labelName, red, green, blue);
    }

    @Override
    public void addToTask(final String boardName, final Long taskId, final Long labelId) {
        labelMapper.addLabelToTask(boardName, taskId, labelId);
    }

    @Override
    public void delete(final String boardName, final Long labelId) {
        labelMapper.deleteLabelsFromTasks(boardName, labelId);
        labelMapper.delete(boardName, labelId);
    }

    @Override
    public void deleteFromTask(final String boardName, final Long taskId, final Long labelId) {
        labelMapper.deleteLabelFromTask(boardName, taskId, labelId);
    }
}
