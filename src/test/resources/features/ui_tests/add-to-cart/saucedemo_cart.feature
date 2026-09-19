@@ui @cart @regression
Feature: cart
  As a logged-in user
  I want to review products in my cart
  So that I can continue shopping, remove items, or checkout

  Background:
    Given I navigate to "/"
    Then I should be on the "login" page
    When I login as an accepted "standard" user
    Then I should be on the "inventory" page
    And I close the google password manager popup
    When I add "Sauce Labs Fleece Jacket" to the cart from the inventory page
    And I add "Sauce Labs Bike Light" to the cart from the inventory page
    And I open the shopping cart
    Then I should be on the "cart" page
    And the cart page is displayed

  @TC_SD_CART_001 @smoke
  Scenario: Cart page displays selected products with quantity and price
    Then the cart headers are displayed
    And I should see 2 cart items
    And I should see the following cart products
      | Sauce Labs Fleece Jacket |
      | Sauce Labs Bike Light    |
    And I should see the following cart product details
      | name                     | quantity | price  |
      | Sauce Labs Fleece Jacket | 1        | $49.99 |
      | Sauce Labs Bike Light    | 1        | $9.99  |
    And I should see a cart Remove button for "Sauce Labs Fleece Jacket"
    And I should see a cart Remove button for "Sauce Labs Bike Light"
    And the cart footer buttons are displayed

  @TC_SD_CART_002
  Scenario: User can remove a product from the cart
    When I remove "Sauce Labs Bike Light" from the cart
    Then I should not see "Sauce Labs Bike Light" in the cart
    And I should see 1 cart items

  @TC_SD_CART_003
  Scenario: User can continue shopping from the cart
    When I continue shopping from the cart
    Then I should be on the "inventory" page

  @TC_SD_CART_004
  Scenario: User can proceed to checkout from the cart
    When I checkout from the cart
    Then I should be on the "checkout your information" page
    And the checkout your information page is displayed
