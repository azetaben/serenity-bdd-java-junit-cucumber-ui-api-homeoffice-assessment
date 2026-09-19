@Add2Cart @regression @all
Feature: add-to-cart price verification
  As a regular user
  I want to verify cart product prices
  So that I can review price before purchase

  Background:
    Given I navigate to "/"
    Then I should be on the "login" page
    When I login as an accepted "standard" user
    Then I should be on the "inventory" page
    And I close the google password manager popup

  @TC_ATC_PRICE_001
  Scenario Outline: Add a product and verify product price in the cart
    When I add "<product_name>" to the cart from the inventory page
    Then I should see a Remove button for "<product_name>"
    When I open the shopping cart
    Then I should be on the "cart" page
    And the cart page is displayed
    And I should see the item in the cart
      | name           | quantity | price   |
      | <product_name> | <qty>    | <price> |
    When I logout from the "cart" page
    Then I should be on the "login" page

    Examples:
      | product_name        | price  | qty |
      | Sauce Labs Backpack | $29.99 | 1   |
