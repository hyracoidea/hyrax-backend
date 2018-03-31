package com.hyrax.microservice.project.rest.api.domain.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LabelColorRequest {

    private static final String LABEL_COLOR_MESSAGE = "The value should be between 0 and 255";

    @Min(value = 0, message = LABEL_COLOR_MESSAGE)
    @Max(value = 255, message = LABEL_COLOR_MESSAGE)
    private int red;

    @Min(value = 0, message = LABEL_COLOR_MESSAGE)
    @Max(value = 255, message = LABEL_COLOR_MESSAGE)
    private int green;

    @Min(value = 0, message = LABEL_COLOR_MESSAGE)
    @Max(value = 255, message = LABEL_COLOR_MESSAGE)
    private int blue;
}
