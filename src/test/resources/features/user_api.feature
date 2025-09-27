Feature: ParaBank Account API Tests

  @smoke @getUser
  Scenario: Verify getting account details
    Given I set the endpoint "services_proxy/bank/customers/14099/accounts"
    When I send a GET request
    Then the response status code should be 200
    And the response should contain "CHECKING"
    And the response time should be less than 3000 milliseconds