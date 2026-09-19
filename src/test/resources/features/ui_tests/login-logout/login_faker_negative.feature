@login_logout @negative @error_validation_tests @all
Feature: negative login validation
  As a user
  I want invalid login attempts rejected
  So that only accepted credentials can access inventory

  Background:
    Given I navigate to "/"
    Then I should be on the "login" page

  Scenario Outline: Invalid login remains rejected
    When I login with username "<username>" and password "<password>"
    Then I should see login error "<expectedError>"

    Examples:
      | username      | password         | expectedError                                                             |
      | invalid_user  | secret_sauce     | Epic sadface: Username and password do not match any user in this service |
      | standard_user | invalid_password | Epic sadface: Username and password do not match any user in this service |
      |               | secret_sauce     | Epic sadface: Username is required                                        |
      | standard_user |                  | Epic sadface: Password is required                                        |
