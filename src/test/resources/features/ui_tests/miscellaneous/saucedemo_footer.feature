@@ui @footer @regression
Feature: footer
  As a logged-in user
  I want to see footer links and copyright information
  So that the site footer can be verified

  Background:
    Given I navigate to "/"
    Then I should be on the "login" page
    When I login as an accepted "standard" user
    Then I should be on the "inventory" page
    And I close the google password manager popup

  @TC_SD_FOOTER_001 @smoke
  Scenario: Footer social links and copyright are displayed
    Then the footer is displayed
    And the footer social links are displayed
    And the footer copy should be "© 2026 Sauce Labs. All Rights Reserved. Terms of Service | Privacy Policy"
