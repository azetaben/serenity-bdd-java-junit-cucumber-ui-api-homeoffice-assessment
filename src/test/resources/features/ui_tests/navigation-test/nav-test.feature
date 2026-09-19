@navigation @regression @all
Feature: page navigation
  As a user
  I want to move through the main purchase pages
  So that navigation between application pages is verified

  Background:
    Given I navigate to "/"
    Then I should be on the "login" page
    When I login as an accepted "standard" user
    Then I should be on the "inventory" page
    And I close the google password manager popup

  @TC_VN_002_1
  Scenario: Navigate from inventory to checkout complete and logout
    When I add "Sauce Labs Backpack" to the cart from the inventory page
    And I open the shopping cart
    Then I should be on the "cart" page
    When I checkout from the cart
    Then I should be on the "checkout your information" page
    When I enter checkout information first name "John" last name "Doe" postal code "12345"
    And I continue from the checkout your information page
    Then I should be on the "checkout overview" page
    When I finish from the checkout overview page
    Then I should be on the "checkout complete" page
    When I go back to products from the checkout complete page
    Then I should be on the "inventory" page
    When I logout from the "inventory" page
    Then I should be on the "login" page

  @TC_VN_002_2
  Scenario: Navigate through inventory item and checkout pages
    When I open "Sauce Labs Bike Light" from the inventory page
    Then I should be on the "inventory item" page
    And the inventory item detail page is displayed
    When I go back to products from the inventory item detail page
    Then I should be on the "inventory" page
    When I add "Sauce Labs Bike Light" to the cart from the inventory page
    And I open the shopping cart
    Then I should be on the "cart" page
    When I checkout from the cart
    Then I should be on the "checkout your information" page
    When I enter checkout information first name "Jane" last name "Smith" postal code "SW1A 1AA"
    And I continue from the checkout your information page
    Then I should be on the "checkout overview" page
    When I finish from the checkout overview page
    Then I should be on the "checkout complete" page
