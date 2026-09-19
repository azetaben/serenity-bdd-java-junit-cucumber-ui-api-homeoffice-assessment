@login_logout @all @error_validation_tests
Feature: negative login functionality
  As a user
  I want invalid login attempts to show clear errors
  So that authentication failures are visible

  Background:
    Given I navigate to "/"
    Then I should be on the "login" page

  Scenario Outline: Invalid login attempts show expected errors
    When I login with username "<username>" and password "<password>"
    Then I should see login error "<error_message>"
    And I should be on the "login" page

    Examples:
      | username                 | password                     | error_message                                                             |
      | locked_out_user          | secret_sauce                 | Epic sadface: Sorry, this user has been locked out.                       |
      | invalid_user             | secret_sauce                 | Epic sadface: Username and password do not match any user in this service |
      | standard_user            | wrong_password               | Epic sadface: Username and password do not match any user in this service |
      |                          | secret_sauce                 | Epic sadface: Username is required                                        |
      | standard_user            |                              | Epic sadface: Password is required                                        |
      | !@#$%^&*                 | secret_sauce                 | Epic sadface: Username and password do not match any user in this service |
      | standard_user            | !@#$%^&*                     | Epic sadface: Username and password do not match any user in this service |
      | user12345678901234567890 | secret_sauce                 | Epic sadface: Username and password do not match any user in this service |
      | standard_user            | password12345678901234567890 | Epic sadface: Username and password do not match any user in this service |
      | STANDARD_USER            | secret_sauce                 | Epic sadface: Username and password do not match any user in this service |
      | standard_user            | SECRET_SAUCE                 | Epic sadface: Username and password do not match any user in this service |


  @TC_EDGE_URL_HTML_002
  Scenario Outline: Invalid login stays on login page
    When I login with username "<username>" and password "<password>"
    Then I should see login error "<error>"
    And I should be on the "login" page

    Examples:
      | username        | password       | error                                                                     |
      | standard_user   | wrong_password | Epic sadface: Username and password do not match any user in this service |
      | locked_out_user | secret_sauce   | Epic sadface: Sorry, this user has been locked out.                       |
      |                 |                | Epic sadface: Username is required                                        |
