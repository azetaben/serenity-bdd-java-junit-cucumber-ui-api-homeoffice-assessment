@miscellaneous @Accessibility @regression @all
Feature: accessibility smoke checks
  As a test engineer
  I want key pages to expose their primary controls
  So that the pages are usable through the shared page objects

  Background:
    Given I navigate to "/"
    Then I should be on the "login" page

  @TC_MC_001
  Scenario: Login page accessibility smoke check
    Then the login page is displayed
    And the accepted usernames are displayed
      | standard_user           |
      | locked_out_user         |
      | problem_user            |
      | performance_glitch_user |
      | error_user              |
      | visual_user             |
    And the password for all users is "secret_sauce"

  @TC_MC_001_1
  Scenario: Inventory page accessibility smoke check
    When I login as an accepted "standard" user
    Then I should be on the "inventory" page
    And I close the google password manager popup
    And the inventory page is displayed
    And the header logo is displayed
    And the product sort dropdown is displayed
    And the product sort options are displayed
    And the footer is displayed
    And the footer social links are displayed
