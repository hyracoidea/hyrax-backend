package com.hyrax.microservice.account.rest.api.controller;

import com.jayway.restassured.RestAssured;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class AbstractRESTControllerIT {

    private static final String DB_CONNECTION_URL = "jdbc:mysql://localhost:3307/hyrax?useSSL=false";
    private static final String DB_CONNECTION_USER = "hyrax_user";
    private static final String DB_CONNECTION_PWD = "hyrax_password";

    private static final String REST_ASSURED_BASE_URI = "http://localhost";
    private static final int REST_ASSURED_PORT = 8080;
    private static final String REST_ASSURED_BASE_PATH = "/hyrax/api";

    protected static Connection connection;
    protected static DSLContext dslContext;

    @BeforeClass
    public static void setup() throws SQLException {

        setupRestAssuredBaseProperties();
        setupDatabaseConnection();
    }

    @AfterClass
    public static void tearDown() throws SQLException {
        closeDatabaseConnection();
    }

    private static void setupRestAssuredBaseProperties() {
        RestAssured.baseURI = REST_ASSURED_BASE_URI;
        RestAssured.port = REST_ASSURED_PORT;
        RestAssured.basePath = REST_ASSURED_BASE_PATH;
    }

    private static void setupDatabaseConnection() throws SQLException {
        connection = DriverManager.getConnection(DB_CONNECTION_URL, DB_CONNECTION_USER, DB_CONNECTION_PWD);
        dslContext = DSL.using(connection, SQLDialect.MYSQL);
    }

    private static void closeDatabaseConnection() throws SQLException {
        dslContext.close();
        connection.close();
    }
}