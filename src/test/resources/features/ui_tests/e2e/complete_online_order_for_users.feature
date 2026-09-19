@e2e @regression @all
Feature: Complete online order for users
  As a user
  I want accepted users to complete checkout
  So that the purchase flow is validated across supported users

  Background:
    Given I navigate to "/"
    Then I should be on the "login" page

  @TC_E2E_USERS_001 @checkout
  Scenario Outline: Accepted user completes an online order
    When I login with username "<username>" and password "<password>"
    Then I should be on the "inventory" page
    And I close the google password manager popup
    And I should see 6 product items
    When I add "Sauce Labs Backpack" to the cart from the inventory page
    And I add "Sauce Labs Bike Light" to the cart from the inventory page
    And I open the shopping cart
    Then I should be on the "cart" page
    And the cart page is displayed
    And I should see 2 cart items
    When I checkout from the cart
    Then I should be on the "checkout your information" page
    When I enter checkout information first name "John" last name "Doe" postal code "12345"
    And I continue from the checkout your information page
    Then I should be on the "checkout overview" page
    And the checkout overview page is displayed
    And I should see 2 checkout overview items
    When I finish from the checkout overview page
    Then I should be on the "checkout complete" page
    And the checkout complete page is displayed
    And I should see checkout complete header "Thank you for your order!" and text "Your order has been dispatched, and will arrive just as fast as the pony can get there!"
    When I go back to products from the checkout complete page
    Then I should be on the "inventory" page

    Examples:
      | username                | password     |
      | standard_user           | secret_sauce |
      | performance_glitch_user | secret_sauce |
      | visual_user             | secret_sauce |

  @TC_E2E_USERS_002 @checkout
  Scenario Outline: Other accepted users complete an online order
    When I login with username "<username>" and password "<password>"
    Then I should be on the "inventory" page
    And I close the google password manager popup
    When I add "Sauce Labs Backpack" to the cart from the inventory page
    And I open the shopping cart
    Then I should be on the "cart" page
    When I checkout from the cart
    Then I should be on the "checkout your information" page
    When I enter checkout information first name "Alex" last name "Taylor" postal code "90210"
    And I continue from the checkout your information page
    Then I should be on the "checkout overview" page
    When I finish from the checkout overview page
    Then I should be on the "checkout complete" page

    Examples:
      | username     | password     |
      | problem_user | secret_sauce |
      | error_user   | secret_sauce |
