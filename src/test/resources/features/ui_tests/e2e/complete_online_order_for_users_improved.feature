@e2e @regression @all
Feature: Improved complete online order for users
  As a user
  I want the checkout flow expressed with reusable page objects
  So that the feature stays stable after framework refactors

  Background:
    Given I navigate to "/"
    Then I should be on the "login" page
    And the accepted usernames are displayed
      | standard_user           |
      | locked_out_user         |
      | problem_user            |
      | performance_glitch_user |
      | error_user              |
      | visual_user             |
    And the password for all users is "secret_sauce"

  @TC_E2E_IMPROVED_001 @checkout @happy-path
  Scenario Outline: Complete checkout with product validation
    When I login with username "<username>" and password "<password>"
    Then I should be on the "inventory" page
    And I close the google password manager popup
    And the inventory page is displayed
    And I should see 6 inventory items
    And the product sort dropdown is displayed
    When I sort products by "Price (high to low)"
    Then the selected sort option should be "Price (high to low)"
    And the products should be sorted by "Price (high to low)"
    When I open "Sauce Labs Backpack" from the inventory page
    Then I should be on the "inventory item" page
    And the inventory item detail page is displayed
    And I should see inventory item details name "Sauce Labs Backpack" description "carry.allTheThings() with the sleek, streamlined Sly Pack that melds uncompromising style with unequaled laptop and tablet protection." price "$29.99"
    When I go back to products from the inventory item page
    Then I should be on the "inventory" page
    When I add "Sauce Labs Backpack" to the cart from the inventory page
    And I add "Sauce Labs Bike Light" to the cart from the inventory page
    And I open the shopping cart
    Then I should be on the "cart" page
    And I should see the following cart product details
      | name                  | quantity | price  |
      | Sauce Labs Backpack   | 1        | $29.99 |
      | Sauce Labs Bike Light | 1        | $9.99  |
    When I checkout from the cart
    Then I should be on the "checkout your information" page
    And the checkout your information page is displayed
    When I enter checkout information first name "<firstName>" last name "<lastName>" postal code "<postalCode>"
    And I continue from the checkout your information page
    Then I should be on the "checkout overview" page
    And the checkout overview page is displayed
    And I should see the following checkout overview product details
      | name                  | quantity | price  |
      | Sauce Labs Backpack   | 1        | $29.99 |
      | Sauce Labs Bike Light | 1        | $9.99  |
    And I should see checkout summary payment "SauceCard #31337" shipping "Free Pony Express Delivery!" subtotal "Item total: $39.98" tax "Tax: $3.20" total "Total: $43.18"
    When I finish from the checkout overview page
    Then I should be on the "checkout complete" page
    And the checkout complete page is displayed
    And I should see checkout complete header "Thank you for your order!" and text "Your order has been dispatched, and will arrive just as fast as the pony can get there!"
    When I go back to products from the checkout complete page
    Then I should be on the "inventory" page

    Examples:
      | username      | password     | firstName | lastName | postalCode |
      | standard_user | secret_sauce | Jane      | Smith    | SW1A 1AA   |
      | visual_user   | secret_sauce | Alex      | Taylor   | 90210      |
