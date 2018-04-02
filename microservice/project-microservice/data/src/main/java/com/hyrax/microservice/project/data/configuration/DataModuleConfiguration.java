package com.hyrax.microservice.project.data.configuration;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableAutoConfiguration
@EnableTransactionManagement
@MapperScan(basePackages = "com.hyrax.microservice.project.data.mapper")
@ComponentScan(basePackages = "com.hyrax.microservice.project.data")
public class DataModuleConfiguration {
}