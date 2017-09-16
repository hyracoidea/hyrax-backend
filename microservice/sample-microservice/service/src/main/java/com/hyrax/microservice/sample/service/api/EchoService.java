package com.hyrax.microservice.sample.service.api;

import com.hyrax.microservice.sample.service.domain.Echo;

import java.util.Optional;

public interface EchoService {

    Optional<Echo> getByEchoId(Long echoId);
}
