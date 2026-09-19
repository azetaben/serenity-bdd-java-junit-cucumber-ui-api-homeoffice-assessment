@postcodes-api @stub @endpoints
Feature: Additional Postcodes.io endpoint validation
  As an API test engineer
  I want to validate bulk lookup, coordinate reverse geocoding, and outward-code nearest endpoints
  So that the framework covers the documented Postcodes.io endpoint groups

  Background:
    Given the API root endpoint is reachable

  @bulk-lookup @postcodes
  Scenario: Bulk postcode lookup returns one result per requested postcode
    Given the bulk postcode lookup request contains
      | postcode |
      | OX49 5NU |
      | M32 0JG  |
      | NE30 1DP |
    When I request the bulk postcode lookup
    Then the response status should be "200"
    And the response content type should be JSON
    And the response should contain these JSON path values
      | json_path                 | type    | value    |
      | status                    | integer | 200      |
      | result.size()             | integer | 3        |
      | result[0].query           | string  | OX49 5NU |
      | result[0].result.postcode | string  | OX49 5NU |
      | result[0].result.outcode  | string  | OX49     |
      | result[1].query           | string  | M32 0JG  |
      | result[1].result.postcode | string  | M32 0JG  |
      | result[1].result.outcode  | string  | M32      |
      | result[2].query           | string  | NE30 1DP |
      | result[2].result.postcode | string  | NE30 1DP |
      | result[2].result.outcode  | string  | NE30     |

  @Smoke @Regression @nearest @reverse-geocoding
  Scenario: Lookup nearest postcodes around a longitude and latitude point
    When I search for the nearest postcodes using longitude -0.141563 and latitude 51.50101
    Then the nearest-postcode search should be successful
    And the response content type should be JSON
    And the response should contain these JSON path values
      | json_path           | type    | value     |
      | status              | integer | 200       |
      | result.size()       | integer | 1         |
      | result[0].postcode  | string  | SW1A 1AA  |
      | result[0].longitude | number  | -0.141563 |
      | result[0].latitude  | number  | 51.50101  |
      | result[0].distance  | integer | 0         |
    And the closest postcode should be "SW1A 1AA"
    And the distance to the nearest "SW1A 1AA" should be minimal

  @outcodes @nearest
  Scenario: Lookup nearest outward codes around an outward code
    Given the outward code is "SW1A"
    When I search for the nearest outward codes
    Then the response status should be "200"
    And the response content type should be JSON
    And the response should contain these JSON path values
      | json_path           | type    | value     |
      | status              | integer | 200       |
      | result.size()       | integer | 3         |
      | result[0].outcode   | string  | SW1A      |
      | result[0].longitude | number  | -0.141563 |
      | result[0].latitude  | number  | 51.50101  |
      | result[0].distance  | integer | 0         |
      | result[1].outcode   | string  | SW1P      |
      | result[2].outcode   | string  | W1J       |
    And the response should exactly match "stubs/responses/outcode-nearest-success.json"
