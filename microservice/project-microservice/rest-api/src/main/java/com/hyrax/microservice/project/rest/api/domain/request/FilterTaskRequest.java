package com.hyrax.microservice.project.rest.api.domain.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FilterTaskRequest {

    private String assignedUsername;

    private List<String> labelNames;
}
