@login_logout @all
Feature: login and logout
  As a user
  I want to login and logout using reusable page steps
  So that session behaviour is validated consistently

  Background:
    Given I navigate to "/"
    Then I should be on the "login" page

  @TC_LLF_012 @regression
  Scenario: Standard user can logout from inventory
    When I login as an accepted "standard" user
    Then I should be on the "inventory" page
    And I close the google password manager popup
    And I should see 6 product items
    When I logout from the "inventory" page
    Then I should be on the "login" page

  @TC_LLF_02200 @regression
  Scenario Outline: Accepted users can logout from inventory
    When I login as an accepted "<username>" user
    Then I should be on the "inventory" page
    And I close the google password manager popup
    When I logout from the "inventory" page
    Then I should be on the "login" page

    Examples:
      | username                |
      | standard_user           |
      | problem_user            |
      | performance_glitch_user |
      | error_user              |
      | visual_user             |
