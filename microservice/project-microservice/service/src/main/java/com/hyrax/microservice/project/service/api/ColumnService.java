package com.hyrax.microservice.project.service.api;

import com.hyrax.microservice.project.service.domain.Column;

import java.util.List;

public interface ColumnService {

    List<Column> findAllByBoardName(String boardName);

    void create(String boardName, String columnName, String requestedBy);

    void updateIndex(String boardName, String columnName, String requestedBy, long from, long to);
}
