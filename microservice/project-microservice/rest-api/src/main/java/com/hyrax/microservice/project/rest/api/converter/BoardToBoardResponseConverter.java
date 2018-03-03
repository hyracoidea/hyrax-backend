package com.hyrax.microservice.project.rest.api.converter;

import com.hyrax.microservice.project.rest.api.domain.response.BoardResponse;
import com.hyrax.microservice.project.service.domain.Board;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class BoardToBoardResponseConverter implements Converter<Board, BoardResponse> {

    @Override
    public BoardResponse convert(final Board board) {
        return BoardResponse.builder()
                .boardName(board.getBoardName())
                .ownerUsername(board.getOwnerUsername())
                .build();
    }
}
