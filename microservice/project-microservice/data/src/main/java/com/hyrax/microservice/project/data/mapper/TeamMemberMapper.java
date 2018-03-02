package com.hyrax.microservice.project.data.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TeamMemberMapper {

    List<String> selectAllUsernameByTeamName(String teamName);

    void insert(@Param("username") String username, @Param("teamName") String teamName);

    void delete(@Param("username") String username, @Param("teamName") String teamName);
}
