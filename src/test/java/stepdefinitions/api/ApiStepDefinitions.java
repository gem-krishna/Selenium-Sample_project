package stepdefinitions.api;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;

public class ApiStepDefinitions {

    private RequestSpecification request;
    private Response response;
    private Map<String, Object> requestParams;

    public ApiStepDefinitions() {
        RestAssured.baseURI = "";
        requestParams = new HashMap<>();
    }

    @Given("I set api params as")
    public void iSetApiParamsAs(io.cucumber.datatable.DataTable dataTable) {
        // Convert DataTable to Map
        Map<String, String> params = dataTable.asMap(String.class, String.class);
        requestParams.clear();
        requestParams.putAll(params);
    }

    @And("I make api call to {string} with method {string}")
    public void iMakeApiCallToWithMethod(String url, String method) {
        request = given()
                .contentType("application/json")
                .accept("application/json")
                .log().all();

        // Add request body if params exist
        if (!requestParams.isEmpty()) {
            request.body(requestParams);
        }

        // Make the API call based on the method
        switch (method.toUpperCase()) {
            case "GET":
                response = request.when().get(url);
                break;
            case "POST":
                response = request.when().post(url);
                break;
            case "PUT":
                response = request.when().put(url);
                break;
            case "DELETE":
                response = request.when().delete(url);
                break;
            case "PATCH":
                response = request.when().patch(url);
                break;
            default:
                throw new IllegalArgumentException("Unsupported HTTP method: " + method);
        }

        // Log response
        response.then().log().all();

        // Clear params after use
        requestParams.clear();
    }

    @And("I validate the response status code is {int}")
    public void iValidateTheResponseStatusCodeIs(int expectedStatusCode) {
        int actualStatusCode = response.getStatusCode();
        assertEquals("Status code should match", expectedStatusCode, actualStatusCode);
    }

    @And("I validate the response body has {string} as {string}")
    public void iValidateTheResponseBodyHasAs(String jsonPath, String expectedValue) {
        response.then().body(jsonPath, equalTo(expectedValue));

        // Alternative way to validate
        String actualValue = response.jsonPath().getString(jsonPath);
        assertEquals("Response body value should match for path: " + jsonPath,
                expectedValue, actualValue);
    }
}
