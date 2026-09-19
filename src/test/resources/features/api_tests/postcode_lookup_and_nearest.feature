@postcodes-api @stub
Feature: Find the nearest postcode using coordinates from a postcode lookup
  As an API consumer
  I want to retrieve the coordinates of a postcode
  So that I can find the nearest postcode to those coordinates

  Background:
    Given the API root endpoint is reachable
    Given the "<postcode>" to search

  @Smoke @Regression @positive @lookup @full-response
  Scenario Outline: Verify every field returned for a postcode lookup
    When I search postcode for "<postcode>"
    Then the response status should be "<code>"
    And the response should exactly match "<expected_response>"

    Examples:
      | postcode | code | expected_response                    |
      | SW1A 1AA | 200  | stubs/responses/lookup-success.json  |

  @Smoke @Regression @positive @lookup @nearest
  Scenario Outline: Search for a "<postcode>" and verify it is the nearest to its coordinates
    When I search postcode for "<postcode>"
    Then the response status should be "<code>"
    And the response should contain a valid result
    And I should extract the latitude and longitude from the response
    When I perform a nearest "<postcode>" search using the extracted coordinates
    Then the response status should be "<code>"
    And the response should contain "<postcode>" results
    And I should extract the nearest "<postcode>" from the results
    And I verify with the extracted coordinates latitude and longitude that the nearest "<postcode>" is indeed the closest to the original "<postcode>"
    Then the "<postcode>" should be the nearest "<postcode>" to its extracted coordinates
    And the distance to the nearest "<postcode>" should be minimal

    Examples:
      | postcode | code |
      | SW1A 1AA | 200  |
      | sw1a 1aa | 200  |


  @positive @lookup
  Scenario Outline: Search for a "<postcode>" and verify it is the nearest to its coordinates
    When I search postcode for "<postcode>"
    Then the response status should be "<code>"
    And the response should contain a valid result
    And I should extract the latitude and longitude from the response
    When I perform a nearest "<postcode>" search using the extracted coordinates
    Then the response status should be "<code>"
    And the response should contain "<postcode>" results
    And I should extract the nearest "<postcode>" from the results
    And I verify with the extracted coordinates "<latitude>" and "<longitude>" is the nearest to "<postcode>"
    Then the "<postcode>" should be the nearest "<postcode>" to its extracted coordinates
    And the distance to the nearest "<postcode>" should be minimal

    Examples:
      | postcode | code | latitude | longitude |
      | SW1A 1AA | 200  | 51.50101 | -0.141563 |
      | sw1a 1aa | 200  | 51.50101 | -0.141563 |

  @positive @lookup
  Scenario Outline: Retrieve signed coordinates for a valid "<postcode>"
    Given the "<postcode>" to search
    When I request the "<postcode>" details
    Then the "<postcode>" lookup should be successful
    And the returned "<postcode>" should be "<expected_postcode>"
    And the returned longitude should be negative
    And the returned latitude should be positive

    Examples:
      | postcode | expected_postcode |
      | SW1A 1AA | SW1A 1AA          |

  @positive @lookup
  Scenario Outline: Resolve supported postcode formats to the canonical postcode
    Given the "<postcode>" to search
    When I request the "<postcode>" details
    Then the "<postcode>" lookup should be successful
    And the returned postcode should be "<expected_postcode>"

    Examples:
      | postcode | expected_postcode |
      | SW1A1AA  | SW1A 1AA          |
      | sw1a 1aa | SW1A 1AA          |

  @negative @lookup
  Scenario Outline: Return no coordinates for an unknown "<postcode>"
    Given the "<postcode>" to search
    When I request the "<postcode>" details
    Then the "<postcode>" lookup should report that the "<postcode>" was not found
    And no longitude or latitude should be available

    Examples:
      | postcode |
      | ZZ99 9ZZ |
      | ZZ999ZZ |

  @Smoke @Regression @positive @chain @nearest
  Scenario Outline: Find a "<postcode>" as the nearest "<postcode>" to its own coordinates
    Given the "<postcode>" to search
    When I request the "<postcode>" details
    And I search for the nearest "<postcode>" using the returned coordinates
    Then the "<postcode>" lookup should be successful
    And the nearest-postcode search should be successful
    And the closest postcode should be "<expected_postcode>"
    And the nearest-postcode results should include "<expected_postcode>"

    Examples:
      | postcode | expected_postcode |
      | SW1A 1AA | SW1A 1AA          |

  @positive @chain
  Scenario Outline: Find a "<postcode>" as the nearest or closest "<postcode>" to its own coordinates
    Given the "<postcode>" to search
    When I request the "<postcode>" details
    And I search for the nearest postcodes using the returned coordinates
    Then the postcode lookup for "<postcode>" should be successful
    And the nearest-postcode search should be successful
    And the closest postcode should be "<expected_postcode>"
    And the nearest-postcode results should include "<expected_postcode>"

    Examples:
      | postcode | expected_postcode |
      | SW1A 1AA | SW1A 1AA          |

  @negative @chain
  Scenario Outline: Do not return the original postcode as nearest to distant coordinates
    Given the "<postcode>" to search
    When I request the "<postcode>" details
    And I move the returned latitude by <latitude_delta> degrees
    And I move the returned longitude by <longitude_delta> degrees
    And I search for the nearest "<postcode>" using the latest stored coordinates
    Then the nearest-postcode search should be successful
    And the closest "<postcode>" should not be "<unexpected_postcode>"

    Examples:
      | postcode | latitude_delta | longitude_delta | unexpected_postcode |
      | SW1A 1AA | 1.0            | 1.0             | SW1A 1AA            |

  @negative @validation
  Scenario Outline: Return no nearest postcodes for coordinates outside the supported geographic range
    When I search for the nearest postcodes using longitude <longitude> and latitude <latitude>
    Then the nearest-postcode search should return no results for the coordinates
    And no nearest-postcode results should be returned

    Examples:
      | longitude | latitude |
      | 200.0     | 51.5     |
      | -200.0    | 51.5     |
      | -0.1416   | 95.0     |
      | -0.1416   | -95.0    |
