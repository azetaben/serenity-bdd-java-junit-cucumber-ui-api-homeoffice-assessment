@login_logout @edge @all
Feature: login and logout edge cases
  As a user
  I want invalid credentials and post-logout access handled consistently
  So that protected pages stay protected

  Background:
    Given I navigate to "/"
    Then I should be on the "login" page

  @TC_EDGE_001 @negative
  Scenario Outline: Login validation messages are displayed
    When I login with username "<username>" and password "<password>"
    Then I should see login error "<error>"
    And I should be on the "login" page

    Examples:
      | username        | password       | error                                                                     |
      |                 |                | Epic sadface: Username is required                                        |
      |                 | secret_sauce   | Epic sadface: Username is required                                        |
      | standard_user   |                | Epic sadface: Password is required                                        |
      | locked_out_user | secret_sauce   | Epic sadface: Sorry, this user has been locked out.                       |
      | standard_user   | wrong_password | Epic sadface: Username and password do not match any user in this service |
      | STANDARD_USER   | secret_sauce   | Epic sadface: Username and password do not match any user in this service |
      | standard_user   | SECRET_SAUCE   | Epic sadface: Username and password do not match any user in this service |

  @TC_EDGE_020 @regression
  Scenario: Logout returns the user to login
    When I login as an accepted "standard_user" user
    Then I should be on the "inventory" page
    And I close the google password manager popup
    When I logout from the "inventory" page
    Then I should be on the "login" page

  @TC_EDGE_024 @regression
  Scenario: Direct inventory access is blocked after logout
    When I login as an accepted "standard_user" user
    Then I should be on the "inventory" page
    And I close the google password manager popup
    When I logout from the "inventory" page
    Then I should be on the "login" page
    When I open inventory page directly
    Then I should be on the "login" page
    And I should see login error "Epic sadface: You can only access '/inventory.html' when you are logged in."
