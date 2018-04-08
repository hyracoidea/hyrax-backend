package com.hyrax.client.email.configuration;

import com.hyrax.client.email.api.properties.EmailEventRESTClientProperties;
import com.hyrax.client.email.api.properties.EmailEventRESTEndpointProperties;
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
        TaskEmailEventSubscriptionConfiguration.class, LabelEmailEventSubscriptionConfiguration.class, EmailSenderConfiguration.class})
@EnableConfigurationProperties({EmailEventRESTClientProperties.class, EmailEventRESTEndpointProperties.class})
public class EmailClientAutoConfiguration {

}
