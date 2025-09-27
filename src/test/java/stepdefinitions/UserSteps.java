package stepdefinitions;

import clients.ApiClient;
import config.ConfigManager;
import io.cucumber.java.en.*;
import io.restassured.response.Response;
import models.User;
import org.junit.jupiter.api.Assertions;
import utils.JsonReader;

/**
 * Step definitions for User API operations
 * Implements Cucumber steps for user-related test scenarios
 */
public class UserSteps {
    
    private Response response;
    private String endpoint;
    private User user;
    private final ApiClient apiClient = new ApiClient();
    
    @Given("I set the endpoint {string}")
    public void i_set_the_endpoint(String url) {
        this.endpoint = url;
    }
    
    @Given("I have user data from {string}")
    public void i_have_user_data_from(String fileName) {
        this.user = (User) JsonReader.readJson(fileName);
    }
    
    @When("I send a GET request")
    public void i_send_a_get_request() {
        response = apiClient.get(endpoint, ConfigManager.getHeaders());
    }
    
    @When("I send a POST request")
    public void i_send_a_post_request() {
        response = apiClient.post(endpoint, user, ConfigManager.getHeaders());
    }
    
    @When("I send a PUT request")
    public void i_send_a_put_request() {
        response = apiClient.put(endpoint, user, ConfigManager.getHeaders());
    }
    
    @When("I send a DELETE request")
    public void i_send_a_delete_request() {
        response = apiClient.delete(endpoint, ConfigManager.getHeaders());
    }
    
    @When("I send a PATCH request")
    public void i_send_a_patch_request() {
        response = apiClient.patch(endpoint, user, ConfigManager.getHeaders());
    }
    
    @Then("the response status code should be {int}")
    public void verify_status_code(int expectedStatusCode) {
        Assertions.assertEquals(expectedStatusCode, response.getStatusCode(),
                "Expected status code " + expectedStatusCode + " but got " + response.getStatusCode());
    }
    
    @Then("the response should contain {string}")
    public void verify_response_contains(String expectedContent) {
        String responseBody = response.asString();
        Assertions.assertTrue(responseBody.contains(expectedContent),
                "Response does not contain expected content: " + expectedContent);
    }
    
    @Then("the response should have user id {int}")
    public void verify_user_id(int expectedId) {
        User responseUser = response.as(User.class);
        Assertions.assertEquals(expectedId, responseUser.getId(),
                "Expected user ID " + expectedId + " but got " + responseUser.getId());
    }
    
    @Then("the response should have username {string}")
    public void verify_username(String expectedUsername) {
        User responseUser = response.as(User.class);
        Assertions.assertEquals(expectedUsername, responseUser.getUsername(),
                "Expected username " + expectedUsername + " but got " + responseUser.getUsername());
    }
    
    @Then("the response time should be less than {int} milliseconds")
    public void verify_response_time(int maxResponseTime) {
        long actualResponseTime = response.getTime();
        Assertions.assertTrue(actualResponseTime < maxResponseTime,
                "Response time " + actualResponseTime + "ms exceeded maximum of " + maxResponseTime + "ms");
    }
    
    @Then("the response should be valid JSON")
    public void verify_valid_json() {
        Assertions.assertDoesNotThrow(() -> {
            response.jsonPath();
        }, "Response is not valid JSON");
    }
}