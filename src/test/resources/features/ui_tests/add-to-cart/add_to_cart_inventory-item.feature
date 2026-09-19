@AddToCartInventoryItem @regression @all
Feature: Add to cart from inventory item detail page
  As a standard user
  I want to add products from their detail pages
  So that I can review more information before buying

  Background:
    Given I navigate to "/"
    Then I should be on the "login" page
    When I login as an accepted "standard" user
    Then I should be on the "inventory" page
    And I close the google password manager popup

  @TC_ATC_ITEM_001
  Scenario Outline: Add products to cart from detail pages
    When I open "<product_name_1>" from the inventory page
    Then I should be on the "inventory item" page
    And the inventory item detail page is displayed
    When I add the product to cart from the inventory item page
    Then I should see the inventory item Remove button
    When I go back to products from the inventory item page
    Then I should be on the "inventory" page
    When I open "<product_name_2>" from the inventory page
    Then I should be on the "inventory item" page
    When I add the product to cart from the inventory item page
    Then I should see the inventory item Remove button

    Examples:
      | product_name_1      | product_name_2        |
      | Sauce Labs Backpack | Sauce Labs Bike Light |

  @TC_ATC_ITEM_002
  Scenario Outline: Inventory and detail pages stay in sync after add and remove
    When I open "<product_name>" from the inventory page
    Then I should be on the "inventory item" page
    And the inventory item detail page is displayed
    And I should see inventory item image for "<product_name>"
    And I should see inventory item details name "<product_name>" description "<description>" price "<price>"
    And I should see the inventory item Add to cart button
    When I add the product to cart from the inventory item page
    Then I should see the inventory item Remove button
    When I remove the product from the inventory item page
    Then I should see the inventory item Add to cart button
    When I add the product to cart from the inventory item page
    Then I should see the inventory item Remove button
    When I go back to products from the inventory item page
    Then I should be on the "inventory" page
    And I should see a Remove button for "<product_name>"

    Examples:
      | product_name        | description                                                                                                                            | price  |
      | Sauce Labs Backpack | carry.allTheThings() with the sleek, streamlined Sly Pack that melds uncompromising style with unequaled laptop and tablet protection. | $29.99 |
