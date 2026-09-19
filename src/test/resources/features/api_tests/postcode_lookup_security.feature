@postcodes-api @stub @security
Feature: Security validation for postcode lookup
  As an API test engineer
  I want postcode lookup requests to handle hostile input safely
  So that malformed or malicious values do not leak implementation details or return unintended data

  Background:
    Given the API root endpoint is reachable

  @positive @control
  Scenario: Valid postcode lookup still succeeds after defensive route matching
    When I search postcode for "SW1A 1AA"
    Then the postcode lookup should be successful
    And the returned postcode should be "SW1A 1AA"
    And the response content type should be JSON

  @negative @input-validation
  Scenario Outline: Hostile postcode lookup payloads fail safely
    When I search postcode for "<payload>"
    Then the postcode lookup should report that the postcode was not found
    And the response content type should be JSON
    And the response should not echo the submitted postcode
    And the response body should not expose sensitive implementation details
    And no longitude or latitude should be available

    Examples:
      | payload                         |
      | ' OR '1'='1                     |
      | SW1A 1AA' OR '1'='1             |
      | %2e%2e%2f%2e%2e%2fetc%2fpasswd |
      | <script>alert(1)</script>       |
      | %0d%0aX-Injected-Header:true    |
      | SW1A 1AA?nearest=true           |
      | SW1A 1AA#fragment               |

  @negative @input-validation
  Scenario: Oversized postcode lookup payload fails safely
    Given the postcode "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" to search
    When I request the postcode details
    Then the postcode lookup should report that the postcode was not found
    And the response content type should be JSON
    And the response should not echo the submitted postcode
    And the response body should not expose sensitive implementation details
    And no longitude or latitude should be available
