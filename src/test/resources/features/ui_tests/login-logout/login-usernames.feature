@Login
Feature: login
  As a user
  I want to login using the accepted usernames
  So that I can verify valid and locked user login behaviour

  Background:
    Given I navigate to "/"
    #Then I should be on the "login" page

  @SmokeTest @RegressionTest @Login
  Scenario Outline: Login with different users
    When I login with username "<username>" and password "<password>"
    Then Login result should be "<result>"
    And I close the google password manager popup

    Examples:
      | username                | password     | result     |
      | standard_user           | secret_sauce | SUCCESS    |
      | locked_out_user         | secret_sauce | LOCKED_OUT |
      | problem_user            | secret_sauce | SUCCESS    |
      | performance_glitch_user | secret_sauce | SUCCESS    |
      | error_user              | secret_sauce | SUCCESS    |
      | visual_user             | secret_sauce | SUCCESS    |

  @SmokeTest @RegressionTest @ValidLogin
  Scenario Outline: Login successfully with accepted users
    When I login as an accepted "<username>" user
    Then I should be redirected to the "inventory" page
    And I close the google password manager popup
    Examples:
      | username                |
      | standard_user           |
      | problem_user            |
      | performance_glitch_user |
      | error_user              |
      | visual_user             |

  @RegressionTest @InvalidLogin @LockedOutUser
  Scenario: Locked out user cannot login to SauceDemo
    When I login with username "locked_out_user" and password "secret_sauce"
    Then I should see login error "Epic sadface: Sorry, this user has been locked out."
