@Logout @regression @all
Feature: logout verification
  As a user
  I want to logout from protected pages
  So that my session is closed

  Background:
    Given I navigate to "/"
    Then I should be on the "login" page
    When I login as an accepted "standard_user" user
    Then I should be on the "inventory" page
    And I close the google password manager popup

  @TC_LGT_001
  Scenario: Logout from inventory page
    When I logout from the "inventory" page
    Then I should be on the "login" page

  @TC_LGT_002
  Scenario: Logout after opening cart
    When I open the shopping cart
    Then I should be on the "cart" page
    When I logout from the "cart" page
    Then I should be on the "login" page

  @TC_LGT_003
  Scenario: Logout from inventory item page
    When I open "Sauce Labs Backpack" from the inventory page
    Then I should be on the "inventory item" page
    When I logout from the "inventory item" page
    Then I should be on the "login" page
