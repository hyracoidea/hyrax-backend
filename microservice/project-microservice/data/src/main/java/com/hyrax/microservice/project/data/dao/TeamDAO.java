package com.hyrax.microservice.project.data.dao;

import com.hyrax.microservice.project.data.entity.TeamEntity;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface TeamDAO {

    Optional<TeamEntity> findByTeamName(String teamName);

    List<TeamEntity> findAllByUsername(String username);

    Set<String> findAllTeamMemberNameByTeamName(String teamName);

    void save(TeamEntity teamEntity);

    void addMemberToTeam(String teamName, String username);

    void delete(String teamName);

    void deleteMemberFromTeam(String teamName, String username);
}
