@miscellaneous @regression @all
Feature: footer section
  As a regular user
  I want to verify the footer section
  So that footer links and copy are available

  Background:
    Given I navigate to "/"
    Then I should be on the "login" page
    When I login as an accepted "standard" user
    Then I should be on the "inventory" page
    And I close the google password manager popup

  @TC_MC_006
  Scenario: Footer elements are displayed
    Then the footer is displayed
    And the footer social links are displayed
    And the footer copy should be "© 2026 Sauce Labs. All Rights Reserved. Terms of Service | Privacy Policy"
