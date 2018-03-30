package com.hyrax.microservice.project.service.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Task {

    private Long taskId;

    private String taskName;

    private String description;

    private Long taskIndex;
}
