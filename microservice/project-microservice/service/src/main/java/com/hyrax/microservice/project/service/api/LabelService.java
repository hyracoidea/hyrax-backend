package com.hyrax.microservice.project.service.api;

import com.hyrax.microservice.project.service.domain.LabelColor;

public interface LabelService {

    void create(String boardName, String labelName, LabelColor labelColor, String requestedBy);
}
