@Login @all
Feature: login application
  As a user
  I want the login page and accepted login flow to work
  So that I can access and leave the products page

  Background:
    Given I navigate to "/"
    Then I should be on the "login" page
    And the login form should be present and visible
    And the accepted usernames are displayed
      | standard_user           |
      | locked_out_user         |
      | problem_user            |
      | performance_glitch_user |
      | error_user              |
      | visual_user             |
    And the password for all users is "secret_sauce"

  @SmokeTest @RegressionTest @Login
  Scenario: Login and logout from inventory
    When I login as an accepted "standard" user
    Then I should be on the "inventory" page
    And I close the google password manager popup
    And I should see 6 product items
    When I logout from the "inventory" page
    Then I should be on the "login" page
