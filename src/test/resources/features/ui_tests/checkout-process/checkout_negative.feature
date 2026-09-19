@CheckoutProcess @negative @error_validation_tests @all
Feature: checkout information validation
  As a test engineer
  I want checkout information validation to use the shared checkout steps
  So that required-field behaviour is covered consistently

  Background:
    Given I navigate to "/"
    Then I should be on the "login" page
    When I login as an accepted "standard" user
    Then I should be on the "inventory" page
    And I close the google password manager popup
    When I add "Sauce Labs Backpack" to the cart from the inventory page
    And I open the shopping cart
    Then I should be on the "cart" page
    When I checkout from the cart
    Then I should be on the "checkout your information" page
    And the checkout your information page is displayed

  Scenario Outline: Checkout information validates required fields
    When I enter checkout information first name "<first_name>" last name "<last_name>" postal code "<postal_code>"
    And I continue from the checkout your information page
    Then I should see checkout your information error "<expected_error>"

    Examples:
      | first_name | last_name | postal_code | expected_error                 |
      |            | Smith     | SW1A 1AA    | Error: First Name is required  |
      | Alex       |           | SW1A 1AA    | Error: Last Name is required   |
      | Alex       | Smith     |             | Error: Postal Code is required |

  Scenario Outline: Valid checkout information proceeds to overview
    When I enter checkout information first name "<first_name>" last name "<last_name>" postal code "<postal_code>"
    And I continue from the checkout your information page
    Then I should be on the "checkout overview" page
    And the checkout overview page is displayed

    Examples:
      | first_name | last_name | postal_code |
      | John       | Doe       | 12345       |
      | Jane       | Smith     | SW1A 1AA    |
      | Alex       | Taylor    | 90210       |
