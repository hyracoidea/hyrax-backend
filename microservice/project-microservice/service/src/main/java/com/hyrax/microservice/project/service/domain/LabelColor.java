package com.hyrax.microservice.project.service.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LabelColor {

    private int red;

    private int green;

    private int blue;
}
