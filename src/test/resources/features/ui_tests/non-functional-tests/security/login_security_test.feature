@security @login @regression
Feature: login security checks
  As a user
  I want login to reject invalid and malicious credentials
  So that protected pages remain secure

  Background:
    Given I navigate to "/"
    Then I should be on the "login" page
    And the login form should be present and visible

  @TC_LLF_023 @invalidLogin
  Scenario: Reject SQL injection style payload
    When I login with username "' OR '1'='1" and password "' OR '1'='1"
    Then Login result should be "INVALID"
    And I should see login error "Epic sadface: Username and password do not match any user in this service"
    And I should be on the "login" page

  @TC_LLF_024 @invalidLogin
  Scenario: Reject XSS style payload in username
    When I login with username "<script>alert('xss')</script>" and password "secret_sauce"
    Then Login result should be "INVALID"
    And I should see login error "Epic sadface: Username and password do not match any user in this service"
    And I should be on the "login" page

  @TC_LLF_025 @invalidLogin
  Scenario Outline: Repeated invalid password attempts do not grant access
    When I login with username "standard_user" and password "<password>"
    Then Login result should be "INVALID"
    And I should see login error "Epic sadface: Username and password do not match any user in this service"
    And I should be on the "login" page

    Examples:
      | password        |
      | wrong_password1 |
      | wrong_password2 |
      | wrong_password3 |

  @TC_LLF_026 @invalidLogin
  Scenario: Consistent error message for invalid user and invalid password
    When I login with username "non_existing_user_123" and password "secret_sauce"
    Then Login result should be "INVALID"
    And I should see login error "Epic sadface: Username and password do not match any user in this service"

  @TC_LLF_028 @invalidLogin
  Scenario: Block unauthenticated direct navigation to inventory page
    Given I navigate to "/inventory.html"
    Then I should be on the "login" page
    And I should see login error "Epic sadface: You can only access '/inventory.html' when you are logged in."

  @TC_LLF_032 @invalidLogin
  Scenario: Failed login should not allow direct navigation to inventory page
    When I login with username "standard_user" and password "wrong_password"
    Then Login result should be "INVALID"
    Given I navigate to "/inventory.html"
    Then I should be on the "login" page
    And I should see login error "Epic sadface: You can only access '/inventory.html' when you are logged in."

  Scenario Outline: Invalid login combination attempts
    When I login with username "<username>" and password "<password>"
    Then Login result should be "<result>"
    And I should see login error "<error_message>"

    Examples:
      | username        | password       | result            | error_message                                                             |
      | locked_out_user | secret_sauce   | LOCKED_OUT        | Epic sadface: Sorry, this user has been locked out.                       |
      | invalid_user    | secret_sauce   | INVALID           | Epic sadface: Username and password do not match any user in this service |
      | standard_user   | wrong_password | INVALID           | Epic sadface: Username and password do not match any user in this service |
      |                 | secret_sauce   | USERNAME_REQUIRED | Epic sadface: Username is required                                        |
      | standard_user   |                | PASSWORD_REQUIRED | Epic sadface: Password is required                                        |
