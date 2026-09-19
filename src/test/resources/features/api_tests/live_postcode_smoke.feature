@postcodes-api @live
Feature: Live Postcodes.io smoke checks
  As an API framework maintainer
  I want a small live smoke suite
  So that deterministic regression tests are not coupled to public API availability

  @positive @lookup
  Scenario Outline: Live lookup for a valid "<postcode>" returns coordinates
    Given the "<postcode>" to search
    When I request the "<postcode>" details
    Then the "<postcode>" lookup should be successful
    And the returned "<postcode>" should be "<expected_postcode>"
    And the returned longitude should be negative
    And the returned latitude should be positive

    Examples:
      | postcode | expected_postcode |
      | SW1A 1AA | SW1A 1AA          |

  @positive @lookup @full-response @schema
  Scenario Outline: Live lookup for a valid "<postcode>" satisfies the full public contract
    Given the "<postcode>" to search
    When I request the "<postcode>" details
    Then the response status should be "<code>"
    And the response should contain a valid result
    And the response should match the JSON schema "schemas/postcode-lookup-response-schema.json"
    And the response should contain these JSON path values
      | json_path        | type    | value               |
      | status           | integer | <code>              |
      | result.postcode  | string  | <expected_postcode> |
      | result.incode    | string  | <incode>            |
      | result.outcode   | string  | <outcode>           |
      | result.country   | string  | <country>           |
      | result.longitude | number  | <longitude>         |
      | result.latitude  | number  | <latitude>          |

    Examples:
      | postcode | code | expected_postcode | incode | outcode | country | longitude | latitude |
      | SW1A 1AA | 200  | SW1A 1AA          | 1AA    | SW1A    | England | -0.141563 | 51.50101 |

  @positive @chain
  Scenario Outline: Live nearest-postcode search proves "<postcode>" is nearest to its extracted coordinates
    Given the "<postcode>" to search
    When I request the "<postcode>" details
    Then the "<postcode>" lookup should be successful
    And the returned "<postcode>" should be "<expected_postcode>"
    And the response should contain these JSON path values
      | json_path        | type    | value               |
      | status           | integer | 200                 |
      | result.postcode  | string  | <expected_postcode> |
      | result.longitude | number  | <longitude>         |
      | result.latitude  | number  | <latitude>          |
    And I store the returned longitude and latitude
    And the extracted coordinates should be close to longitude <longitude> and latitude <latitude>
    When I search for the nearest "<postcode>" using the stored coordinates
    Then the nearest-postcode search should be successful
    And the response should match the JSON schema "schemas/nearest-postcodes-response-schema.json"
    And the response should contain these JSON path values
      | json_path           | type    | value               |
      | status              | integer | 200                 |
      | result[0].postcode  | string  | <expected_postcode> |
      | result[0].longitude | number  | <longitude>         |
      | result[0].latitude  | number  | <latitude>          |
      | result[0].distance  | number  | 0                   |
    And the closest postcode should be "<expected_postcode>"
    And the nearest-postcode results should include "<expected_postcode>"
    And the first nearest postcode coordinates should match the stored coordinates
    And the distance to the nearest "<expected_postcode>" should be minimal

    Examples:
      | postcode | expected_postcode | longitude | latitude |
      | SW1A 1AA | SW1A 1AA          | -0.141563 | 51.50101 |
