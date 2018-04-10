package com.hyrax.client.email.api.domain;

import lombok.Getter;

public enum SubTemplate {

    REGISTRATION("registration"),
    CREATION("creation"),
    UPDATE("update"),
    REMOVAL("removal"),

    MEMBER_ADDITION("member_addition"),
    MEMBER_REMOVAL("member_removal"),

    ASSIGN_USER("assign_user"),
    ASSIGN_LABEL("assign_label"),
    REMOVE_LABEL("remove_label"),

    MOVE_BETWEEN_COLUMNS("move_between_columns"),

    UNWATCH("unwatch"),
    WATCH("watch");

    @Getter
    private final String subTemplateName;

    SubTemplate(final String subTemplateName) {
        this.subTemplateName = subTemplateName;
    }

}
