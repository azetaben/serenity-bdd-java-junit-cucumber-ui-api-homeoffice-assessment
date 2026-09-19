@accepted_users_login_details @login @all
Feature: Accepted users login details
  As a user
  I want to login using acceptedUsersLogin examples
  So that accepted username details are exercised from the page contract

  Background:
    Given I navigate to "/"
    Then the login page is displayed

  Scenario Outline: login with acceptedUsersLogin successful users
    When I login as an accepted "<username>" user
    Then I should be redirected to the "inventory" page
    And I close the google password manager popup
    And I should see 6 product items
    And I logout from the "inventory" page

    Examples:
      | username                |
      | standard_user           |
      | problem_user            |
      | performance_glitch_user |
      | error_user              |
      | visual_user             |

  Scenario: locked out acceptedUsersLogin user sees error
    When I login as an accepted "locked_out_user" user
    Then I should see login error "Epic sadface: Sorry, this user has been locked out."
