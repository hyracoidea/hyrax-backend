package com.hyrax.microservice.email.rest.api.domain;

import lombok.Getter;

public enum BaseTemplate {

    ACCOUNT("account"),
    BOARD("board"),
    COLUMN("column"),
    LABEL("label"),
    TASK("task"),
    TEAM("team"),
    WATCHED_TASK("watched_task");

    @Getter
    private final String baseTemplateName;

    BaseTemplate(final String baseTemplateName) {
        this.baseTemplateName = baseTemplateName;
    }

}
