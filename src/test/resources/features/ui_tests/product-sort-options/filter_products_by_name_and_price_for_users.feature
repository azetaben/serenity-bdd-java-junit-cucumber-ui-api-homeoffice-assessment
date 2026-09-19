@ProductsDisplayedPage @ParallelRun @all
Feature: product sorting for accepted users
  As an accepted user
  I want to verify sorting behaviour
  So that product lists are ordered by the selected option

  Background:
    Given I navigate to "/"
    Then I should be on the "login" page

  @Sorting
  Scenario Outline: Accepted users see default Name A to Z sorting
    When I login with username "<username>" and password "secret_sauce"
    Then Login result should be "SUCCESS"
    And I should be on the "inventory" page
    And I close the google password manager popup
    And the product sort dropdown is displayed
    And the selected sort option should be "Name (A to Z)"
    And the products should be sorted by "Name (A to Z)"

    Examples:
      | username                |
      | standard_user           |
      | performance_glitch_user |
      | visual_user             |

  @Sorting
  Scenario Outline: Accepted users can sort by Name Z to A
    When I login with username "<username>" and password "secret_sauce"
    Then Login result should be "SUCCESS"
    And I should be on the "inventory" page
    And I close the google password manager popup
    When I sort products by "Name (Z to A)"
    Then the selected sort option should be "Name (Z to A)"
    And the products should be sorted by "Name (Z to A)"

    Examples:
      | username                |
      | standard_user           |
      | performance_glitch_user |
      | visual_user             |

  @Sorting
  Scenario Outline: Accepted users can sort by Price low to high
    When I login with username "<username>" and password "secret_sauce"
    Then Login result should be "SUCCESS"
    And I should be on the "inventory" page
    And I close the google password manager popup
    When I sort products by "Price (low to high)"
    Then the selected sort option should be "Price (low to high)"
    And the products should be sorted by "Price (low to high)"

    Examples:
      | username                |
      | standard_user           |
      | performance_glitch_user |
      | visual_user             |

  @Sorting
  Scenario Outline: Accepted users can sort by Price high to low
    When I login with username "<username>" and password "secret_sauce"
    Then Login result should be "SUCCESS"
    And I should be on the "inventory" page
    And I close the google password manager popup
    When I sort products by "Price (high to low)"
    Then the selected sort option should be "Price (high to low)"
    And the products should be sorted by "Price (high to low)"

    Examples:
      | username                |
      | standard_user           |
      | performance_glitch_user |
      | visual_user             |
