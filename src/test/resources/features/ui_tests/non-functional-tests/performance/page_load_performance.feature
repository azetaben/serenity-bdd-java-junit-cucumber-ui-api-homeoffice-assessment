@regression @performance
Feature: page load smoke checks
  As an application user
  I want key pages to load successfully
  So that the application feels responsive during normal use

  Background:
    Given I navigate to "/"
    Then I should be on the "login" page

  @TC_PLP_001
  Scenario: Login page loads with expected content
    Then the login page is displayed
    And the accepted usernames are displayed
      | standard_user           |
      | locked_out_user         |
      | problem_user            |
      | performance_glitch_user |
      | error_user              |
      | visual_user             |

  @TC_PLP_002
  Scenario Outline: Authenticated inventory page loads for accepted users
    When I login with username "<username>" and password "<password>"
    Then Login result should be "SUCCESS"
    And I should be on the "inventory" page
    And I close the google password manager popup
    And the inventory page is displayed
    And I should see 6 inventory items

    Examples:
      | username                | password     |
      | standard_user           | secret_sauce |
      | performance_glitch_user | secret_sauce |
