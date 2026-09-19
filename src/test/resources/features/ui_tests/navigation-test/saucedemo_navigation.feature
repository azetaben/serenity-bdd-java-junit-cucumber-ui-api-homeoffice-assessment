@@ui @navigation @regression
Feature: navigation menu
  As a logged-in user
  I want to use the application navigation menu
  So that I can access menu options and logout

  Background:
    Given I navigate to "/"
    Then I should be on the "login" page
    When I login as an accepted "standard" user
    Then I should be on the "inventory" page
    And I close the google password manager popup

  @TC_SD_NAV_001 @smoke
  Scenario: Header logo and navigation menu links are displayed
    Then the header logo is displayed
    When I open the navigation menu
    Then the navigation menu is displayed
    And the navigation menu links are displayed

  @TC_SD_NAV_002
  Scenario: User can close the navigation menu
    When I open the navigation menu
    Then the navigation menu is displayed
    When I close the navigation menu
    Then the navigation menu is hidden

  @TC_SD_NAV_003
  Scenario: All Items menu option keeps the user on inventory page
    When I open the navigation menu
    And I select All Items from the navigation menu
    Then I should be on the "inventory" page

  @TC_SD_NAV_004
  Scenario: Reset App State menu option is selectable
    When I open the navigation menu
    And I select Reset App State from the navigation menu
    Then I should be on the "inventory" page

  @TC_SD_NAV_005 @logout
  Scenario: User can logout from navigation menu
    When I open the navigation menu
    And I select Logout from the navigation menu
    Then I should be logged out of SauceDemo
    And I should be on the "login" page

  @TC_SD_NAV_006 @sort
  Scenario: Product sort dropdown options are displayed
    Then the product sort dropdown is displayed
    And the product sort options are displayed

  @TC_SD_NAV_007 @sort
  Scenario Outline: User can sort products from the inventory page
    When I sort products by "<sort_option>"
    Then the selected sort option should be "<sort_option>"
    And the products should be sorted by "<sort_option>"

    Examples:
      | sort_option         |
      | Name (A to Z)       |
      | Name (Z to A)       |
      | Price (low to high) |
      | Price (high to low) |
