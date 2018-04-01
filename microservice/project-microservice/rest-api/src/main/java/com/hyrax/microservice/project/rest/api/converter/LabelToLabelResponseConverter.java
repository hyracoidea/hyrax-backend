package com.hyrax.microservice.project.rest.api.converter;

import com.hyrax.microservice.project.rest.api.domain.response.LabelResponse;
import com.hyrax.microservice.project.service.domain.Label;
import com.hyrax.microservice.project.service.domain.LabelColor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class LabelToLabelResponseConverter implements Converter<Label, LabelResponse> {

    @Override
    public LabelResponse convert(final Label label) {
        return LabelResponse.builder()
                .labelId(label.getLabelId())
                .labelName(label.getLabelName())
                .labelColor(LabelColor.builder()
                        .red(label.getRed())
                        .green(label.getGreen())
                        .blue(label.getBlue())
                        .build()
                )
                .build();
    }
}
