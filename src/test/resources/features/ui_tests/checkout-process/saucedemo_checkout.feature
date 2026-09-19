@@ui @checkout @regression
Feature: checkout
  As a customer
  I want to complete checkout with products in my cart
  So that I can place an order successfully

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
    When I checkout from the cart
    Then I should be on the "checkout your information" page
    And the checkout your information page is displayed

  @TC_SD_CHECKOUT_001 @smoke
  Scenario: Checkout your information page fields and buttons are displayed
    Then the checkout your information page is displayed

  @TC_SD_CHECKOUT_002
  Scenario Outline: Checkout your information page validates required fields
    When I enter checkout information first name "<first_name>" last name "<last_name>" postal code "<postal_code>"
    And I continue from the checkout your information page
    Then I should see checkout your information error "<expected_error>"

    Examples:
      | first_name | last_name | postal_code | expected_error                 |
      |            | Smith     | SW1A 1AA    | Error: First Name is required  |
      | Alex       |           | SW1A 1AA    | Error: Last Name is required   |
      | Alex       | Smith     |             | Error: Postal Code is required |

  @TC_SD_CHECKOUT_003
  Scenario: User can cancel from checkout your information page
    When I cancel from the checkout your information page
    Then I should be on the "cart" page
    And the cart page is displayed

  @TC_SD_CHECKOUT_004
  Scenario: Checkout overview displays cart items and summary information
    When I enter checkout information first name "Alex" last name "Smith" postal code "SW1A 1AA"
    And I continue from the checkout your information page
    Then I should be on the "checkout overview" page
    And the checkout overview page is displayed
    And the checkout overview headers are displayed
    And I should see 2 checkout overview items
    And I should see the following checkout overview products
      | Sauce Labs Fleece Jacket |
      | Sauce Labs Bike Light    |
    And I should see the following checkout overview product details
      | name                     | quantity | price  |
      | Sauce Labs Fleece Jacket | 1        | $49.99 |
      | Sauce Labs Bike Light    | 1        | $9.99  |
    And I should see checkout summary payment "SauceCard #31337" shipping "Free Pony Express Delivery!" subtotal "Item total: $59.980000000000004" tax "Tax: $4.80" total "Total: $64.78"

  @TC_SD_CHECKOUT_005 @smoke
  Scenario: User can complete checkout and return to products
    When I enter checkout information first name "Alex" last name "Smith" postal code "SW1A 1AA"
    And I continue from the checkout your information page
    Then I should be on the "checkout overview" page
    When I finish from the checkout overview page
    Then I should be on the "checkout complete" page
    And the checkout complete page is displayed
    And I should see checkout complete header "Thank you for your order!" and text "Your order has been dispatched, and will arrive just as fast as the pony can get there!"
    When I go back to products from the checkout complete page
    Then I should be on the "inventory" page

  @TC_SD_CHECKOUT_006
  Scenario: User can cancel from checkout overview
    When I enter checkout information first name "Alex" last name "Smith" postal code "SW1A 1AA"
    And I continue from the checkout your information page
    Then I should be on the "checkout overview" page
    When I cancel from the checkout overview page
    Then I should be on the "inventory" page
