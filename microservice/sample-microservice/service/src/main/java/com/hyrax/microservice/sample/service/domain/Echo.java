package com.hyrax.microservice.sample.service.domain;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Echo {

    private Long id;
    private String message;
}
