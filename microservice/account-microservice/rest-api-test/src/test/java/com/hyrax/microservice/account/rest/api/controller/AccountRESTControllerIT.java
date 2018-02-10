package com.hyrax.microservice.account.rest.api.controller;

import com.hyrax.microservice.reader.JsonFileReader;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.apache.http.HttpStatus;
import org.jooq.Record;
import org.jooq.Result;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(DataProviderRunner.class)
public class AccountRESTControllerIT extends AbstractRESTControllerIT {

    private static final String REST_ENDPOINT_PATH_ACCOUNT = "/account";
    private static final String REQUEST_FOLDER = "account/create/request";
    private static final String RESPONSE_FOLDER = "account/create/response";

    @After
    public void cleanDatabase() {
        dslContext.execute("DELETE FROM account WHERE username = 'hyrax'");
        dslContext.execute("DELETE FROM account WHERE email = 'hyrax2018@email.com'");
    }

    @Test
    @UseDataProvider("data")
    public void createAccountWithDataProvider(final String testCaseFileName, final int expectedHttpStatusCode) {
        // Given
        final String request = JsonFileReader.read(REQUEST_FOLDER, testCaseFileName);
        final String expectedResponse = JsonFileReader.read(RESPONSE_FOLDER, testCaseFileName);

        // When
        final Response response = createRequestSpecificationWith(request).when()
                .post(REST_ENDPOINT_PATH_ACCOUNT);

        // Then
        response.then()
                .contentType(ContentType.JSON)
                .statusCode(expectedHttpStatusCode);
        assertThat(response.getBody().prettyPrint(), equalTo(expectedResponse));
        verifyDatabase(false);
    }

    @Test
    public void createAccountShouldRespondNoContent() {
        // Given
        final String testCaseFileName = "no_content_with_non_existing_account.json";
        final String request = JsonFileReader.read(REQUEST_FOLDER, testCaseFileName);

        // When
        final Response response = createRequestSpecificationWith(request).when()
                .post(REST_ENDPOINT_PATH_ACCOUNT);

        // Then
        response.then()
                .statusCode(HttpStatus.SC_NO_CONTENT);
        assertThat(response.getBody().prettyPrint().isEmpty(), is(true));
        verifyDatabase(true);
    }

    @DataProvider
    public static Object[][] data() {
        return new Object[][]{
                {"request_validation_failed_with_missing_first_name.json", HttpStatus.SC_UNPROCESSABLE_ENTITY},
                {"request_validation_failed_with_too_long_first_name.json", HttpStatus.SC_UNPROCESSABLE_ENTITY},

                {"request_validation_failed_with_missing_last_name.json", HttpStatus.SC_UNPROCESSABLE_ENTITY},
                {"request_validation_failed_with_too_long_last_name.json", HttpStatus.SC_UNPROCESSABLE_ENTITY},

                {"request_validation_failed_with_missing_username.json", HttpStatus.SC_UNPROCESSABLE_ENTITY},
                {"request_validation_failed_with_too_long_username.json", HttpStatus.SC_UNPROCESSABLE_ENTITY},

                {"request_validation_failed_with_missing_email.json", HttpStatus.SC_UNPROCESSABLE_ENTITY},
                {"request_validation_failed_with_invalid_email.json", HttpStatus.SC_UNPROCESSABLE_ENTITY},

                {"request_validation_failed_with_missing_password.json", HttpStatus.SC_UNPROCESSABLE_ENTITY},
                {"request_validation_failed_with_invalid_password.json", HttpStatus.SC_UNPROCESSABLE_ENTITY},

                {"request_validation_failed_with_missing_passwordConfirmation.json", HttpStatus.SC_UNPROCESSABLE_ENTITY},
                {"request_validation_failed_with_invalid_passwordConfirmation.json", HttpStatus.SC_UNPROCESSABLE_ENTITY},

                {"request_validation_failed_with_different_invalid_passwords.json", HttpStatus.SC_UNPROCESSABLE_ENTITY},

                {"conflict_with_existing_username.json", HttpStatus.SC_CONFLICT},
                {"conflict_with_existing_email.json", HttpStatus.SC_CONFLICT}
        };
    }

    private RequestSpecification createRequestSpecificationWith(final String request) {
        return given()
                .log().method().and().log().path().and().log().body()
                .request()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(request);
    }

    private void verifyDatabase(boolean existsRecord) {
        Result<Record> result = dslContext.select()
                .from("account")
                .where("email = 'hyrax2018@email.com' or username = 'hyrax'")
                .fetch();

        if (existsRecord) {
            assertThat(result.isNotEmpty(), is(true));
            assertThat(result.size(), is(1));
            assertThat(result.get(0), notNullValue());
            assertThat(result.get(0).getValue("first_name"), equalTo("HyraxFirstName"));
            assertThat(result.get(0).getValue("last_name"), equalTo("HyraxLastName"));
            assertThat(result.get(0).getValue("username"), equalTo("hyrax"));
            assertThat(result.get(0).getValue("email"), equalTo("hyrax2018@email.com"));
            assertThat(result.get(0).getValue("password_hash").toString().length(), is(60));
            assertThat(result.get(0).getValue("authority"), equalTo("USER"));
        } else {
            assertThat(result.isEmpty(), is(true));
        }


    }
}
