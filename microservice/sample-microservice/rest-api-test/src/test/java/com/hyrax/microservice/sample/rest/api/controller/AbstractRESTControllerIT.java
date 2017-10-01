package com.hyrax.microservice.sample.rest.api.controller;

import com.jayway.restassured.RestAssured;
import org.junit.BeforeClass;

public abstract class AbstractRESTControllerIT {

    @BeforeClass
    public static void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8090;
        RestAssured.basePath = "/sample";
    }
}