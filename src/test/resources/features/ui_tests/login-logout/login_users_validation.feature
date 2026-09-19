@login_logout @all
Feature: login users validation
  As a user
  I want accepted and invalid user validation
  So that only valid users can access inventory

  Background:
    Given I navigate to "/"
    Then I should be on the "login" page

  @TC_LU_001 @validLogin @regression
  Scenario: Standard user can log in
    When I login with username "standard_user" and password "secret_sauce"
    Then I should be on the "inventory" page
    And I close the google password manager popup
    And I should see 6 product items

  @TC_LU_004 @validLogin @regression
  Scenario Outline: Accepted users can log in when they are not locked out
    When I login with username "<username>" and password "secret_sauce"
    Then I should be on the "inventory" page
    And I close the google password manager popup
    Examples:
      | username                |
      | standard_user           |
      | problem_user            |
      | performance_glitch_user |
      | error_user              |
      | visual_user             |

  @TC_LU_005 @negative @regression
  Scenario Outline: Login validation messages are displayed for invalid attempts
    When I login with username "<username>" and password "<password>"
    Then I should see login error "<expected_error>"

    Examples:
      | username        | password       | expected_error                                                            |
      | locked_out_user | secret_sauce   | Epic sadface: Sorry, this user has been locked out.                       |
      | wrong_user      | secret_sauce   | Epic sadface: Username and password do not match any user in this service |
      | standard_user   | wrong_password | Epic sadface: Username and password do not match any user in this service |
      |                 | secret_sauce   | Epic sadface: Username is required                                        |
      | standard_user   |                | Epic sadface: Password is required                                        |
