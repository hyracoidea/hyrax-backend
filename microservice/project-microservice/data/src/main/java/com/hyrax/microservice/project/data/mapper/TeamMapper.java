package com.hyrax.microservice.project.data.mapper;

import com.hyrax.microservice.project.data.entity.TeamEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TeamMapper {

    TeamEntity selectByName(String name);

    List<TeamEntity> selectAllTeamByUsername(@Param("username") String username);

    void insert(TeamEntity teamEntity);

    void delete(String teamName);
}
