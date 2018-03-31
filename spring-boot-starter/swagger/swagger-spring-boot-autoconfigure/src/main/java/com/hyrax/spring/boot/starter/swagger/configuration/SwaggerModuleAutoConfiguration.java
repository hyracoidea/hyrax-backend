package com.hyrax.spring.boot.starter.swagger.configuration;

import com.google.common.collect.Lists;
import com.hyrax.spring.boot.starter.swagger.properties.SwaggerProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
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
@EnableConfigurationProperties(SwaggerProperties.class)
public class SwaggerModuleAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public Docket api(final SwaggerProperties swaggerProperties) {
        return new Docket(DocumentationType.SWAGGER_2)
                .globalOperationParameters(globalOperationParameters(swaggerProperties))
                .select()
                .apis(RequestHandlerSelectors.basePackage(swaggerProperties.getBasePackage()))
                .paths(PathSelectors.any())
                .build();
    }

    private List<Parameter> globalOperationParameters(final SwaggerProperties swaggerProperties) {
        return Lists.newArrayList(new ParameterBuilder()
                .name("Authorization")
                .description("Authorization header")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(swaggerProperties.isAuthRequired())
                .build());
    }
}
