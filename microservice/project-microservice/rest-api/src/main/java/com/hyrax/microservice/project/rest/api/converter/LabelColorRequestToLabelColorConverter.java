package com.hyrax.microservice.project.rest.api.converter;

import com.hyrax.microservice.project.rest.api.domain.request.LabelColorRequest;
import com.hyrax.microservice.project.service.domain.LabelColor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class LabelColorRequestToLabelColorConverter implements Converter<LabelColorRequest, LabelColor> {

    @Override
    public LabelColor convert(final LabelColorRequest labelColorRequest) {
        return LabelColor.builder()
                .red(labelColorRequest.getRed())
                .green(labelColorRequest.getGreen())
                .blue(labelColorRequest.getBlue())
                .build();
    }
}
