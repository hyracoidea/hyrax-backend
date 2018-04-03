package com.hyrax.microservice.project.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SingleTaskEntity {

    private Long taskId;

    private String taskName;

    private String description;

    private String assignedUsername;

    private Set<LabelEntity> labels;

    private Set<String> watchedUsers;
}
