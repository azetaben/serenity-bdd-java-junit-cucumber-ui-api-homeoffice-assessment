@Add2Cart @ShippingCart @regression @all
Feature: shopping cart functionality
  As a user
  I want to add and remove items from the shopping cart
  So that I can manage selected products before checkout

  Background:
    Given I navigate to "/"
    Then I should be on the "login" page
    When I login as an accepted "standard" user
    Then I should be on the "inventory" page
    And I close the google password manager popup

  @TC_ATC_SHOPPING_CART_001
  Scenario Outline: Add and remove items through the shopping cart flow
    When I add "<initial_product>" to the cart from the inventory page
    And I add "<additional_product_1>" to the cart from the inventory page
    And I add "<additional_product_2>" to the cart from the inventory page
    And I add "<additional_product_3>" to the cart from the inventory page
    Then I should see a Remove button for "<initial_product>"
    And I should see a Remove button for "<additional_product_1>"
    And I should see a Remove button for "<additional_product_2>"
    And I should see a Remove button for "<additional_product_3>"
    When I open the shopping cart
    Then I should be on the "cart" page
    And the cart page is displayed
    And I should see 4 cart items
    When I remove "<removed_product_1>" from the cart
    And I remove "<removed_product_2>" from the cart
    Then I should see 2 cart items
    When I continue shopping from the cart
    Then I should be on the "inventory" page
    When I add "<continue_shopping_product>" to the cart from the inventory page
    And I open the shopping cart
    Then I should be on the "cart" page
    And I should see the following cart product details
      | name                   | quantity |
      | <final_cart_product_1> | 1        |
      | <final_cart_product_2> | 1        |
      | <final_cart_product_3> | 1        |

    Examples:
      | initial_product     | additional_product_1 | additional_product_2     | additional_product_3  | removed_product_1 | removed_product_2        | continue_shopping_product         | final_cart_product_1  | final_cart_product_2 | final_cart_product_3              |
      | Sauce Labs Backpack | Sauce Labs Onesie    | Sauce Labs Fleece Jacket | Sauce Labs Bike Light | Sauce Labs Onesie | Sauce Labs Fleece Jacket | Test.allTheThings() T-Shirt (Red) | Sauce Labs Bike Light | Sauce Labs Backpack  | Test.allTheThings() T-Shirt (Red) |
