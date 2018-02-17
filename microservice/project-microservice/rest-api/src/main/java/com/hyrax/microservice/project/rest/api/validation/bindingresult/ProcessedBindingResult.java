package com.hyrax.microservice.project.rest.api.validation.bindingresult;

import com.hyrax.microservice.project.rest.api.domain.response.RequestValidationDetail;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
public class ProcessedBindingResult implements Serializable {

    private static final long serialVersionUID = 4594138288778529865L;

    private final List<RequestValidationDetail> requestValidationDetails;

    public boolean hasValidationErrors() {
        return !requestValidationDetails.isEmpty();
    }
}
