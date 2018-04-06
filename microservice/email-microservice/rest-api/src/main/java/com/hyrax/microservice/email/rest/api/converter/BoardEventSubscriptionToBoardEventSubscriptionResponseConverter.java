package com.hyrax.microservice.email.rest.api.converter;

import com.hyrax.client.email.api.response.BoardEventSubscriptionResponse;
import com.hyrax.microservice.email.service.api.model.BoardEventSubscription;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class BoardEventSubscriptionToBoardEventSubscriptionResponseConverter implements Converter<BoardEventSubscription, BoardEventSubscriptionResponse> {

    @Override
    public BoardEventSubscriptionResponse convert(final BoardEventSubscription boardEventSubscription) {
        return BoardEventSubscriptionResponse.builder()
                .username(boardEventSubscription.getUsername())
                .boardCreationAction(boardEventSubscription.isBoardCreationAction())
                .boardMemberAdditionAction(boardEventSubscription.isBoardMemberAdditionAction())
                .boardMemberRemovalAction(boardEventSubscription.isBoardMemberRemovalAction())
                .boardRemovalAction(boardEventSubscription.isBoardRemovalAction())
                .build();
    }
}
