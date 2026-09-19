@login_logout @all
Feature: Login Functionality
  As a user
  I want to login into the application
  So that I can make an online purchase

  Background: Open the login page
    Given I navigate to "/"
    Then I should be on the "login" page
    And I should see the login form is present and visible


  Scenario Outline: Accepted users can login and reach the inventory page
    When I login as an accepted "<username>" user
    Then I should be on the "inventory" page
    And I close the google password manager popup
    And the page header should be "Products"
    And I should see 6 product items
    And I logout from the "inventory" page

    Examples:
      | username                |
      | standard_user           |
      | problem_user            |
      | performance_glitch_user |
      | error_user              |
      | visual_user             |

  Scenario: Locked out user sees login error
    When I login as an accepted "locked_out_user" user
    Then I should see login error "Epic sadface: Sorry, this user has been locked out."

  Scenario Outline: Invalid login attempts show validation errors
    When I login with username "<username>" and password "<password>"
    Then I should see login error "<expected_error>"

    Examples:
      | username                                      | password                                      | expected_error                                                            |
      | locked_out_user                               | secret_sauce                                  | Epic sadface: Sorry, this user has been locked out.                       |
      | locked_out_user@                              | secret_sauce                                  | Epic sadface: Username and password do not match any user in this service |
      | locked_out_user@                              |                                               | Epic sadface: Password is required                                        |
      |                                               | secret_sauce                                  | Epic sadface: Username is required                                        |
      |                                               |                                               | Epic sadface: Username is required                                        |
      | wet                                           |                                               | Epic sadface: Password is required                                        |
      | =$%&*=                                        |                                               | Epic sadface: Password is required                                        |
      | 1                                             | 1                                             | Epic sadface: Username and password do not match any user in this service |
      | a                                             | a                                             | Epic sadface: Username and password do not match any user in this service |
      | ^                                             | ^                                             | Epic sadface: Username and password do not match any user in this service |
      | ^                                             | 012345678901234567890123456789009800000000000 | Epic sadface: Username and password do not match any user in this service |
      | 012345678901234567890123456789009800000000000 | 123456                                        | Epic sadface: Username and password do not match any user in this service |

  @TC_LLF_001 @validLogin @regression @smoke @sanity
  Scenario: 001 - login with valid credentials as a standard user
    When I login as an accepted "standard" user
    Then I should be on the "inventory" page
    And I close the google password manager popup
    And I should see 6 product items

  @TC_LLF_002 @login @validLogin @regression
  Scenario: 002 - login in login page
    When I login as an accepted "standard" user
    Then I should be on the "inventory" page
    And I close the google password manager popup
    And I should see 6 product items

  @TC_LLF_003 @invalidLogin @regression
  Scenario: 003 - login with invalid credentials as a locked out user
    When I login as an accepted "locked out" user
    Then I should see login error "Epic sadface: Sorry, this user has been locked out."

  @TC_LLF_004 @login @validLogin @regression
  Scenario: 004 - login with valid credentials as a problem user
    When I login as an accepted "problem" user
    Then I should be on the "inventory" page
    And I close the google password manager popup
    And I should see 6 product items

  @TC_LLF_005 @login @validLogin @regression
  Scenario: 005 - login with valid credentials as a performance glitch user
    When I login as an accepted "performance glitch" user
    Then I should be on the "inventory" page
    And I close the google password manager popup
    And I should see 6 product items

  @TC_LLF_006 @login @validLogin @regression
  Scenario Outline: 006 - login with accepted usernames and password
    When I login with username "<username>" and password "<password>"
    Then I should be on the "<page>" page
    And I close the google password manager popup
    And I should see 6 product items
    And I logout from the "<page>" page

    Examples:
      | username                | password     | page      |
      | standard_user           | secret_sauce | inventory |
      | performance_glitch_user | secret_sauce | inventory |
      | problem_user            | secret_sauce | inventory |
      | error_user              | secret_sauce | inventory |
      | visual_user             | secret_sauce | inventory |

  @TC_LLF_007 @login @validLogin @regression
  Scenario: 007 - successful login with regular user
    When I login as an accepted "standard" user
    Then I should be on the "inventory" page
    And I close the google password manager popup
    And I should see 6 product items

  @ErrorValidation
  @TC_LLF_008 @login @invalidLogin @regression
  Scenario: 008 - failed login with invalid password
    When I login with username "standard_user" and password "password"
    Then I should see login error "Epic sadface: Username and password do not match any user in this service"

  @ErrorValidation
  @TC_LLF_009 @login @invalidLogin @regression
  Scenario: 009 - failed login with locked out user
    When I login as an accepted "locked out" user
    Then I should see login error "Epic sadface: Sorry, this user has been locked out."

  @TC_LLF_010 @login @validLogin @regression @performance
  Scenario: 010 - login as performance glitch user
    When I login as an accepted "performance glitch" user
    Then I should be on the "inventory" page
    And I close the google password manager popup

  @TC_LLF_011 @ErrorValidation @login @invalidLogin @regression
  Scenario Outline: 011 - login with invalid credentials
    When I login with username "<username>" and password "<password>"
    Then I should see login error "<expected_error>"

    Examples:
      | username                                      | password                                      | expected_error                                                            |
      | locked_out_user                               | secret_sauce                                  | Epic sadface: Sorry, this user has been locked out.                       |
      | locked_out_user@                              | secret_sauce                                  | Epic sadface: Username and password do not match any user in this service |
      | locked_out_user@                              |                                               | Epic sadface: Password is required                                        |
      |                                               | secret_sauce                                  | Epic sadface: Username is required                                        |
      |                                               |                                               | Epic sadface: Username is required                                        |
      | wet                                           |                                               | Epic sadface: Password is required                                        |
      | =$%&*=                                        |                                               | Epic sadface: Password is required                                        |
      | 1                                             | 1                                             | Epic sadface: Username and password do not match any user in this service |
      | a                                             | a                                             | Epic sadface: Username and password do not match any user in this service |
      | ^                                             | ^                                             | Epic sadface: Username and password do not match any user in this service |
      | ^                                             | 012345678901234567890123456789009800000000000 | Epic sadface: Username and password do not match any user in this service |
      | 012345678901234567890123456789009800000000000 | 123456                                        | Epic sadface: Username and password do not match any user in this service |

  @TC_LLF_012 @login @validLogin @regression
  Scenario: 012 - successful logout returns to login page
    When I login as an accepted "standard" user
    Then I should be on the "inventory" page
    And I close the google password manager popup
    And I should see 6 product items
    When I logout from the "inventory" page
    Then I should be on the "login" page

  @TC_LLF_013 @login @validLogin @regression
  Scenario: 013 - successful logout from inventory page
    When I login as an accepted "standard" user
    Then I should be on the "inventory" page
    And I close the google password manager popup
    And I should see 6 product items
    When I logout from the "inventory" page
    Then I should be on the "login" page

  @TC_LLF_014 @login @validLogin @regression
  Scenario: 014 - login form is displayed after logout
    When I login as an accepted "standard" user
    Then I should be on the "inventory" page
    And I close the google password manager popup
    And I should see 6 product items
    When I logout from the "inventory" page
    Then I should be on the "login" page
    And the login form should be present and visible
