package com.hyrax.client.email.configuration;

import com.hyrax.client.email.api.properties.EmailEventSubscriptionRESTClientProperties;
import com.hyrax.client.email.api.properties.EmailEventSubscriptionRESTEndpointProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@PropertySources({
        @PropertySource("classpath:email-rest-client.properties"),
        @PropertySource("classpath:email-rest-endpoint.properties")
})
@Import({BoardEmailEventSubscriptionConfiguration.class, TeamEmailEventSubscriptionConfiguration.class, ColumnEmailEventSubscriptionConfiguration.class,
        TaskEmailEventSubscriptionConfiguration.class, LabelEmailEventSubscriptionConfiguration.class})
@EnableConfigurationProperties({EmailEventSubscriptionRESTClientProperties.class, EmailEventSubscriptionRESTEndpointProperties.class})
public class EmailClientAutoConfiguration {

}
