package com.hyrax.microservice.account.rest.api.validation.bindingresult;

import com.google.common.collect.Lists;
import com.hyrax.microservice.account.rest.api.domain.response.RequestValidationDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BindingResultProcessor {

    private final ConversionService conversionService;

    @Autowired
    public BindingResultProcessor(final ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    public ProcessedBindingResult process(final BindingResult bindingResult) {
        final List<RequestValidationDetail> requestValidationDetailEntries = Lists.newArrayList();

        requestValidationDetailEntries.addAll(processGlobalErrors(bindingResult));
        requestValidationDetailEntries.addAll(processFieldErrors(bindingResult));

        return ProcessedBindingResult.builder()
                .requestValidationDetails(requestValidationDetailEntries)
                .build();
    }

    private List<RequestValidationDetail> processGlobalErrors(final BindingResult bindingResult) {
        final List<RequestValidationDetail> requestValidationDetailEntries = Lists.newArrayList();
        if (bindingResult.hasGlobalErrors()) {
            requestValidationDetailEntries.addAll(bindingResult.getGlobalErrors()
                    .parallelStream()
                    .map(objectError -> conversionService.convert(objectError, RequestValidationDetail.class))
                    .collect(Collectors.toList()
                    )
            );
        }
        return requestValidationDetailEntries;
    }

    private List<RequestValidationDetail> processFieldErrors(final BindingResult bindingResult) {
        final List<RequestValidationDetail> requestValidationDetailEntries = Lists.newArrayList();
        if (bindingResult.hasFieldErrors()) {
            requestValidationDetailEntries.addAll(bindingResult.getFieldErrors()
                    .parallelStream()
                    .map(fieldError -> conversionService.convert(fieldError, RequestValidationDetail.class))
                    .collect(Collectors.toList()
                    )
            );
        }
        return requestValidationDetailEntries;
    }
}
