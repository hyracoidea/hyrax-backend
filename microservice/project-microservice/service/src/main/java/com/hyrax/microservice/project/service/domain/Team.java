package com.hyrax.microservice.project.service.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Team {

    private Long teamId;

    private String name;

    private String description;

    private String ownerUsername;
}
