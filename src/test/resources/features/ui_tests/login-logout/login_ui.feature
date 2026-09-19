@login_logout @ui @all
Feature: login page UI
  As a user
  I want to see login page guidance
  So that I can log in with a supported user

  @TC_UI_001
  Scenario: Login page displays logo, form, accepted users and password
    Given I navigate to "/"
    Then I should be on the "login" page
    And the login form should be present and visible
    And the accepted usernames are displayed
      | standard_user           |
      | locked_out_user         |
      | problem_user            |
      | performance_glitch_user |
      | error_user              |
      | visual_user             |
    And the password for all users is "secret_sauce"
