@ProductsDisplayedPage @ParallelRun @all
Feature: product sorting by name and price
  As a valid user
  I want to sort products by name and price
  So that I can find products more easily

  Background:
    Given I navigate to "/"
    Then I should be on the "login" page

  @TC_PDP_001
  Scenario Outline: Default sorting is Name A to Z for accepted users
    When I login as an accepted "<user_type>" user
    Then I should be on the "inventory" page
    And I close the google password manager popup
    And the inventory page is displayed
    And the product sort dropdown is displayed
    And the product sort options are displayed
    And the selected sort option should be "Name (A to Z)"
    And the products should be sorted by "Name (A to Z)"

    Examples:
      | user_type          |
      | standard           |
      | performance glitch |
      | visual             |

  @TC_PDP_002
  Scenario Outline: Sort products by Name Z to A
    When I login as an accepted "<user_type>" user
    Then I should be on the "inventory" page
    And I close the google password manager popup
    And the selected sort option should be "Name (A to Z)"
    When I sort products by "Name (Z to A)"
    Then the selected sort option should be "Name (Z to A)"
    And the products should be sorted by "Name (Z to A)"

    Examples:
      | user_type          |
      | standard           |
      | performance glitch |

  @TC_PDP_003
  Scenario Outline: Sort products by Price high to low
    When I login as an accepted "<user_type>" user
    Then I should be on the "inventory" page
    And I close the google password manager popup
    When I sort products by "Price (high to low)"
    Then the selected sort option should be "Price (high to low)"
    And the products should be sorted by "Price (high to low)"

    Examples:
      | user_type          |
      | standard           |
      | performance glitch |

  @TC_PDP_004
  Scenario Outline: Sort products by Price low to high
    When I login as an accepted "<user_type>" user
    Then I should be on the "inventory" page
    And I close the google password manager popup
    When I sort products by "Price (low to high)"
    Then the selected sort option should be "Price (low to high)"
    And the products should be sorted by "Price (low to high)"

    Examples:
      | user_type          |
      | standard           |
      | performance glitch |
