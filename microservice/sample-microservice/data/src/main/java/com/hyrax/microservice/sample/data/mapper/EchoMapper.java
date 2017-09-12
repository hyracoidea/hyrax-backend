package com.hyrax.microservice.sample.data.mapper;

import com.hyrax.microservice.sample.data.entity.EchoEntity;

public interface EchoMapper {

    EchoEntity findById(Long id);
}
