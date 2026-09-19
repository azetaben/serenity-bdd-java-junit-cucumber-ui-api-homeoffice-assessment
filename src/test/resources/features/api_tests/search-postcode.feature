@postcodes-api @stub
Feature: Search postcode SW1A 1AA

  Background:
    Given the API root endpoint is reachable

  @positive @lookup @full-response
  Scenario: Verify every field returned for SW1A 1AA
    When I search postcode for "SW1A 1AA"
    Then the response status should be "200"
    And the response should contain a valid result
    And the response should exactly match "stubs/responses/lookup-success.json"

  @positive @lookup @schema
  Scenario: Verify SW1A 1AA lookup response matches the expected JSON schema
    When I search postcode for "SW1A 1AA"
    Then the response status should be "200"
    And the response should match the JSON schema "schemas/postcode-lookup-response-schema.json"

  @positive @lookup
  Scenario Outline: Verify "<postcode>" returns signed coordinates
    Given the postcode "<postcode>" to search
    When I request the "<postcode>" details
    Then the "<postcode>" lookup should be successful
    And the returned postcode should be "<postcode>"
    And the returned longitude should be negative
    And the returned latitude should be positive
    Examples:
      | postcode |
      |SW1A 1AA  |

  @positive @lookup
  Scenario Outline: Verify supported "<postcode>" input formats resolve to canonical postcode
    Given the "<postcode>" to search
    When I request the "<postcode>" details
    Then the "<postcode>" lookup should be successful
    And the returned postcode should be "<postcode>"

    Examples:
      | postcode |
      | SW1A 1AA |
      | SW1A1AA  |
      | sw1a 1aa |

  @positive @chain
  Scenario Outline: Verify "<postcode>" is nearest to its own returned coordinates
    Given the "<postcode>" to search
    When I request the "<postcode>" details
    Then the postcode lookup for "<postcode>" should be successful
    And I store the returned longitude and latitude
    When I search for the nearest "<postcode>" using the stored coordinates
    Then the nearest-postcode search should be successful
    And the closest postcode should be "<postcode>"
    And the nearest-postcode results should include "<postcode>"
    And the distance to the nearest "<postcode>" should be minimal
    Examples:
      | postcode |
      | SW1A 1AA |

  @negative @chain
  Scenario Outline: Verify SW1A 1AA is not nearest after moving away from its coordinates
    Given the "<postcode>" to search
    When I request the "<postcode>" details
    And I search for the nearest postcodes using the adjusted coordinates
      | latitude_adjustment | longitude_adjustment |
      | 1.0                 | 1.0                  |
    Then the nearest-postcode search should be successful
    And the closest postcode should not be "<postcode>"

    Examples:
      | postcode |
      | SW1A 1AA |
