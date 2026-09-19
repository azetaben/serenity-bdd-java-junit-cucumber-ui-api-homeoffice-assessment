@CartFlow @regression @all
Feature: Login inventory cart flow
  As a standard SauceDemo user
  I want to add products from inventory to the cart
  So that I can verify the cart contains the selected products

  Background:
    Given I navigate to "/"
    Then I should be on the "login" page
    When I login as an accepted "standard" user
    Then I should be on the "inventory" page
    And the inventory page is displayed
    And I close the google password manager popup

  @TC_CART_FLOW_001
  Scenario: Login to inventory, add one product to cart, and verify it
    When I add "Sauce Labs Backpack" to the cart from the inventory page
    Then I should see a Remove button for "Sauce Labs Backpack"
    And cart badge count should be 1
    When I open the shopping cart
    Then I should be on the "cart" page
    And the cart page is displayed
    And I should see 1 cart items
    And cart contains exactly:
      | Sauce Labs Backpack |

  @TC_CART_FLOW_002
  Scenario: Login to inventory, add multiple products to cart, and verify them
    When I add products to the cart:
      | Sauce Labs Backpack   |
      | Sauce Labs Bike Light |
      | Sauce Labs Onesie     |
    Then cart badge count should be 3
    And I should see a Remove button for "Sauce Labs Backpack"
    And I should see a Remove button for "Sauce Labs Bike Light"
    And I should see a Remove button for "Sauce Labs Onesie"
    When I open the shopping cart
    Then I should be on the "cart" page
    And the cart page is displayed
    And I should see 3 cart items
    And cart contains exactly:
      | Sauce Labs Backpack   |
      | Sauce Labs Bike Light |
      | Sauce Labs Onesie     |

  @TC_CART_FLOW_003
  Scenario: Verify products in cart and check out
    When I add products to the cart:
      | Sauce Labs Backpack      |
      | Sauce Labs Bolt T-Shirt  |
      | Sauce Labs Fleece Jacket |
    And I open the shopping cart
    Then I should be on the "cart" page
    And the cart page is displayed
    And I should see 3 cart items
    And cart contains exactly:
      | Sauce Labs Backpack      |
      | Sauce Labs Bolt T-Shirt  |
      | Sauce Labs Fleece Jacket |
    When I checkout from the cart
    Then I should be on the "checkout information" page
    And the checkout information page is displayed

  @TC_CART_FLOW_004
  Scenario: Verify products in cart and continue shopping
    When I add products to the cart:
      | Sauce Labs Bike Light |
      | Sauce Labs Onesie     |
    And I open the shopping cart
    Then I should be on the "cart" page
    And the cart page is displayed
    And I should see 2 cart items
    And cart contains exactly:
      | Sauce Labs Bike Light |
      | Sauce Labs Onesie     |
    When I continue shopping from the cart
    Then I should be on the "inventory" page
    And the inventory page is displayed
    And cart badge count should be 2

  @TC_CART_FLOW_005
  Scenario: Verify a product price and quantity in the cart
    When I add "Sauce Labs Backpack" to the cart from the inventory page
    And I open the shopping cart
    Then I should be on the "cart" page
    And the cart page is displayed
    And I should see 1 cart items
    And cart product "Sauce Labs Backpack" has price "$29.99" and quantity 1

  @TC_CART_FLOW_006
  Scenario: Verify products prices and quantities in the cart
    When I add products to the cart:
      | Sauce Labs Backpack      |
      | Sauce Labs Bike Light    |
      | Sauce Labs Fleece Jacket |
    And I open the shopping cart
    Then I should be on the "cart" page
    And the cart page is displayed
    And I should see 3 cart items
    And I should see the following cart product details
      | name                     | quantity | price  |
      | Sauce Labs Backpack      | 1        | $29.99 |
      | Sauce Labs Bike Light    | 1        | $9.99  |
      | Sauce Labs Fleece Jacket | 1        | $49.99 |

  @TC_CART_FLOW_007
  Scenario: Add product, open product title, return to products, and verify cart details
    When I add "Sauce Labs Backpack" to the cart from the inventory page
    Then the cart badge count should be 1
    When I open "Sauce Labs Backpack" from the inventory page
    Then I should be on the "inventory item" page
    And the inventory item detail page is displayed
    And I should see inventory item details name "Sauce Labs Backpack" description "carry.allTheThings() with the sleek, streamlined Sly Pack that melds uncompromising style with unequaled laptop and tablet protection." price "$29.99"
    And I should see the inventory item Remove button
    And the cart badge count should be 1
    When I go back to products from the inventory item page
    Then I should be on the "inventory" page
    And the inventory page is displayed
    And the cart badge count should be 1
    When I open the shopping cart
    Then I should be on the "cart" page
    And the cart page is displayed
    And I should see 1 cart items
    And cart contains exactly:
      | Sauce Labs Backpack |
    And cart product "Sauce Labs Backpack" has price "$29.99" and quantity 1