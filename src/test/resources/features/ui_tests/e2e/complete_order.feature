@e2e @regression @all
Feature: Complete online order
  As a user
  I want to complete an online order
  So that the full purchase journey is validated with generic page steps

  @TC_E2E_003 @checkout @logout
  Scenario: Complete checkout, go back home and logout
    Given I navigate to "https://www.saucedemo.com"
    Then I should be on the "login" page
    When I login as an accepted "standard" user
    Then I should be on the "inventory" page
    And I close the google password manager popup
    And I should see 6 product items
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


  @TC_E2E_004 @checkout @e2e_complete_order
  Scenario: Complete online purchase
    Given I navigate to "/"
    Then I should be on the "login" page
    And I input accepted username "standard_user" and password for all users
    When I "login" from the "login" page
    Then I should be on the "inventory" page
    And I close the google password manager popup
    And cart badge icon should not be visible
    And I should see 6 product items
    When I add "Sauce Labs Backpack" to the cart
    And cart badge count should be 1
    Then I should see a Remove button for "Sauce Labs Backpack"
    When I remove "Sauce Labs Backpack" from the cart
    Then I can see "Add to cart" for "Sauce Labs Backpack"
    And I add products:
      | Sauce Labs Backpack      |
      | Sauce Labs Fleece Jacket |
    And cart badge count should be 2
    When I tap "shopping cart"
    Then I should be on the "cart" page
    And cart badge count should be 2
    And I should see the item in the cart
      | name                     | quantity | price  |
      | Sauce Labs Backpack      | 1        | $29.99 |
      | Sauce Labs Fleece Jacket | 1        | $49.99 |
    And the page should contain
      | Sauce Labs Backpack |
      | 29.99               |
      | 1                   |
    And the table should contain
      | Sauce Labs Backpack      | 29.99 |
      | Sauce Labs Fleece Jacket | 49.99 |
    And I should see "Continue Shopping"
    And I should see "Checkout"
    And I should see the following displayed on the page:
      | Continue Shopping   |
      | Checkout            |
      | Remove              |
      | Sauce Labs Backpack |
    When I "checkout" from the "cart" page
    Then I should be on the "checkout your information" page
    When I enter checkout information:
      | first name | last name | postal code |
      | John       | Doe       | 12345       |
    And I "continue" from the "checkout your information" page
    Then I should be on the "checkout overview" page
    And I should see the following checkout overview product details
      | name                     | quantity | price  |
      | Sauce Labs Backpack      | 1        | $29.99 |
      | Sauce Labs Fleece Jacket | 1        | $49.99 |
    Then I should be on the "checkout overview" page
    And the checkout overview headers are displayed
    And the page header should be "Checkout: Overview"
    And I should see 2 checkout overview items
    And I should see the following checkout overview product details
      | name                     | quantity | price  |
      | Sauce Labs Backpack      | 1        | $29.99 |
      | Sauce Labs Fleece Jacket | 1        | $49.99 |
    And I should see checkout summary
      | payment  | SauceCard #31337            |
      | shipping | Free Pony Express Delivery! |
      | subtotal | $79.98                      |
      | tax      | $6.40                       |
      | total    | $86.38                      |
    When I "finish" from the "checkout overview" page
    Then I should be on the "checkout complete" page
    Then the page header should be "Checkout: Complete!"
    And I can see "Thank you for your order!"
    And I should see "Your order has been dispatched"
    And I should see "Back Home"
    When I "back Home" from the "checkout complete" page
    Then I should be on the "inventory" page
    When I "logout" from the "inventory" page
    Then I should be on the "login" page


  @TC_E2E_005
  @checkout
  @e2e
  Scenario: Customer completes an online purchase

    Given I am logged in as "standard_user"
    And my cart is empty

    When I add the following products to my cart:
      | Sauce Labs Backpack      |
      | Sauce Labs Fleece Jacket |

    Then the cart badge should display "2"
    When I open the shopping cart

    Then the cart should contain:
      | name                     | quantity | price  |
      | Sauce Labs Backpack      | 1        | $29.99 |
      | Sauce Labs Fleece Jacket | 1        | $49.99 |

    When I proceed to checkout

    And I complete the checkout information:
      | first name | last name | postal code |
      | John       | Doe       | 12345       |

    Then the checkout summary should display:
      | payment  | SauceCard #31337            |
      | shipping | Free Pony Express Delivery! |
      | subtotal | $79.98                      |
      | tax      | $6.40                       |
      | total    | $86.38                      |

    When I place the order

    Then the order should be completed successfully
    And I should see "Your order has been dispatched" message
    When I return to the inventory page
    Then I should be on the "inventory" page
