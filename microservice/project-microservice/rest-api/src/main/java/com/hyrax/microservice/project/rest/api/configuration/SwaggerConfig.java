package com.hyrax.microservice.project.rest.api.configuration;

import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .globalOperationParameters(globalOperationParameters())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.hyrax.microservice.project.rest.api.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private List<Parameter> globalOperationParameters() {
        return Lists.newArrayList(new ParameterBuilder()
                .name("Authorization")
                .description("Authorization header")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(true)
                .build());
    }

}
