@login_logout @reset @regression @all
Feature: reset app state
  As a user
  I want to reset app state from the navigation menu
  So that the inventory session can be cleared

  Background:
    Given I navigate to "/"
    Then I should be on the "login" page
    When I login as an accepted "standard" user
    Then I should be on the "inventory" page
    And I close the google password manager popup

  @TC_LLF_013
  Scenario: Reset app state from navigation menu
    When I add "Sauce Labs Backpack" to the cart from the inventory page
    Then I should see a Remove button for "Sauce Labs Backpack"
    Then the navigation menu is displayed
    When I open the navigation menu
    And the navigation menu links are displayed
    When I select Reset App State from the navigation menu
    Then I should be on the "inventory" page

  @TC_LLF_013A
  Scenario: Logout from navigation menu
    When I open the navigation menu
    Then the navigation menu is displayed
    When I select Logout from the navigation menu
    Then I should be on the "login" page

  @TC_LLF_013B
  Scenario: About link opens Sauce Labs site
    When I open the navigation menu
    Then the navigation menu is displayed
    When I select About from the navigation menu

  @TC_LLF_013C
  Scenario: All Items link keeps user on inventory
    When I open the navigation menu
    Then the navigation menu is displayed
    When I select All Items from the navigation menu
    Then I should be on the "inventory" page
