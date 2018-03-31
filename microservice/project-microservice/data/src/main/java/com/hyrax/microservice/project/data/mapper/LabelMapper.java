package com.hyrax.microservice.project.data.mapper;

import org.apache.ibatis.annotations.Param;

public interface LabelMapper {

    void insert(@Param("boardName") String boardName, @Param("labelName") String labelName,
                @Param("red") int red, @Param("green") int green, @Param("blue") int blue);
}
