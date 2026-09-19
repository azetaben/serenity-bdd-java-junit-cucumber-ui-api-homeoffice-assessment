@Add2Cart @regression @all
Feature: add to cart
  As a regular user
  I want to add products to the cart
  So that I can purchase them later

  Background:
    Given I navigate to "/"
    Then I should be on the "login" page
    When I login as an accepted "standard" user
    Then I should be on the "inventory" page
    And I close the google password manager popup

  @TC_ATC_001
  Scenario: Open the cart from inventory
    When I open the shopping cart
    Then I should be on the "cart" page
    And the cart page is displayed

  @TC_ATC_002
  Scenario: Continue shopping from cart returns to inventory
    When I open the shopping cart
    Then I should be on the "cart" page
    When I continue shopping from the cart
    Then I should be on the "inventory" page

  @TC_ATC_003
  Scenario Outline: Add and remove products before checkout
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
    And I should see 4 cart items
    When I remove "<removed_product_1>" from the cart
    And I remove "<removed_product_2>" from the cart
    Then I should see 2 cart items
    And I should not see "<removed_product_1>" in the cart
    And I should not see "<removed_product_2>" in the cart

    Examples:
      | initial_product     | additional_product_1 | additional_product_2     | additional_product_3  | removed_product_1 | removed_product_2        |
      | Sauce Labs Backpack | Sauce Labs Onesie    | Sauce Labs Fleece Jacket | Sauce Labs Bike Light | Sauce Labs Onesie | Sauce Labs Fleece Jacket |

  @TC_ATC_004
  Scenario Outline: Add one product and verify it in the cart
    When I add "<product_name>" to the cart from the inventory page
    Then I should see a Remove button for "<product_name>"
    When I open the shopping cart
    Then I should be on the "cart" page
    And I should see 1 cart items
    And I should see the following cart products
      | <product_name> |

    Examples:
      | product_name        |
      | Sauce Labs Backpack |
