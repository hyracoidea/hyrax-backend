package com.hyrax.microservice.sample.data.annotation;

import com.hyrax.microservice.sample.data.configuration.DataModuleConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.lang.annotation.*;

@SpringBootTest(classes = DataModuleConfiguration.class)
@ActiveProfiles(profiles = "test")
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Inherited
public @interface IntegrationTest {

}
