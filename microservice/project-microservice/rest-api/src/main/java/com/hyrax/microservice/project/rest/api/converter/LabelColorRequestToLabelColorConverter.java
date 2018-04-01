package com.hyrax.microservice.project.rest.api.converter;

import com.hyrax.microservice.project.rest.api.domain.request.LabelColorRequest;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class LabelColorRequestToLabelColorConverter implements Converter<LabelColorRequest, com.hyrax.microservice.project.service.domain.LabelColor> {

    @Override
    public com.hyrax.microservice.project.service.domain.LabelColor convert(final LabelColorRequest labelColorRequest) {
        return com.hyrax.microservice.project.service.domain.LabelColor.builder()
                .red(labelColorRequest.getRed())
                .green(labelColorRequest.getGreen())
                .blue(labelColorRequest.getBlue())
                .build();
    }
}
