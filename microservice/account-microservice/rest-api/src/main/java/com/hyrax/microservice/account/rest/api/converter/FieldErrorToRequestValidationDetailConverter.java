package com.hyrax.microservice.account.rest.api.converter;

import com.hyrax.microservice.account.rest.api.response.RequestValidationDetail;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

import java.util.Objects;

@Component
public class FieldErrorToRequestValidationDetailConverter implements Converter<FieldError, RequestValidationDetail> {

    @Override
    public RequestValidationDetail convert(final FieldError fieldError) {
        RequestValidationDetail requestValidationDetail = null;

        if (Objects.nonNull(fieldError)) {
            requestValidationDetail = RequestValidationDetail.builder()
                    .field(fieldError.getField())
                    .message(fieldError.getDefaultMessage())
                    .build();
        }

        return requestValidationDetail;
    }
}
