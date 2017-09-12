package com.hyrax.microservice.sample.data.entity;

import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class EchoEntity extends AbstractEntity<Long> {

    private String message;

    @Builder
    public EchoEntity(final Long id, final String message) {
        this.id = id;
        this.message = message;
    }
}
