Feature: User API Tests

  @smoke @getUser
  Scenario: Verify getting user details
    Given I set the endpoint "parabank/services_proxy/bank/customers/14099/accounts"
    When I send a GET request
    Then the response status code should be 200
    And the response should contain "VimalKY"
    And the response time should be less than 3000 milliseconds
    And the response should be valid JSON
