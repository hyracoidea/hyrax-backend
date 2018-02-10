package com.hyrax.microservice.account.rest.api.converter;

import com.hyrax.microservice.account.rest.api.domain.response.RequestValidationDetail;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.validation.ObjectError;

import java.util.Objects;

@Component
public class ObjectErrorToRequestValidationDetailConverter implements Converter<ObjectError, RequestValidationDetail> {

    @Override
    public RequestValidationDetail convert(final ObjectError objectError) {
        RequestValidationDetail requestValidationDetail = null;

        if (Objects.nonNull(objectError)) {
            requestValidationDetail = RequestValidationDetail.builder()
                    .field(StringUtils.uncapitalize(objectError.getCode()))
                    .message(objectError.getDefaultMessage())
                    .build();
        }

        return requestValidationDetail;
    }
}
