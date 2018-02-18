package com.hyrax.client.account.configuration;

import com.hyrax.client.account.api.properties.AccountRESTClientProperties;
import com.hyrax.client.account.api.service.AccountRESTService;
import com.hyrax.client.account.api.service.impl.AccountRESTClient;
import com.hyrax.client.account.api.service.impl.AccountRESTServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

@Configuration
@PropertySource(value = "classpath:account-rest-client.properties")
@EnableConfigurationProperties(AccountRESTClientProperties.class)
public class AccountClientAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public Client client() {
        return ClientBuilder.newClient();
    }

    @Bean
    @ConditionalOnMissingBean
    public AccountRESTClient accountRESTClient(final Client client, final AccountRESTClientProperties accountRESTClientProperties) {
        return new AccountRESTClient(client, accountRESTClientProperties);
    }

    @Bean
    @ConditionalOnMissingBean
    public AccountRESTService accountRESTService(final AccountRESTClient accountRESTClient) {
        return new AccountRESTServiceImpl(accountRESTClient);
    }
}
