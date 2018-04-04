package com.hyrax.microservice.email.rest.api.converter;

import com.hyrax.microservice.email.rest.api.domain.response.ColumnEventSubscriptionResponse;
import com.hyrax.microservice.email.service.api.model.ColumnEventSubscription;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ColumnEventSubscriptionToColumnEventSubscriptionResponseConverter implements Converter<ColumnEventSubscription, ColumnEventSubscriptionResponse> {

    @Override
    public ColumnEventSubscriptionResponse convert(final ColumnEventSubscription columnEventSubscription) {
        return ColumnEventSubscriptionResponse.builder()
                .username(columnEventSubscription.getUsername())
                .columnCreationAction(columnEventSubscription.isColumnCreationAction())
                .columnRemovalAction(columnEventSubscription.isColumnRemovalAction())
                .build();
    }
}
