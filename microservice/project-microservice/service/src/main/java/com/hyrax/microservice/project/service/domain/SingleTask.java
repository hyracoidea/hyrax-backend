package com.hyrax.microservice.project.service.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SingleTask {

    private Long taskId;

    private String taskName;

    private String description;

    private String assignedUsername;

    private Set<Label> labels;

    private Set<String> watchedUsers;
}
