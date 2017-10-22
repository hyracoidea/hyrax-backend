package com.hyrax.microservice.sample.rest.api.controller;

import com.hyrax.client.common.api.response.ErrorResponse;
import com.hyrax.client.sample.api.response.EchoResponse;
import com.hyrax.microservice.sample.service.api.EchoService;
import com.hyrax.microservice.sample.service.domain.Echo;
import com.hyrax.microservice.sample.service.exception.EchoNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "/echo", description = "Operations for echo")
@RestController
public class EchoRESTController extends AbstractRESTController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EchoRESTController.class);

    private final EchoService echoService;

    private final ConversionService conversionService;

    @Autowired
    public EchoRESTController(final EchoService echoService, final ConversionService conversionService) {
        super(LOGGER);
        this.echoService = echoService;
        this.conversionService = conversionService;
    }

    @ApiOperation(value = "Finds echo by id")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @GetMapping(path = "/echo/{echoId}")
    public ResponseEntity<EchoResponse> retrieveEchoResponse(@ApiParam(value = "the looked for id of the echo", required = true, defaultValue = "1")
                                                             @PathVariable(name = "echoId") final Long echoId) {
        LOGGER.info("Received echoId is: {}", echoId);

        final Echo echo = echoService.getByEchoId(echoId).orElseThrow(() -> new EchoNotFoundException("Echo not found with id: " + String.valueOf(echoId)));
        return ResponseEntity.ok(conversionService.convert(echo, EchoResponse.class));
    }

    @ExceptionHandler(EchoNotFoundException.class)
    protected ResponseEntity<ErrorResponse> handleNotFound(final Exception e) {
        logException(e);
        return createErrorResponse(HttpStatus.NOT_FOUND, e);
    }
}
