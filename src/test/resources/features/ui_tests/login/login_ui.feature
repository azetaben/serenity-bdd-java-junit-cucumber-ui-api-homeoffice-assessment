@@ui @login @login_logout @all
Feature: login page UI
  As a user
  I want to see the login page guidance
  So that I can log in with a supported user

  @TC_UI_001
  Scenario Outline: Login page displays logo, form, accepted users and password
    Given I navigate to "/"
    And the page title should be "Swag Labs"
    And the page URL should be "https://www.saucedemo.com/"
    Then I should be on the "login" page
    And the login form should be present and visible
    Then the accepted users list contains "<username>"
    And the accepted usernames are displayed
      | standard_user           |
      | locked_out_user         |
      | problem_user            |
      | performance_glitch_user |
      | error_user              |
      | visual_user             |
    And the password for all users is "secret_sauce"

    Examples:
      | username                |
      | standard_user           |
      | problem_user            |
      | performance_glitch_user |
      | error_user              |
      | visual_user             |

  @TC_UI_001_1
  Scenario Outline: 01 - Verify Login page displays logo, form and every accepted users and password
    Given I navigate to "/"
    And the page title should be "Swag Labs"
    And the page URL should be "https://www.saucedemo.com/"
    Then I should be on the "login" page
    And the login form should be present and visible
    Then the accepted users list contains "<username>"
    And the password for all users is "secret_sauce"
    Examples:
      | username                |
      | standard_user           |
      | problem_user            |
      | performance_glitch_user |
      | error_user              |
      | visual_user             |

