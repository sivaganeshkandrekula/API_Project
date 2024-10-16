package stepDefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.equalTo;

import static org.hamcrest.Matchers.lessThan;

public class ApiSteps {
    private Response response;
    public String storedId;

    @When("I send a POST request to {string} with payload")
    public void sendPostRequest(String url, String payload) {
        response = RestAssured.given()
                .header("Content-Type", "application/json")
                .body(payload)
                .when()
                .post(url);
    }

    @Then("the response status code should be {int}")
    public void validateStatusCode(int expectedStatusCode) {
        response.then().statusCode(expectedStatusCode);
    }

    @And("I store the ID from the response and passed into get request to {string}")
    public void i_store_the_id_from_the_response_passed_into_get_request(String url) {
        storedId = response.jsonPath().getString("id");

        response = RestAssured.given()
               // .pathParam("{id}",storedId )
                .get(url.replace("{id}", storedId));
    }

    @Then("the response should contain the following data")
    public void validateResponseData(DataTable dataTable) {
        List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
        String expectedName = data.get(0).get("expectedName");
        int expectedYear = Integer.parseInt(data.get(0).get("expectedYear"));

        response.then()
                .body("name", equalTo(expectedName))
                .body("data.year", equalTo(expectedYear));
    }



    @And("I send a PUT request to {string} with payload")
    public void sendPutRequest(String url, String payload) {


        response = RestAssured.given()
                .header("Content-Type", "application/json")
               // .pathParam("id", storedId)
                .body(payload)
                .put(url.replace("{id}", storedId));
    }


    @And("I send a DELETE request to {string} with the stored ID")
    public void sendDeleteRequest(String url) {


        response = RestAssured.given()
               // .pathParam("id", storedId)
                .delete(url.replace("{id}", storedId));
    }

    @When("I send a GET request to {string}")
    public void i_send_a_get_request_to(String url) {
        response = RestAssured.given()
                .get(url);
    }

    @Then("the response status codes should be {int}")
    public void responsestatuscodes(int expectedStatusCode) {
        response.then().statusCode(expectedStatusCode);
    }


    @When("I send a post request to {string} with payload")
    public void i_send_a_post_request_to_with_payload(String url, String payload) {
        response = RestAssured.given()
                .header("Content-Type", "application/json")
                .body(payload)
                .when()
                .post(url);

    }


    @Then("the response time should be less than {int} ms")
    public void validateResponseTime(int timeInMs) {
        response.then().time(lessThan((long) timeInMs));
    }

    @When("I send a POST request to {string} with missing name")
    public void sendPostRequestWithMissingName(String url) {
        String payload = "{ \"data\": { \"year\": 2019, \"price\": 1849.99, \"CPU model\": \"Intel Core i9\", \"Hard disk size\": \"1 TB\" }}";
        sendPostRequest(url, payload);
    }
    @Then("the response status code should be {int} for missing name")
    public void validateStatusCodeForMissingName(int expectedStatusCode) {
        validateStatusCode(expectedStatusCode);
    }

    @When("I send a POST request to {string} with invalid year")
    public void sendPostRequestWithInvalidYear(String url) {
        String payload = "{ \"name\": \"Apple MacBook Pro 16\", \"data\": { \"year\": \"invalid_year\", \"price\": 1849.99, \"CPU model\": \"Intel Core i9\", \"Hard disk size\": \"1 TB\" }}";
        sendPostRequest(url, payload);
    }



    @Then("the response status code should be {int} for invalid year")
    public void validateStatusCodeForInvalidYear(int expectedStatusCode) {
        validateStatusCode(expectedStatusCode);
    }
}

