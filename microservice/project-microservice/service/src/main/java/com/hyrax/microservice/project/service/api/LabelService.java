package com.hyrax.microservice.project.service.api;

import com.hyrax.microservice.project.service.domain.LabelColor;

public interface LabelService {

    void create(String boardName, String labelName, LabelColor labelColor, String requestedBy);

    void addLabelToTask(String boardName, Long taskId, Long labelId, String requestedBy);

    void remove(String boardName, Long labelId, String requestedBy);
}
