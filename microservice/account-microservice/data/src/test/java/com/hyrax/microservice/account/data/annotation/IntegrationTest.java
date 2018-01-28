package com.hyrax.microservice.account.data.annotation;

import com.hyrax.microservice.account.data.configuration.DataModuleConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@SpringBootTest(classes = DataModuleConfiguration.class)
@Transactional
@ActiveProfiles(profiles = "test")
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Inherited
public @interface IntegrationTest {

}
