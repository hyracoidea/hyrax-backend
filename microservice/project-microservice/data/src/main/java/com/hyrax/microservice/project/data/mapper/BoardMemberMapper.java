package com.hyrax.microservice.project.data.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BoardMemberMapper {

    List<String> selectAllUsernameByBoardName(@Param("boardName") String boardName);

    void insert(@Param("username") String username, @Param("boardName") String boardName);

    void deleteMemberFromBoard(@Param("boardName") String boardName, @Param("username") String username);

    void deleteMembersFromBoard(@Param("boardName") String boardName);
}
