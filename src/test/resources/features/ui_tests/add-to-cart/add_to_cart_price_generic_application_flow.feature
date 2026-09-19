@Add2Cart @GenericPages @regression
Feature: add to cart using generic application page contracts
  As a test automation engineer
  I want the generic page contracts to support SauceDemo add-to-cart flows
  So that common step scripts can be reused across pages

  Background:
    Given I navigate to "/"
    Then I should be on the "login" page
    And the page element "login" should be visible
    And the button title should be "Login"
    And the fields should be editable
    And the fields should be blank
    And the accepted users list should contain "standard_user"
    When I logged in as "standard_user" with password "secret_sauce"
    Then I should be on the "inventory" page
    And I close the google password manager popup
    And the page header should be "Products"
    And the page title should be "Swag Labs"
    When I tap "reset app state"
    And I tap "close menu"


  @TC_GENERIC_ATC_001
  Scenario: Add one product to cart with the generic application flow page
    Then cart badge icon should not be visible
    When I add "Sauce Labs Backpack" to the cart
    Then cart item count should be 1
    When I tap "shopping cart"
    Then I should be on the "cart" page
    And the table should be below the heading
    And the page should contain
      | Sauce Labs Backpack |
      | 29.99               |
      | 1                   |
    And the table should contain
      | Sauce Labs Backpack | 29.99 |

  @TC_GENERIC_ATC_002
  Scenario: Complete checkout fields with generic input contracts
    Then I should be on the "inventory" page
    Then the page header should be "Products"
    And cart badge icon should not be visible
    And I can see "Add to cart" for "Sauce Labs Bike Light"
    When I add "Sauce Labs Bike Light" to the cart
    Then I can see "Remove" for "Sauce Labs Bike Light"
    Then cart badge count should be 1
    And I tap "shopping cart"
    Then I should be on the "cart" page
    And I should see the cart product details
      | product title         | quantity | price($) |
      | Sauce Labs Bike Light | 1        | 9.99     |
    Then I should be on the "cart" page
    Then the page header should be "Your Cart"
    And I tap "checkout"
    Then I should be on the "checkout your information" page
    When I input checkout information "Jane" "Tester" "SW1A 1AA"
    When I tap "continue"
    Then I should be on the "checkout overview" page
    And the page should contain
      | Sauce Labs Bike Light |
      | Payment Information   |
    When I tap "finish"
    Then I should be on the "checkout complete" page
    Then the page header should be "Checkout: Complete!"
    And the message below heading should be "Thank you for your order!"
