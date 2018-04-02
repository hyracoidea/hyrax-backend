package com.hyrax.microservice.project.service.api;

import com.hyrax.microservice.project.service.domain.Label;
import com.hyrax.microservice.project.service.domain.LabelColor;

import java.util.List;

public interface LabelService {

    List<Label> findAllByBoardName(String boardName);

    void create(String boardName, String labelName, LabelColor labelColor, String requestedBy);

    void addLabelToTask(String boardName, Long taskId, Long labelId, String requestedBy);

    void remove(String boardName, Long labelId, String requestedBy);

    void removeLabelFromTask(String boardName, Long taskId, Long labelId, String requestedBy);
}
