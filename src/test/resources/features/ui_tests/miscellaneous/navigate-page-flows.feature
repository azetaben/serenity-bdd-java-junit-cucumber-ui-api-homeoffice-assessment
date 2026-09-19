@miscellaneous @navigation @regression @all
Feature: page flow navigation
  As a user
  I want reusable page flow examples
  So that common journeys are expressed with generic steps

  Background:
    Given I navigate to "/"
    Then I should be on the "login" page

  Scenario: Navigate to inventory page after login
    When I login as an accepted "standard" user
    Then I should be on the "inventory" page
    And I close the google password manager popup
    And the inventory page is displayed

  Scenario: Navigate to cart page after adding products
    When I login as an accepted "standard" user
    Then I should be on the "inventory" page
    When I add "Sauce Labs Backpack" to the cart from the inventory page
    And I add "Sauce Labs Bike Light" to the cart from the inventory page
    And I open the shopping cart
    Then I should be on the "cart" page
    And the cart page is displayed

  Scenario: Navigate to checkout information page
    When I login as an accepted "standard" user
    Then I should be on the "inventory" page
    When I add "Sauce Labs Backpack" to the cart from the inventory page
    And I open the shopping cart
    Then I should be on the "cart" page
    When I checkout from the cart
    Then I should be on the "checkout your information" page
    And the checkout your information page is displayed

  Scenario: Navigate to checkout overview page
    When I login as an accepted "standard" user
    Then I should be on the "inventory" page
    When I add "Sauce Labs Backpack" to the cart from the inventory page
    And I open the shopping cart
    Then I should be on the "cart" page
    When I checkout from the cart
    Then I should be on the "checkout your information" page
    When I enter checkout information first name "John" last name "Doe" postal code "12345"
    And I continue from the checkout your information page
    Then I should be on the "checkout overview" page
    And the checkout overview page is displayed

  Scenario: Navigate to checkout complete page
    When I login as an accepted "standard" user
    Then I should be on the "inventory" page
    When I add "Sauce Labs Backpack" to the cart from the inventory page
    And I open the shopping cart
    Then I should be on the "cart" page
    When I checkout from the cart
    Then I should be on the "checkout your information" page
    When I enter checkout information first name "John" last name "Doe" postal code "12345"
    And I continue from the checkout your information page
    Then I should be on the "checkout overview" page
    When I finish from the checkout overview page
    Then I should be on the "checkout complete" page
    And the checkout complete page is displayed
