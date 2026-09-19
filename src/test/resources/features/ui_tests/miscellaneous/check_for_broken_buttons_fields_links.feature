@miscellaneous @regression @all
Feature: visible controls and links
  As a regular user
  I want to verify key buttons, fields, and links
  So that the main page controls are available

  Background:
    Given I navigate to "/"
    Then I should be on the "login" page

  @TC_MC_002
  Scenario: Login page fields and button are displayed
    Then the login page is displayed
    And the accepted usernames are displayed
      | standard_user           |
      | locked_out_user         |
      | problem_user            |
      | performance_glitch_user |
      | error_user              |
      | visual_user             |
    And the password for all users is "secret_sauce"

  @TC_MC_003
  Scenario: Inventory controls are displayed after login
    When I login as an accepted "standard" user
    Then I should be on the "inventory" page
    And I close the google password manager popup
    And the inventory page is displayed
    And the header logo is displayed
    And the product sort dropdown is displayed
    And the product sort options are displayed

  @TC_MC_004
  Scenario: Navigation menu links are displayed after login
    When I login as an accepted "standard" user
    Then I should be on the "inventory" page
    When I open the navigation menu
    Then the navigation menu is displayed
    And the navigation menu links are displayed

  @TC_MC_005
  Scenario Outline: Accepted users can reach inventory controls
    When I login with username "<username>" and password "secret_sauce"
    Then Login result should be "<result>"
    And I should be on the "inventory" page
    And the product sort dropdown is displayed

    Examples:
      | username                | result  |
      | standard_user           | SUCCESS |
      | problem_user            | SUCCESS |
      | performance_glitch_user | SUCCESS |
      | error_user              | SUCCESS |
      | visual_user             | SUCCESS |
