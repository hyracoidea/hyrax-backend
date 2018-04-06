package com.hyrax.microservice.email.rest.api.converter;

import com.hyrax.client.email.api.response.LabelEventSubscriptionResponse;
import com.hyrax.microservice.email.service.api.model.LabelEventSubscription;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class LabelEventSubscriptionToLabelEventSubscriptionResponseConverter implements Converter<LabelEventSubscription, LabelEventSubscriptionResponse> {

    @Override
    public LabelEventSubscriptionResponse convert(final LabelEventSubscription labelEventSubscription) {
        return LabelEventSubscriptionResponse.builder()
                .username(labelEventSubscription.getUsername())
                .labelCreationAction(labelEventSubscription.isLabelCreationAction())
                .labelRemovalAction(labelEventSubscription.isLabelRemovalAction())
                .build();
    }
}
