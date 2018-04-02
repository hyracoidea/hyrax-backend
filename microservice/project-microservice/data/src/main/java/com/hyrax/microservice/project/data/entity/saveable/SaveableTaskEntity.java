package com.hyrax.microservice.project.data.entity.saveable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaveableTaskEntity {

    private Long taskId;

    private String boardName;

    private String columnName;

    private String taskName;

    private String description;
}
