package com.hyrax.microservice.email.service.configuration;

import com.hyrax.microservice.email.data.configuration.DataModuleConfiguration;
import com.hyrax.microservice.email.data.entity.BoardEventSubscriptionEntity;
import com.hyrax.microservice.email.data.entity.ColumnEventSubscriptionEntity;
import com.hyrax.microservice.email.data.entity.LabelEventSubscriptionEntity;
import com.hyrax.microservice.email.data.entity.TaskEventSubscriptionEntity;
import com.hyrax.microservice.email.data.entity.TeamEventSubscriptionEntity;
import com.hyrax.microservice.email.data.repository.EventSubscriptionRepository;
import com.hyrax.microservice.email.service.api.BoardEventSubscriptionService;
import com.hyrax.microservice.email.service.api.ColumnEventSubscriptionService;
import com.hyrax.microservice.email.service.api.LabelEventSubscriptionService;
import com.hyrax.microservice.email.service.api.TaskEventSubscriptionService;
import com.hyrax.microservice.email.service.api.TeamEventSubscriptionService;
import com.hyrax.microservice.email.service.api.impl.BoardEventSubscriptionServiceImpl;
import com.hyrax.microservice.email.service.api.impl.ColumnEventSubscriptionServiceImpl;
import com.hyrax.microservice.email.service.api.impl.LabelEventSubscriptionServiceImpl;
import com.hyrax.microservice.email.service.api.impl.TaskEventSubscriptionServiceImpl;
import com.hyrax.microservice.email.service.api.impl.TeamEventSubscriptionServiceImpl;
import com.hyrax.microservice.email.service.api.model.BoardEventSubscription;
import com.hyrax.microservice.email.service.api.model.ColumnEventSubscription;
import com.hyrax.microservice.email.service.api.model.LabelEventSubscription;
import com.hyrax.microservice.email.service.api.model.TaskEventSubscription;
import com.hyrax.microservice.email.service.api.model.TeamEventSubscription;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = "com.hyrax.microservice.email.service")
@Import(DataModuleConfiguration.class)
public class ServiceModuleConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public BoardEventSubscriptionService boardEventSubscriptionService(final EventSubscriptionRepository<BoardEventSubscriptionEntity> eventSubscriptionRepository,
                                                                       final ModelMapper modelMapper) {
        return new BoardEventSubscriptionServiceImpl(eventSubscriptionRepository, modelMapper, BoardEventSubscription.class, BoardEventSubscriptionEntity.class);
    }

    @Bean
    public ColumnEventSubscriptionService columnEventSubscriptionService(final EventSubscriptionRepository<ColumnEventSubscriptionEntity> eventSubscriptionRepository,
                                                                         final ModelMapper modelMapper) {
        return new ColumnEventSubscriptionServiceImpl(eventSubscriptionRepository, modelMapper, ColumnEventSubscription.class, ColumnEventSubscriptionEntity.class);
    }

    @Bean
    public LabelEventSubscriptionService labelEventSubscriptionService(final EventSubscriptionRepository<LabelEventSubscriptionEntity> eventSubscriptionRepository,
                                                                       final ModelMapper modelMapper) {
        return new LabelEventSubscriptionServiceImpl(eventSubscriptionRepository, modelMapper, LabelEventSubscription.class, LabelEventSubscriptionEntity.class);
    }

    @Bean
    public TaskEventSubscriptionService taskEventSubscriptionService(final EventSubscriptionRepository<TaskEventSubscriptionEntity> eventSubscriptionRepository,
                                                                     final ModelMapper modelMapper) {
        return new TaskEventSubscriptionServiceImpl(eventSubscriptionRepository, modelMapper, TaskEventSubscription.class, TaskEventSubscriptionEntity.class);
    }

    @Bean
    public TeamEventSubscriptionService teamEventSubscriptionService(final EventSubscriptionRepository<TeamEventSubscriptionEntity> eventSubscriptionRepository,
                                                                     final ModelMapper modelMapper) {
        return new TeamEventSubscriptionServiceImpl(eventSubscriptionRepository, modelMapper, TeamEventSubscription.class, TeamEventSubscriptionEntity.class);
    }
}

