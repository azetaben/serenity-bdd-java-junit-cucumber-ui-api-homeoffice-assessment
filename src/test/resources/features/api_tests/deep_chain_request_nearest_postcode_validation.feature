@postcodes-api @stub @deep-chain
Feature: Deep chain request validation for nearest postcode
  As an API test engineer
  I want to chain "<postcode>" lookup coordinates into a nearest-postcode request
  So that I can prove SW1A 1AA is the nearest postcode to its returned location

  Background:
    Given the API root endpoint is reachable

  @positive @chain @lookup @nearest
  Scenario Outline: Chain lookup coordinates into nearest-postcode search and verify "<postcode>" is nearest
    Given the "<postcode>" to search
    When I request the "<postcode>" details
    Then the "<postcode>" lookup should be successful
    And the response should contain a valid result
    And the response should contain these JSON path values
      | json_path        | type    | value     |
      | status           | integer | 200       |
      | result.postcode  | string  | <postcode> |
      | result.longitude | number  | -0.141563 |
      | result.latitude  | number  | 51.50101  |
    And I store the returned longitude and latitude
    And the extracted coordinates should be close to longitude -0.141563 and latitude 51.50101
    When I search for the nearest "<postcode>" using the stored coordinates
    Then the nearest-postcode search should be successful
    And the response should contain these JSON path values
      | json_path           | type    | value     |
      | status              | integer | 200       |
      | result[0].postcode  | string  | <postcode>  |
      | result[0].longitude | number  | -0.141563 |
      | result[0].latitude  | number  | 51.50101  |
      | result[0].distance  | integer | 0         |
    And the closest postcode should be "<postcode>"
    And the nearest-postcode results should include "<postcode>"
    And the first nearest postcode coordinates should match the stored coordinates
    And the distance to the nearest "<postcode>" should be minimal
    And the response should exactly match "<postcode>"
    Examples:
      | postcode |
      | SW1A 1AA |

  @positive @chain @canonicalization @nearest
  Scenario Outline: Chain supported "<postcode>" request formats and verify the canonical postcode is nearest
    Given the "<postcode>" to search
    When I request the "<postcode>" details
    Then the "<postcode>" lookup should be successful
    And the returned postcode should be "<postcode>"
    And I store the returned longitude and latitude
    When I search for the nearest "<postcode>" using the stored coordinates
    Then the nearest-postcode search should be successful
    And the closest postcode should be "<postcode>"
    And the nearest-postcode results should include "<postcode>"
    And the distance to the nearest "<postcode>" should be minimal

    Examples:
      | postcode |
      | SW1A 1AA |
      | SW1A1AA  |
      | sw1a 1aa |

  @negative @chain @nearest
  Scenario Outline: Moved coordinates should not return "<postcode>" as the nearest postcode
    Given the "<postcode>" to search
    When I request the "<postcode>" details
    Then the "<postcode>" lookup should be successful
    And I store the returned longitude and latitude
    When I search for the nearest "<postcode>" using the adjusted coordinates
      | latitude_adjustment | longitude_adjustment |
      | 1.0                 | 1.0                  |
    Then the nearest-postcode search should be successful
    And the closest postcode should not be "<postcode>"
    Examples:
      | postcode |
      | SW1A 1AA |
