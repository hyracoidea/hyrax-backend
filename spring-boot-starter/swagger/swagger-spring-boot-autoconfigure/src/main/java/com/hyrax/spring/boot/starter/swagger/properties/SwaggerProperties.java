package com.hyrax.spring.boot.starter.swagger.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.NotNull;

@Data
@ConfigurationProperties(prefix = "swagger.api")
public class SwaggerProperties {

    @NotNull
    private String basePackage;

    private boolean authRequired;
}
