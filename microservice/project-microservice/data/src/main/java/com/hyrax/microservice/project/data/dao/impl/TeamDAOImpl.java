package com.hyrax.microservice.project.data.dao.impl;

import com.hyrax.microservice.project.data.dao.TeamDAO;
import com.hyrax.microservice.project.data.entity.TeamEntity;
import com.hyrax.microservice.project.data.mapper.TeamMapper;
import com.hyrax.microservice.project.data.mapper.TeamMemberMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
@AllArgsConstructor
public class TeamDAOImpl implements TeamDAO {

    private final TeamMapper teamMapper;

    private final TeamMemberMapper teamMemberMapper;

    @Override
    public Optional<TeamEntity> findByTeamName(final String teamName) {
        return Optional.ofNullable(teamMapper.selectByName(teamName));
    }

    @Override
    public List<TeamEntity> findAllByUsername(final String username) {
        return teamMapper.selectAllTeamByUsername(username);
    }

    @Override
    public Set<String> findAllTeamMemberNameByTeamName(final String teamName) {
        return teamMemberMapper.selectAllUsernameByTeamName(teamName);
    }

    @Override
    public void save(final TeamEntity teamEntity) {
        teamMapper.insert(teamEntity);
    }

    @Override
    public void addMemberToTeam(final String teamName, final String username) {
        teamMemberMapper.insert(username, teamName);
    }

    @Override
    public void delete(final String teamName) {
        teamMemberMapper.deleteAllByTeamName(teamName);
        teamMapper.delete(teamName);
    }

    @Override
    public void deleteMemberFromTeam(final String teamName, final String username) {
        teamMemberMapper.delete(username, teamName);
    }
}
