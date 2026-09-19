@navigation @checkout @regression @all
Feature: checkout navigation
  As a regular user
  I want to navigate through cart and checkout pages
  So that page transitions are covered with generic steps

  Background:
    Given I navigate to "/"
    Then I should be on the "login" page
    When I login as an accepted "standard" user
    Then I should be on the "inventory" page
    And I close the google password manager popup

  @TC_NT_01
  Scenario: Navigate from cart to checkout information and cancel
    When I add "Sauce Labs Backpack" to the cart from the inventory page
    And I open the shopping cart
    Then I should be on the "cart" page
    And the cart page is displayed
    When I checkout from the cart
    Then I should be on the "checkout your information" page
    And the checkout your information page is displayed
    When I cancel from the checkout your information page
    Then I should be on the "cart" page

  @TC_NT_02
  Scenario: Navigate through checkout complete and logout
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
    When I go back to products from the checkout complete page
    Then I should be on the "inventory" page
    When I logout from the "inventory" page
    Then I should be on the "login" page
