@crossBrowser @chrome @firefox @edge @smoke
Feature: Cross browser smoke test

  Scenario: User can login across supported browsers
    Given I navigate to "/"
    Then I should be on the "login" page
    When I login as an accepted "standard" user
    Then I should be on the "inventory" page
    And I close the google password manager popup
    And the inventory page is displayed
    When I logout from the "inventory" page
    Then I should be on the "login" page
