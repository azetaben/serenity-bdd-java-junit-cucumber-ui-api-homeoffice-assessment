@@ui @login @regression
Feature: login
  As a customer
  I want the login page to validate accepted credentials and protected access
  So that only logged-in users can reach the inventory page

  @TC_SD_LOGIN_001 @smoke
  Scenario: Login page displays accepted users and password guidance
    Given I navigate to "/"
    Then the login page is displayed
    And the accepted usernames are displayed
      | standard_user           |
      | locked_out_user         |
      | problem_user            |
      | performance_glitch_user |
      | error_user              |
      | visual_user             |
    And the password for all users is "secret_sauce"

  @TC_SD_LOGIN_002 @security
  Scenario: Inventory page cannot be accessed before login
    Given I open inventory page directly
    Then the login page is displayed
    And I should see login error "Epic sadface: You can only access '/inventory.html' when you are logged in."

  @TC_SD_LOGIN_003 @smoke
  Scenario: Standard user can login successfully
    Given I navigate to "/"
    When I login with username "standard_user" and password "secret_sauce"
    Then I should be on the "inventory" page
    And I close the google password manager popup
    And I should see 6 product items

  @TC_SD_LOGIN_004
  Scenario Outline: Invalid login attempts show the expected validation message
    Given I navigate to "/"
    When I login with username "<username>" and password "<password>"
    Then I should see login error "<expected_error>"

    Examples:
      | username        | password     | expected_error                                                            |
      | locked_out_user | secret_sauce | Epic sadface: Sorry, this user has been locked out.                       |
      | invalid_user    | secret_sauce | Epic sadface: Username and password do not match any user in this service |
      | standard_user   | wrong_pass   | Epic sadface: Username and password do not match any user in this service |
      |                 | secret_sauce | Epic sadface: Username is required                                        |
      | standard_user   |              | Epic sadface: Password is required                                        |
