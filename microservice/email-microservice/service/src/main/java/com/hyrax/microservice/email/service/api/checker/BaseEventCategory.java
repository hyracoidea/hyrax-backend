package com.hyrax.microservice.email.service.api.checker;

import lombok.Getter;

public enum BaseEventCategory {

    ACCOUNT("account"),
    BOARD("board"),
    COLUMN("column"),
    LABEL("label"),
    TASK("task"),
    TEAM("team"),
    WATCHED_TASK("watched_task");

    @Getter
    private final String baseTemplateName;

    BaseEventCategory(final String baseTemplateName) {
        this.baseTemplateName = baseTemplateName;
    }

}
