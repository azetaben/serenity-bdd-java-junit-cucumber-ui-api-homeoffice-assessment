@ui @general_steps_examples
Feature: Reusable step examples
  As a test engineer
  I want examples that use the current reusable steps
  So that feature authors can copy valid step patterns

  Background:
    Given I navigate to "/"
    Then I should be on the "login" page

  Scenario: Login, inventory, cart, and checkout example
    Then the login page is displayed
    When I login as an accepted "standard" user
    Then I should be on the "inventory" page
    And I close the google password manager popup
    And the inventory page is displayed
    And I should see 6 inventory items
    When I add "Sauce Labs Backpack" to the cart from the inventory page
    Then I should see a Remove button for "Sauce Labs Backpack"
    When I open the shopping cart
    Then I should be on the "cart" page
    And the cart page is displayed
    And I should see the following cart product details
      | name                | quantity | price  |
      | Sauce Labs Backpack | 1        | $29.99 |
    When I checkout from the cart
    Then I should be on the "checkout your information" page
    When I enter checkout information first name "Jane" last name "Smith" postal code "AB12 3CD"
    And I continue from the checkout your information page
    Then I should be on the "checkout overview" page
    And the checkout overview page is displayed

  Scenario: Login error assertion example
    When I login with username "" and password ""
    Then Login result should be "USERNAME_REQUIRED"
    And I should see login error "Epic sadface: Username is required"

  Scenario: Navigation and footer example
    When I login as an accepted "standard" user
    Then I should be on the "inventory" page
    When I open the navigation menu
    Then the navigation menu is displayed
    And the navigation menu links are displayed
    When I close the navigation menu
    Then the navigation menu is hidden
    And the footer is displayed
    And the footer social links are displayed
