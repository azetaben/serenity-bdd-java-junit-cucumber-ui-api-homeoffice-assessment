@miscellaneous @regression @performance @all
Feature: performance smoke checks
  As a user interested in performance
  I want key pages to load and render their primary controls
  So that the performance feature uses maintained framework steps

  @TC_MC_007
  Scenario: Login page loads successfully
    Given I navigate to "/"
    Then I should be on the "login" page
    And the login form should be present and visible

  @TC_MC_007_1
  Scenario: Products page loads successfully for performance user
    Given I navigate to "/"
    Then I should be on the "login" page
    When I login with username "performance_glitch_user" and password "secret_sauce"
    Then Login result should be "SUCCESS"
    And I should be on the "inventory" page
    And I close the google password manager popup
    And the inventory page is displayed
    And I should see 6 inventory items
