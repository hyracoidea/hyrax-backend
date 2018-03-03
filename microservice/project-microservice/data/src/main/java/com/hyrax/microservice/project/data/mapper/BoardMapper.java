package com.hyrax.microservice.project.data.mapper;

import com.hyrax.microservice.project.data.entity.BoardEntity;
import org.apache.ibatis.annotations.Param;

public interface BoardMapper {

    BoardEntity selectByBoardName(@Param("boardName") String boardName);

    void insert(@Param("boardName") String boardName, @Param("ownerUsername") String ownerUsername);

    void delete(@Param("boardName") String boardName);
}
