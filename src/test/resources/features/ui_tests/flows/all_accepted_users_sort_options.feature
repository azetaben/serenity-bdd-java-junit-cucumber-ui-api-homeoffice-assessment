@ProductsDisplayedPage @Sorting @regression @all
Feature: all accepted users product sorting
  As an accepted SauceDemo user
  I want to use every product sort option
  So that I can verify inventory ordering for each user type

  @TC_SORT_ACCEPTED_USERS_001
  Scenario Outline: Accepted logged-in users can use product sort option <sort_option>
    Given I am logged in as "<user_type>"
    Then I should be on the "inventory" page
    And the inventory page is displayed
    And the product sort dropdown is displayed
    And the product sort options are displayed
    When I sort products by "<sort_option>"
    Then the selected sort option should be "<sort_option>"
    And the products should be sorted by "<sort_option>"

    Examples:
      | user_type          | sort_option          |
      | standard           | Name (A to Z)        |
      | standard           | Name (Z to A)        |
      | standard           | Price (low to high)  |
      | standard           | Price (high to low)  |
      | problem            | Name (A to Z)        |
      | problem            | Name (Z to A)        |
      | problem            | Price (low to high)  |
      | problem            | Price (high to low)  |
      | performance glitch | Name (A to Z)        |
      | performance glitch | Name (Z to A)        |
      | performance glitch | Price (low to high)  |
      | performance glitch | Price (high to low)  |
      | error              | Name (A to Z)        |
      | error              | Name (Z to A)        |
      | error              | Price (low to high)  |
      | error              | Price (high to low)  |
      | visual             | Name (A to Z)        |
      | visual             | Name (Z to A)        |
      | visual             | Price (low to high)  |
      | visual             | Price (high to low)  |

  @TC_SORT_ACCEPTED_USERS_002
  Scenario Outline: standard logged-in user can use product sort option <sort_option>
    Given I am logged in as "<user_type>"
    Then I should be on the "inventory" page
    And the inventory page is displayed
    And the product sort dropdown is displayed
    And the product sort options are displayed
    When I sort products by "<sort_option>"
    Then the selected sort option should be "<sort_option>"
    And the products should be sorted by "<sort_option>"

    Examples:
      | user_type          | sort_option          |
      | standard           | Name (A to Z)        |
      | standard           | Name (Z to A)        |
      | standard           | Price (low to high)  |
      | standard           | Price (high to low)  |

  @TC_SORT_ACCEPTED_USERS_003
  Scenario Outline: problem logged-in user can use product sort option <sort_option>
    Given I am logged in as "<user_type>"
    Then I should be on the "inventory" page
    And the inventory page is displayed
    And the product sort dropdown is displayed
    And the product sort options are displayed
    When I sort products by "<sort_option>"
    Then the selected sort option should be "<sort_option>"
    But the products should not be sorted by "<sort_option>"

    Examples:
      | user_type          | sort_option          |
      | problem            | Name (A to Z)        |
      | problem            | Name (Z to A)        |
      | problem            | Price (low to high)  |
      | problem            | Price (high to low)  |

  @TC_SORT_ACCEPTED_USERS_003a
  Scenario Outline: problem logged-in user can use product sort option <sort_option> - Name (A to Z)
    Given I am logged in as "<user_type>"
    Then I should be on the "inventory" page
    And the inventory page is displayed
    And the product sort dropdown is displayed
    And the product sort options are displayed
    When I sort products by "<sort_option>"
    Then the selected sort option should be "<sort_option>"
    And the products should be sorted by "<sort_option>"

    Examples:
      | user_type          | sort_option          |
      | problem            | Name (A to Z)        |



  @TC_SORT_ACCEPTED_USERS_004
  Scenario Outline: performance glitch logged-in user can use product sort option <sort_option>
    Given I am logged in as "<user_type>"
    Then I should be on the "inventory" page
    And the inventory page is displayed
    And the product sort dropdown is displayed
    And the product sort options are displayed
    When I sort products by "<sort_option>"
    Then the selected sort option should be "<sort_option>"
    And the products should be sorted by "<sort_option>"

    Examples:
      | user_type          | sort_option          |
      | performance glitch | Name (A to Z)        |
      | performance glitch | Name (Z to A)        |
      | performance glitch | Price (low to high)  |
      | performance glitch | Price (high to low)  |


  @TC_SORT_ACCEPTED_USERS_005
  Scenario Outline: error user logged-in can use product sort option <sort_option>
    Given I am logged in as "<user_type>"
    Then I should be on the "inventory" page
    And the inventory page is displayed
    And the product sort dropdown is displayed
    And the product sort options are displayed
    When I sort products by "<sort_option>"
    Then the selected sort option should be "<sort_option>"
    And the products should be sorted by "<sort_option>"

    Examples:
      | user_type          | sort_option          |
      | error              | Name (A to Z)        |
      | error              | Name (Z to A)        |
      | error              | Price (low to high)  |
      | error              | Price (high to low)  |

  @TC_SORT_ACCEPTED_USERS_006
  Scenario Outline: visual user logged-in can use product sort option <sort_option>
    Given I am logged in as "<user_type>"
    Then I should be on the "inventory" page
    And the inventory page is displayed
    And the product sort dropdown is displayed
    And the product sort options are displayed
    When I sort products by "<sort_option>"
    Then the selected sort option should be "<sort_option>"
    And the products should be sorted by "<sort_option>"

    Examples:
      | user_type          | sort_option          |
      | visual             | Name (A to Z)        |
      | visual             | Name (Z to A)        |
      | visual             | Price (low to high)  |
      | visual             | Price (high to low)  |