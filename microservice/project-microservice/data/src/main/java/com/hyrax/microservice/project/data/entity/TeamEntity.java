package com.hyrax.microservice.project.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamEntity {

    private Long teamId;

    private String name;

    private String description;

    private String ownerUsername;
}
