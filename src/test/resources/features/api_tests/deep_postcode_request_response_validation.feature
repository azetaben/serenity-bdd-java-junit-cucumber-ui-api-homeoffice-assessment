@postcodes-api @stub @deep-validation
Feature: Deep request and response validation for postcode lookup
  As an API test engineer
  I want to validate postcode lookup request handling and response payloads deeply
  So that regressions in contract, routing, canonicalization, and data values are caught

  Background:
    Given the API root endpoint is reachable

  @positive @request @lookup
  Scenario Outline: Lookup request accepts supported SW1A 1AA path formats
    When I search postcode for "SW1A 1AA"
    Then the response status should be "200"
    And the returned postcode should be "<postcode>"

    Examples:
      | postcode |
      | SW1A 1AA |
      | SW1A1AA  |
      | sw1a 1aa |

  @positive @response @schema
  Scenario: Lookup response satisfies the expected JSON schema
    When I search postcode for "SW1A 1AA"
    Then the response status should be "200"
    And the response time should be between 50 to 90 milliseconds
    And the response should match the JSON schema "schemas/postcode-lookup-response-schema.json"

  @positive @response @contract
  Scenario: Lookup response matches the full expected response contract
    When I search postcode for "SW1A 1AA"
    Then the response status should be "200"
    And the response should contain a valid result
    And the response should exactly match "stubs/responses/lookup-success.json"

  @positive @response @performance
  Scenario: Lookup response is returned within the accepted response time
    When I search postcode for "SW1A 1AA"
    Then the response status should be "200"
    And the response time should be less than 1000 milliseconds

  @positive @response @performance
  Scenario: Lookup response is returned within the accepted response time
    When I search postcode for "SW1A 1AA"
    Then the response status should be "200"
    And the response time should be less than 90 milliseconds
    And the response time should be between 30 to 90 milliseconds

  @positive @response @fields
  Scenario: Lookup response contains expected top-level and result field values
    When I search postcode for "SW1A 1AA"
    Then the response status should be "200"
    And the response should contain these JSON path values
      | json_path                              | type    | value                                |
      | status                                 | integer | 200                                  |
      | result.postcode                        | string  | SW1A 1AA                             |
      | result.quality                         | integer | 1                                    |
      | result.eastings                        | integer | 529090                               |
      | result.northings                       | integer | 179645                               |
      | result.country                         | string  | England                              |
      | result.nhs_ha                          | string  | London                               |
      | result.longitude                       | number  | -0.141563                            |
      | result.latitude                        | number  | 51.50101                             |
      | result.european_electoral_region       | string  | London                               |
      | result.primary_care_trust              | string  | Westminster                          |
      | result.region                          | string  | London                               |
      | result.lsoa                            | string  | Westminster 018C                     |
      | result.msoa                            | string  | Westminster 018                      |
      | result.incode                          | string  | 1AA                                  |
      | result.outcode                         | string  | SW1A                                 |
      | result.parliamentary_constituency      | string  | Cities of London and Westminster     |
      | result.parliamentary_constituency_2024 | string  | Cities of London and Westminster     |
      | result.admin_district                  | string  | Westminster                          |
      | result.parish                          | string  | Westminster, unparished area         |
      | result.date_of_introduction            | string  | 198001                               |
      | result.index_of_multiple_deprivation   | integer | 24862                                |
      | result.admin_ward                      | string  | St James's                           |
      | result.ccg                             | string  | NHS West and North London            |
      | result.nuts                            | string  | Westminster                          |
      | result.pfa                             | string  | Metropolitan Police                  |
      | result.nhs_region                      | string  | London                               |
      | result.ttwa                            | string  | London                               |
      | result.national_park                   | string  | England (non-National Park)          |
      | result.bua                             | string  | City of Westminster                  |
      | result.icb                             | string  | NHS West and North London Integrated Care Board |
      | result.cancer_alliance                 | string  | West London                          |
      | result.lsoa11                          | string  | Westminster 018C                     |
      | result.msoa11                          | string  | Westminster 018                      |
      | result.lsoa21                          | string  | Westminster 018C                     |
      | result.msoa21                          | string  | Westminster 018                      |
      | result.oa21                            | string  | E00023938                            |
      | result.ruc11                           | string  | (England/Wales) Urban major conurbation |
      | result.ruc21                           | string  | Urban: Nearer to a major town or city |
      | result.lep1                            | string  | London                               |

  @Smoke @Regression @positive @response @coordinates @location
  Scenario: Extract longitude and latitude and validate closeness to real location coordinates
    Given the "SW1A 1AA" to search
    When I request the postcode details
    Then the postcode lookup should be successful
    And I store the returned longitude and latitude
    And the extracted coordinates should be close to longitude -0.141563 and latitude 51.50101
    And the extracted coordinates should be within 0.10 kilometres of longitude -0.1419 and latitude 51.5014
    And the returned longitude should be negative
    And the returned latitude should be positive

  @positive @response @nulls
  Scenario: Lookup response preserves expected null fields
    When I search postcode for "SW1A 1AA"
    Then the response status should be "200"
    And the response should contain these JSON path values
      | json_path                     | type |
      | result.senedd_constituency    | null |
      | result.senedd_constituency_no | null |
      | result.admin_county           | null |
      | result.date_of_termination    | null |
      | result.ced                    | null |
      | result.lep2                   | null |
      | result.codes.lep2             | null |

  @positive @response @codes
  Scenario: Lookup response contains expected administrative code values
    When I search postcode for "SW1A 1AA"
    Then the response status should be "200"
    And the response should contain these JSON path values
      | json_path                                      | type   | value     |
      | result.codes.admin_district                    | string | E09000033 |
      | result.codes.admin_county                      | string | E99999999 |
      | result.codes.admin_ward                        | string | E05013806 |
      | result.codes.parish                            | string | E43000236 |
      | result.codes.parliamentary_constituency        | string | E14001172 |
      | result.codes.parliamentary_constituency_2024   | string | E14001172 |
      | result.codes.ccg                               | string | E38000256 |
      | result.codes.ccg_id                            | string | W2U3Z     |
      | result.codes.ced                               | string | E99999999 |
      | result.codes.nuts                              | string | TLI35     |
      | result.codes.lsoa                              | string | E01004736 |
      | result.codes.msoa                              | string | E02000977 |
      | result.codes.lau2                              | string | E09000033 |
      | result.codes.pfa                               | string | E23000001 |
      | result.codes.nhs_region                        | string | E40000003 |
      | result.codes.ttwa                              | string | E30000234 |
      | result.codes.national_park                     | string | E65000001 |
      | result.codes.bua                               | string | E63012036 |
      | result.codes.icb                               | string | E54000071 |
      | result.codes.cancer_alliance                   | string | E56000021 |
      | result.codes.lsoa11                            | string | E01004736 |
      | result.codes.msoa11                            | string | E02000977 |
      | result.codes.lsoa21                            | string | E01004736 |
      | result.codes.msoa21                            | string | E02000977 |
      | result.codes.oa21                              | string | E00023938 |
      | result.codes.ruc11                             | string | A1        |
      | result.codes.ruc21                             | string | UN1       |
      | result.codes.lep1                              | string | E37000051 |

  @negative @request @lookup
  Scenario Outline: Lookup request for unknown postcode returns not found without coordinates
    Given the "<postcode>" to search
    When I request the postcode details
    Then the postcode lookup should report that the postcode was not found
    And no longitude or latitude should be available

    Examples:
      | postcode |
      | ZZ99 9ZZ |
      | ZZ999ZZ  |
