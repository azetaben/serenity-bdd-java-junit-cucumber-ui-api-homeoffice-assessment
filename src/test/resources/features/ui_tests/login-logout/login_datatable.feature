@login_logout @dataDriven @all
Feature: login data-driven tests
  As a user
  I want login behaviour validated across credential sets
  So that accepted and rejected users are handled correctly

  Background:
    Given I navigate to "/"
    Then I should be on the "login" page

  @validLogin @regression
  Scenario Outline: Successful login with valid credentials for <username>
    When I login with username "<username>" and password "<password>"
    Then I should be on the "inventory" page
    And I close the google password manager popup
    And I should see 6 product items

    Examples:
      | username                | password     |
      | standard_user           | secret_sauce |
      | problem_user            | secret_sauce |
      | performance_glitch_user | secret_sauce |
      | error_user              | secret_sauce |
      | visual_user             | secret_sauce |

  @invalidLogin @regression
  Scenario Outline: Failed login with invalid credentials
    When I login with username "<username>" and password "<password>"
    Then I should see login error "<expectedMessage>"
    And I should be on the "login" page

    Examples:
      | username        | password       | expectedMessage                                                           |
      | locked_out_user | secret_sauce   | Epic sadface: Sorry, this user has been locked out.                       |
      | invalid_user    | wrong_password | Epic sadface: Username and password do not match any user in this service |
      | standard_user   | wrong_password | Epic sadface: Username and password do not match any user in this service |
      |                 | secret_sauce   | Epic sadface: Username is required                                        |
      | standard_user   |                | Epic sadface: Password is required                                        |
      |                 |                | Epic sadface: Username is required                                        |
