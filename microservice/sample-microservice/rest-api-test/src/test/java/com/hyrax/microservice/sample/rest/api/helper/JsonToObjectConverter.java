package com.hyrax.microservice.sample.rest.api.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;

public class JsonToObjectConverter {

    private static final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    public static <T> T convert(final String json, final Class<T> targetType) throws IOException {
        return objectMapper.readValue(json, targetType);
    }
}
