package com.hyrax.microservice.project.data.mapper;

import org.apache.ibatis.annotations.Param;

public interface BoardMapper {

    void insert(@Param("boardName") String boardName, @Param("ownerUsername") String ownerUsername);
}
