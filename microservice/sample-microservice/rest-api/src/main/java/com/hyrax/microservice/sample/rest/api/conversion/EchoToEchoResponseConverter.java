package com.hyrax.microservice.sample.rest.api.conversion;

import com.hyrax.microservice.sample.rest.api.response.EchoResponse;
import com.hyrax.microservice.sample.service.domain.Echo;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class EchoToEchoResponseConverter implements Converter<Echo, EchoResponse> {

    @Override
    public EchoResponse convert(final Echo echo) {
        EchoResponse echoResponse = null;
        if (Objects.nonNull(echo)) {
            echoResponse = convertToEchoResponse(echo);
        }
        return echoResponse;
    }

    private EchoResponse convertToEchoResponse(final Echo echo) {
        return EchoResponse.builder()
                .id(echo.getId())
                .message(echo.getMessage())
                .build();
    }
}
