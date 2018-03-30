package com.hyrax.microservice.project.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskEntity {

    private Long taskId;

    private String taskName;

    private String description;

    private Long taskIndex;
}
