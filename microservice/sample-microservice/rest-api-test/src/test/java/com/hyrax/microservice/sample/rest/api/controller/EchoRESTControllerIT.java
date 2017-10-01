package com.hyrax.microservice.sample.rest.api.controller;

import com.google.common.collect.ImmutableMap;
import com.hyrax.client.common.api.response.ErrorResponse;
import com.hyrax.client.sample.api.response.EchoResponse;
import com.hyrax.microservice.sample.rest.api.helper.JsonFileReader;
import com.hyrax.microservice.sample.rest.api.helper.JsonToObjectConverter;
import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.builder.ResponseSpecBuilder;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.specification.RequestSpecification;
import com.jayway.restassured.specification.ResponseSpecification;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class EchoRESTControllerIT extends AbstractRESTControllerIT {

    private static final String ECHO_REST_ENDPOINT_URL = "/echo/{echoId}";
    private static final String ECHO_ID_AS_PATH_VARIABLE_NAME = "echoId";

    private static final String FOLDER_NAME_ECHO = "echo";

    @Test
    public void retrieveEchoResponseShouldRespondWithHttpStatusOKWhenPathVariableEchoIdExists() throws IOException {
        // Given
        final String expectedJsonResponse = JsonFileReader.read(FOLDER_NAME_ECHO, "echo_response_with_http_status_ok.json");
        final EchoResponse expectedResponse = JsonToObjectConverter.convert(expectedJsonResponse, EchoResponse.class);
        final Object existingEchoId = 1L;
        final int httpStatusCodeOK = 200;

        // When
        final EchoResponse actualResponse = callRetrieveEchoRESTEndpoint(existingEchoId, httpStatusCodeOK, EchoResponse.class);

        // Then
        assertThat(actualResponse, equalTo(expectedResponse));
    }

    @Test
    public void retrieveEchoResponseShouldRespondWithHttpStatusBadRequestWhenPathVariableEchoIdIsString() throws IOException {
        // Given
        final String expectedErrorResponse = JsonFileReader.read(FOLDER_NAME_ECHO, "error_response_with_http_status_bad_request.json");
        final ErrorResponse expectedResponse = JsonToObjectConverter.convert(expectedErrorResponse, ErrorResponse.class);
        final Object echoIdAsString = "echoIdAsString";
        final int httpStatusCodeBadRequest = 400;

        // When
        final ErrorResponse actualResponse = callRetrieveEchoRESTEndpoint(echoIdAsString, httpStatusCodeBadRequest, ErrorResponse.class);

        // Then
        assertThat(actualResponse, equalTo(expectedResponse));
    }

    @Test
    public void retrieveEchoResponseShouldRespondWithHttpStatusNotFoundWhenPathVariableEchoIdDoesNotExist() throws IOException {
        // Given
        final String expectedErrorResponse = JsonFileReader.read(FOLDER_NAME_ECHO, "error_response_with_http_status_not_found.json");
        final ErrorResponse expectedResponse = JsonToObjectConverter.convert(expectedErrorResponse, ErrorResponse.class);
        final Object nonExistingEchoId = -1L;
        final int httpStatusCodeNotFound = 404;

        // When
        final ErrorResponse actualResponse = callRetrieveEchoRESTEndpoint(nonExistingEchoId, httpStatusCodeNotFound, ErrorResponse.class);

        // Then
        assertThat(actualResponse, equalTo(expectedResponse));
    }

    private <T> T callRetrieveEchoRESTEndpoint(final Object echoId, final int expectedHttpStatusCode, final Class<T> responseType) {
        final Map<String, Object> pathParameters = ImmutableMap.<String, Object>builder()
                .put(ECHO_ID_AS_PATH_VARIABLE_NAME, echoId)
                .build();

        return given().log().all().and().spec(requestSpecificationWith(pathParameters))
                .when().get(ECHO_REST_ENDPOINT_URL)
                .then().spec(responseSpecificationWith(expectedHttpStatusCode)).extract().as(responseType);
    }

    private RequestSpecification requestSpecificationWith(final Map<String, Object> pathParameters) {
        return new RequestSpecBuilder()
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .addPathParameters(pathParameters)
                .build();
    }

    private ResponseSpecification responseSpecificationWith(final int statusCode) {
        return new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectStatusCode(statusCode)
                .build();
    }
}
