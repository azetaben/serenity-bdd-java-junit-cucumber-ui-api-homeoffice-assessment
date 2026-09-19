@@ui @inventory_item @regression
Feature: inventory item detail
  As a logged-in user
  I want to view product details from the inventory page
  So that I can review an item before buying it

  Background:
    Given I navigate to "/"
    Then I should be on the "login" page
    When I login as an accepted "standard" user
    Then I should be on the "inventory" page
    And I close the google password manager popup

  @TC_SD_ITEM_001 @smoke
  Scenario: Inventory item detail page displays product information
    When I open "Sauce Labs Bike Light" from the inventory page
    Then I should be on the "inventory item" page
    And the inventory item detail page is displayed
    And I should see inventory item image for "Sauce Labs Bike Light"
    And I should see inventory item details name "Sauce Labs Bike Light" description "A red light isn't the desired state in testing but it sure helps when riding your bike at night. Water-resistant with 3 lighting modes, 1 AAA battery included." price "$9.99"
    And I should see the inventory item Add to cart button

  @TC_SD_ITEM_002
  Scenario: User can add and remove product from inventory item page
    When I open "Sauce Labs Bike Light" from the inventory page
    And I add the product to cart from the inventory item page
    Then I should see the inventory item Remove button
    When I remove the product from the inventory item page
    Then I should see the inventory item Add to cart button

  @TC_SD_ITEM_003
  Scenario: User can return to products from inventory item page
    When I open "Sauce Labs Bike Light" from the inventory page
    And I go back to products from the inventory item page
    Then I should be on the "inventory" page
