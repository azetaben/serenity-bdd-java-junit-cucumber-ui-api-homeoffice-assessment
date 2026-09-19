@e2e @regression @all
Feature: Complete online order
  As a user
  I want to complete an online order
  So that the full purchase journey is validated with generic page steps

  Background:
    Given I navigate to "/"
    Then I should be on the "login" page
    And the login form should be present and visible
    When I login as an accepted "standard" user
    Then I should be on the "inventory" page
    And I close the google password manager popup
    And I should see 6 product items


  @TC_E2E_001 @checkout @happy-path
  Scenario: Complete checkout and return to products
    And the product sort dropdown is displayed
    And the product sort options are displayed
    When I sort products by "Name (Z to A)"
    Then the selected sort option should be "Name (Z to A)"
    And the products should be sorted by "Name (Z to A)"
    When I sort products by "Price (low to high)"
    Then the selected sort option should be "Price (low to high)"
    And the products should be sorted by "Price (low to high)"
    When I add "Sauce Labs Backpack" to the cart from the inventory page
    And I add "Sauce Labs Bike Light" to the cart from the inventory page
    Then I should see a Remove button for "Sauce Labs Backpack"
    And I should see a Remove button for "Sauce Labs Bike Light"
    When I open the shopping cart
    Then I should be on the "cart" page
    And the cart page is displayed
    And the cart headers are displayed
    And I should see 2 cart items
    And I should see the following cart products
      | Sauce Labs Backpack   |
      | Sauce Labs Bike Light |
    When I continue shopping from the cart
    Then I should be on the "inventory" page
    When I add "Test.allTheThings() T-Shirt (Red)" to the cart from the inventory page
    And I open the shopping cart
    Then I should be on the "cart" page
    And I should see 3 cart items
    When I checkout from the cart
    Then I should be on the "checkout your information" page
    And the checkout your information page is displayed
    When I enter checkout information first name "Jane" last name "Smith" postal code "SW1A 1AA"
    And I continue from the checkout your information page
    Then I should be on the "checkout overview" page
    And the checkout overview page is displayed
    And I should see 3 checkout overview items
    And I should see the following checkout overview products
      | Sauce Labs Backpack               |
      | Sauce Labs Bike Light             |
      | Test.allTheThings() T-Shirt (Red) |
    When I finish from the checkout overview page
    Then I should be on the "checkout complete" page
    And I should see checkout complete header "Thank you for your order!" and text "Your order has been dispatched, and will arrive just as fast as the pony can get there!"
    When I go back to products from the checkout complete page
    Then I should be on the "inventory" page

  @TC_E2E_002 @checkout @logout
  Scenario: Complete checkout and logout from inventory
    When I add "Sauce Labs Backpack" to the cart from the inventory page
    And I open the shopping cart
    Then I should be on the "cart" page
    When I checkout from the cart page
    Then I should be on the "checkout your information" page
    When I enter checkout information first name "John" last name "Doe" postal code "12345"
    And I continue from the checkout your information page
    Then I should be on the "checkout overview" page
    When I finish from the checkout overview page
    Then I should be on the "checkout complete" page
    When I go back to products from the checkout complete page
    Then I should be on the "inventory" page
    When I logout from the "inventory" page
    Then I should be on the "login" page

  @TC_E2E_002 @checkout @logout
  Scenario: Complete checkout and go back home
    When I add "Sauce Labs Backpack" to the cart from the inventory page
    And I open the shopping cart
    Then I should be on the "cart" page
    When I "checkout" from the "cart" page
    Then I should be on the "checkout your information" page
    When I enter checkout information first name "John" last name "Doe" postal code "12345"
    And I "continue" from the "checkout your information" page
    Then I should be on the "checkout overview" page
    When I "finish" from the "checkout overview" page
    Then I should be on the "checkout complete" page
    When I "back Home" from the "checkout complete" page
    Then I should be on the "inventory" page
    When I "logout" from the "inventory" page
    Then I should be on the "login" page
