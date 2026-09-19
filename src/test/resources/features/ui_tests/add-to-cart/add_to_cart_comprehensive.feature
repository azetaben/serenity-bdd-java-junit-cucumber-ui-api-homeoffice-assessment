@Add2Cart @regression @all @CartComprehensive
Feature: comprehensive cart operations
  As a standard user
  I want to add, remove, verify, and continue shopping
  So that I can manage my cart before checkout

  Background:
    Given I navigate to "/"
    Then I should be on the "login" page
    When I login as an accepted "standard" user
    Then I should be on the "inventory" page
    And I close the google password manager popup

  @TC_ATC_COMPREHENSIVE_001
  Scenario: Add multiple products, remove one, continue shopping, and add more
    When I add "Sauce Labs Backpack" to the cart from the inventory page
    And I add "Sauce Labs Bike Light" to the cart from the inventory page
    And I add "Sauce Labs Bolt T-Shirt" to the cart from the inventory page
    And I open the shopping cart
    Then I should be on the "cart" page
    And I should see the following cart product details
      | name                    | quantity | price  |
      | Sauce Labs Backpack     | 1        | $29.99 |
      | Sauce Labs Bike Light   | 1        | $9.99  |
      | Sauce Labs Bolt T-Shirt | 1        | $15.99 |
    When I remove "Sauce Labs Bike Light" from the cart
    Then I should not see "Sauce Labs Bike Light" in the cart
    And I should see 2 cart items
    When I continue shopping from the cart
    Then I should be on the "inventory" page
    When I add "Sauce Labs Fleece Jacket" to the cart from the inventory page
    And I add "Test.allTheThings() T-Shirt (Red)" to the cart from the inventory page
    And I open the shopping cart
    Then I should see 4 cart items
    And I should see the following cart product details
      | name                              | quantity | price  |
      | Sauce Labs Backpack               | 1        | $29.99 |
      | Sauce Labs Bolt T-Shirt           | 1        | $15.99 |
      | Sauce Labs Fleece Jacket          | 1        | $49.99 |
      | Test.allTheThings() T-Shirt (Red) | 1        | $15.99 |

  @TC_ATC_COMPREHENSIVE_002
  Scenario: Remove products and preserve remaining cart state
    When I add "Sauce Labs Backpack" to the cart from the inventory page
    And I add "Sauce Labs Bolt T-Shirt" to the cart from the inventory page
    And I add "Sauce Labs Bike Light" to the cart from the inventory page
    And I open the shopping cart
    Then I should be on the "cart" page
    When I remove "Sauce Labs Bike Light" from the cart
    Then I should see 2 cart items
    And I should see the following cart product details
      | name                    | quantity | price  |
      | Sauce Labs Backpack     | 1        | $29.99 |
      | Sauce Labs Bolt T-Shirt | 1        | $15.99 |
    When I continue shopping from the cart
    Then I should be on the "inventory" page

  @TC_ATC_COMPREHENSIVE_003
  Scenario: Add products across multiple cart visits
    When I add "Sauce Labs Backpack" to the cart from the inventory page
    And I open the shopping cart
    Then I should see 1 cart items
    When I continue shopping from the cart
    Then I should be on the "inventory" page
    When I add "Sauce Labs Bike Light" to the cart from the inventory page
    And I open the shopping cart
    Then I should see 2 cart items
    When I continue shopping from the cart
    Then I should be on the "inventory" page
    When I add "Sauce Labs Bolt T-Shirt" to the cart from the inventory page
    And I open the shopping cart
    Then I should see the following cart product details
      | name                    | quantity | price  |
      | Sauce Labs Backpack     | 1        | $29.99 |
      | Sauce Labs Bike Light   | 1        | $9.99  |
      | Sauce Labs Bolt T-Shirt | 1        | $15.99 |
