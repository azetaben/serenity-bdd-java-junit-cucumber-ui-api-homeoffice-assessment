@Add2Cart @regression @all @CartEdgeCases
Feature: add-to-cart edge cases
  As a customer
  I want cart item details to remain correct for different products
  So that product identity and price are reliable

  Background:
    Given I navigate to "/"
    Then I should be on the "login" page
    When I login as an accepted "standard" user
    Then I should be on the "inventory" page
    And I close the google password manager popup

  @TC_ATC_EDGE_001
  Scenario: Verify the most expensive product in the cart
    When I add "Sauce Labs Fleece Jacket" to the cart from the inventory page
    Then I should see a Remove button for "Sauce Labs Fleece Jacket"
    When I open the shopping cart
    Then I should be on the "cart" page
    And I should see the following cart product details
      | name                     | quantity | price  |
      | Sauce Labs Fleece Jacket | 1        | $49.99 |

  @TC_ATC_EDGE_002
  Scenario: Verify the cheapest product in the cart
    When I add "Sauce Labs Onesie" to the cart from the inventory page
    Then I should see a Remove button for "Sauce Labs Onesie"
    When I open the shopping cart
    Then I should be on the "cart" page
    And I should see the following cart product details
      | name              | quantity | price |
      | Sauce Labs Onesie | 1        | $7.99 |

  @TC_ATC_EDGE_003
  Scenario: Verify a punctuation-heavy product name in the cart
    When I add "Test.allTheThings() T-Shirt (Red)" to the cart from the inventory page
    Then I should see a Remove button for "Test.allTheThings() T-Shirt (Red)"
    When I open the shopping cart
    Then I should be on the "cart" page
    And I should see the following cart product details
      | name                              | quantity | price  |
      | Test.allTheThings() T-Shirt (Red) | 1        | $15.99 |

  @TC_ATC_EDGE_004
  Scenario: Verify two products with the same price remain distinct
    When I add "Sauce Labs Bolt T-Shirt" to the cart from the inventory page
    And I add "Test.allTheThings() T-Shirt (Red)" to the cart from the inventory page
    And I open the shopping cart
    Then I should be on the "cart" page
    And I should see the following cart product details
      | name                              | quantity | price  |
      | Sauce Labs Bolt T-Shirt           | 1        | $15.99 |
      | Test.allTheThings() T-Shirt (Red) | 1        | $15.99 |

  @TC_ATC_EDGE_005
  Scenario: Verify first and last catalog products together
    When I add "Sauce Labs Backpack" to the cart from the inventory page
    And I add "Test.allTheThings() T-Shirt (Red)" to the cart from the inventory page
    And I open the shopping cart
    Then I should be on the "cart" page
    And I should see the following cart product details
      | name                              | quantity | price  |
      | Sauce Labs Backpack               | 1        | $29.99 |
      | Test.allTheThings() T-Shirt (Red) | 1        | $15.99 |

  @TC_ATC_EDGE_006
  Scenario: Verify remaining cart details after removing one item
    When I add "Sauce Labs Backpack" to the cart from the inventory page
    And I add "Sauce Labs Fleece Jacket" to the cart from the inventory page
    And I add "Sauce Labs Bike Light" to the cart from the inventory page
    And I open the shopping cart
    Then I should be on the "cart" page
    When I remove "Sauce Labs Fleece Jacket" from the cart
    Then I should see 2 cart items
    And I should not see "Sauce Labs Fleece Jacket" in the cart
    And I should see the following cart product details
      | name                  | quantity | price  |
      | Sauce Labs Backpack   | 1        | $29.99 |
      | Sauce Labs Bike Light | 1        | $9.99  |

  @TC_ATC_EDGE_007
  Scenario: Cart details persist after continue shopping and returning
    When I add "Sauce Labs Backpack" to the cart from the inventory page
    And I add "Sauce Labs Onesie" to the cart from the inventory page
    And I open the shopping cart
    Then I should be on the "cart" page
    When I continue shopping from the cart
    Then I should be on the "inventory" page
    When I open the shopping cart
    Then I should see the following cart product details
      | name                | quantity | price  |
      | Sauce Labs Backpack | 1        | $29.99 |
      | Sauce Labs Onesie   | 1        | $7.99  |

  @TC_ATC_EDGE_008
  Scenario: Verify all available products in the cart
    When I add "Sauce Labs Backpack" to the cart from the inventory page
    And I add "Sauce Labs Bike Light" to the cart from the inventory page
    And I add "Sauce Labs Bolt T-Shirt" to the cart from the inventory page
    And I add "Sauce Labs Fleece Jacket" to the cart from the inventory page
    And I add "Sauce Labs Onesie" to the cart from the inventory page
    And I add "Test.allTheThings() T-Shirt (Red)" to the cart from the inventory page
    And I open the shopping cart
    Then I should see 6 cart items
