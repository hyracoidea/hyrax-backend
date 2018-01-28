package com.hyrax.microservice.account.data.configuration;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableAutoConfiguration
@EnableTransactionManagement
@MapperScan(basePackages = "com.hyrax.microservice.account.data.mapper")
@ComponentScan(basePackages = "com.hyrax.microservice.account.data.mapper")
public class DataModuleConfiguration {
}