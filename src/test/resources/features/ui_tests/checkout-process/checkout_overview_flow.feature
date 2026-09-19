@CheckoutProcess @regression @all
Feature: checkout overview flow
  As a customer
  I want to review checkout overview details
  So that I can verify the order before finishing

  Background:
    Given I navigate to "/"
    Then I should be on the "login" page
    When I login as an accepted "standard" user
    Then I should be on the "inventory" page
    And I close the google password manager popup
    When I add "Sauce Labs Backpack" to the cart from the inventory page
    And I add "Sauce Labs Bike Light" to the cart from the inventory page
    And I open the shopping cart
    Then I should be on the "cart" page
    And I should see the following cart product details
      | name                  | quantity | price  |
      | Sauce Labs Bike Light | 1        | $9.99  |
      | Sauce Labs Backpack   | 1        | $29.99 |
    When I checkout from the cart page
    Then I should be on the "checkout your information" page
    When I enter checkout information first name "John" last name "Doe" postal code "12345"
    And I continue from the checkout your information page
    Then I should be redirected to the "checkout overview" page
    And the checkout overview page is displayed

  @TC_CO_007
  Scenario: Verify checkout overview product details
    Then the checkout overview headers are displayed
    And I should see 2 checkout overview items
    And I should see the following checkout overview product details
      | name                  | quantity | price  |
      | Sauce Labs Bike Light | 1        | $9.99  |
      | Sauce Labs Backpack   | 1        | $29.99 |

  @TC_CO_008
  Scenario: Verify checkout overview summary
    Then I should see checkout summary payment "SauceCard #31337" shipping "Free Pony Express Delivery!" subtotal "Item total: $39.98" tax "Tax: $3.20" total "Total: $43.18"

  @TC_CO_009
  Scenario: Finish checkout from overview
    When I finish from the checkout overview page
    Then I should be redirected to the "checkout complete" page
    And the checkout complete page is displayed

  @TC_CO_010
  Scenario: Cancel checkout from overview
    When I cancel from the checkout overview page
    Then I should be redirected to the "inventory" page
