@ui @login @all
Feature: login users validation
  As a user
  I want login validation for accepted and invalid users
  So that only valid users can access the products page

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
    When I login as "<username>" with password "secret_sauce"
    Then I should be on the "inventory" page
    Examples:
      | username                |
      | standard_user           |
      | problem_user            |
      | performance_glitch_user |
      | error_user              |
      | visual_user             |

  @TC_LU_004_1 @validLogin @regression
  Scenario Outline: 01 - Verify every accepted user account
    Then the accepted users list contains "<username>"
    When I login as "<username>" with password "secret_sauce"
    Then I should be on the "inventory" page
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
