package com.hyrax.microservice.project.data.mapper;

import com.hyrax.microservice.project.data.entity.TeamEntity;

public interface TeamMapper {

    TeamEntity selectByName(String name);

    void insert(TeamEntity teamEntity);

    void delete(String teamName);
}
