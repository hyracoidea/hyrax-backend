package com.hyrax.microservice.sample.service.api.impl;

import com.google.common.base.Preconditions;
import com.hyrax.microservice.sample.data.entity.EchoEntity;
import com.hyrax.microservice.sample.data.mapper.EchoMapper;
import com.hyrax.microservice.sample.service.api.EchoService;
import com.hyrax.microservice.sample.service.domain.Echo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
public class EchoServiceImpl implements EchoService {

    private final EchoMapper echoMapper;
    private final ModelMapper modelMapper;

    @Autowired
    public EchoServiceImpl(final EchoMapper echoMapper, final ModelMapper modelMapper) {
        this.echoMapper = echoMapper;
        this.modelMapper = modelMapper;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Echo> getByEchoId(final Long echoId) {
        Preconditions.checkArgument(Objects.nonNull(echoId));

        Echo result = null;
        final EchoEntity echoEntity = echoMapper.findById(echoId);
        if (Objects.nonNull(echoEntity)) {
            result = modelMapper.map(echoEntity, Echo.class);
        }

        return Optional.ofNullable(result);
    }
}
