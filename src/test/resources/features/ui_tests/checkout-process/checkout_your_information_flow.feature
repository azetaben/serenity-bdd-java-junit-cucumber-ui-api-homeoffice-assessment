@CheckoutProcess @regression @all
Feature: checkout your information flow
  As a regular user
  I want to enter checkout information
  So that I can proceed to the checkout overview page

  Background:
    Given I navigate to "/"
    Then I should be on the "login" page
    When I login as an accepted "standard" user
    Then I should be on the "inventory" page
    And I close the google password manager popup
    When I add "Sauce Labs Backpack" to the cart from the inventory page
    And I add "Sauce Labs Bike Light" to the cart from the inventory page
    And I open the shopping cart
    Then I should be on the "cart" page
    And the cart page is displayed
    And I should see 2 cart items
    When I checkout from the cart
    Then I should be on the "checkout your information" page
    And the checkout your information page is displayed

  @TC_CO_011
  Scenario: Enter valid checkout information
    When I enter checkout information first name "John" last name "Doe" postal code "12345"
    And I continue from the checkout your information page
    Then I should be on the "checkout overview" page
    And the checkout overview page is displayed

  @TC_CO_012
  Scenario Outline: Enter invalid checkout information
    When I enter checkout information first name "<first_name>" last name "<last_name>" postal code "<postal_code>"
    And I continue from the checkout your information page
    Then I should see checkout your information error "<expected_error>"

    Examples:
      | first_name | last_name | postal_code | expected_error                 |
      |            | Doe       | 12345       | Error: First Name is required  |
      | John       |           | 12345       | Error: Last Name is required   |
      | John       | Doe       |             | Error: Postal Code is required |

  @TC_CO_013
  Scenario: Cancel from checkout information
    When I cancel from the checkout your information page
    Then I should be on the "cart" page
