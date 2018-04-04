package com.hyrax.microservice.project.service.api;

public interface WatchTaskService {

    void watchTask(String boardName, Long taskId, String requestedBy);

    void unwatch(String boardName, Long taskId, String requestedBy);
}
