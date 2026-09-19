@Add2Cart @Persistence @Edges
Feature: cart lifecycle
  As a customer
  I want cart items to persist across page navigation
  So that cart state remains reliable during shopping

  Background:
    Given I navigate to "/"
    Then I should be on the "login" page
    When I login as an accepted "standard" user
    Then I should be on the "inventory" page
    And I close the google password manager popup

  @edge_cart_continue_shopping
  Scenario: Continue shopping from cart maintains state
    When I add "Sauce Labs Backpack" to the cart from the inventory page
    And I open the shopping cart
    Then I should be on the "cart" page
    And I should see 1 cart items
    When I continue shopping from the cart
    Then I should be on the "inventory" page
    And I should see a Remove button for "Sauce Labs Backpack"
    When I open the shopping cart
    Then I should see the following cart products
      | Sauce Labs Backpack |

  @edge_cart_all_items
  Scenario: Adding all available items to cart
    When I add "Sauce Labs Backpack" to the cart from the inventory page
    And I add "Sauce Labs Bike Light" to the cart from the inventory page
    And I add "Sauce Labs Bolt T-Shirt" to the cart from the inventory page
    And I add "Sauce Labs Fleece Jacket" to the cart from the inventory page
    And I add "Sauce Labs Onesie" to the cart from the inventory page
    And I add "Test.allTheThings() T-Shirt (Red)" to the cart from the inventory page
    And I open the shopping cart
    Then I should be on the "cart" page
    And I should see 6 cart items

  @edge_cart_empty_checkout
  Scenario: Checkout can be opened from an empty cart
    When I open the shopping cart
    Then I should be on the "cart" page
    And the cart page is displayed
    When I checkout from the cart
    Then I should be on the "checkout your information" page
    And the checkout your information page is displayed

  @edge_cart_remove_from_cart_page
  Scenario: Remove item from cart page updates visible items
    When I add "Sauce Labs Backpack" to the cart from the inventory page
    And I add "Sauce Labs Bike Light" to the cart from the inventory page
    And I open the shopping cart
    Then I should be on the "cart" page
    When I remove "Sauce Labs Backpack" from the cart
    Then I should see 1 cart items
    And I should not see "Sauce Labs Backpack" in the cart
    And I should see the following cart products
      | Sauce Labs Bike Light |

  @edge_cart_user_variants
  Scenario Outline: Accepted users can add an item to the cart
    When I logout from the "inventory" page
    Then I should be on the "login" page
    When I login with username "<username>" and password "secret_sauce"
    Then Login result should be "<result>"
    When I add "Sauce Labs Backpack" to the cart from the inventory page
    Then I should see a Remove button for "Sauce Labs Backpack"

    Examples:
      | username                | result  |
      | standard_user           | SUCCESS |
      | performance_glitch_user | SUCCESS |
