@CheckoutProcess @regression @all
Feature: checkout finish flow
  As a customer
  I want to finish checkout
  So that the order is completed and I can return to products

  @TC_CO_FINISH_001
  Scenario: Complete checkout and return to inventory
    Given I navigate to "/"
    Then I should be on the "login" page
    When I login as an accepted "standard" user
    Then I should be on the "inventory" page
    And I close the google password manager popup
    When I add "Sauce Labs Backpack" to the cart from the inventory page
    And I add "Sauce Labs Bike Light" to the cart from the inventory page
    And I add "Test.allTheThings() T-Shirt (Red)" to the cart from the inventory page
    And I open the shopping cart
    Then I should be on the "cart" page
    And the cart page is displayed
    And I should see 3 cart items
    And I should see the following cart products
      | Sauce Labs Bike Light             |
      | Sauce Labs Backpack               |
      | Test.allTheThings() T-Shirt (Red) |
    When I checkout from the cart
    Then I should be on the "checkout your information" page
    And the checkout your information page is displayed
    When I enter checkout information first name "John" last name "Doe" postal code "12345"
    And I continue from the checkout your information page
    Then I should be on the "checkout overview" page
    And the checkout overview page is displayed
    And I should see the following checkout overview product details
      | name                              | quantity | price  |
      | Sauce Labs Bike Light             | 1        | $9.99  |
      | Sauce Labs Backpack               | 1        | $29.99 |
      | Test.allTheThings() T-Shirt (Red) | 1        | $15.99 |
    And I should see checkout summary payment "SauceCard #31337" shipping "Free Pony Express Delivery!" subtotal "Item total: $55.97" tax "Tax: $4.48" total "Total: $60.45"
    When I finish from the checkout overview page
    Then I should be on the "checkout complete" page
    And the checkout complete page is displayed
    And I should see checkout complete header "Thank you for your order!" and text "Your order has been dispatched, and will arrive just as fast as the pony can get there!"
    When I go back to products from the checkout complete page
    Then I should be on the "inventory" page
