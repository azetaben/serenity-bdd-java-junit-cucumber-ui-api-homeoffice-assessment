@Add2Cart @regression @all
Feature: SauceDemo cart edge coverage

  Background:
    Given I navigate to "/"
    When I login as "standard_user" with password "secret_sauce"
    Then I should be on the "inventory" page
    And I close the google password manager popup

  Scenario Outline: 01 - Add one "<product>" and verify by title
    When I add a "<product>" to the cart
    And I open the shopping cart
    And I should see the following cart products
      | <product> |
    Examples:
      | product             |
      | Sauce Labs Backpack |

  Scenario: 02 - Add one product and verify cart badge count
    When I add a "Sauce Labs Backpack" to the cart
    And I should see 1 cart items
    Then cart badge is 1

  Scenario: 06 - Add one product and verify remove control is exposed
    When I add product "Sauce Labs Backpack"
    Then remove button is displayed for "Sauce Labs Backpack"

  Scenario: 07 - Add multiple products and verify exact titles
    When I add products:
      | Sauce Labs Backpack     |
      | Sauce Labs Bike Light   |
      | Sauce Labs Bolt T-Shirt |
    And I open the cart
    Then cart contains exactly:
      | Sauce Labs Backpack     |
      | Sauce Labs Bike Light   |
      | Sauce Labs Bolt T-Shirt |

  Scenario: 08 - Product image remains visible for the selected title
    Then inventory image is displayed for "Sauce Labs Backpack"

  Scenario: 09 - Verify product price and quantity in cart by title
    When I add product "Sauce Labs Backpack"
    And I open the cart
    Then cart product "Sauce Labs Backpack" has price "$29.99" and quantity 1
    Then cart product "Sauce Labs Backpack" has $ price "29.99" and quantity 1

  Scenario: 10 - Remove one product from cart by title
    When I add product "Sauce Labs Backpack"
    And I open the cart
    And I remove product "Sauce Labs Backpack" from cart
    Then cart does not contain product "Sauce Labs Backpack"

  Scenario: 11 - Remove from inventory restores add-to-cart button
    When I add product "Sauce Labs Backpack"
    And I remove product "Sauce Labs Backpack" from inventory
    Then add button is displayed for "Sauce Labs Backpack"

  Scenario: 12 - Remove one of multiple products and preserve the others
    When I add products:
      | Sauce Labs Backpack   |
      | Sauce Labs Bike Light |
    And I open the cart
    And I remove product "Sauce Labs Backpack" from cart
    Then cart contains exactly:
      | Sauce Labs Bike Light |

  Scenario: 13 - Removed product is absent and badge decrements
    When I add products:
      | Sauce Labs Backpack   |
      | Sauce Labs Bike Light |
    And I open the cart
    And I remove product "Sauce Labs Backpack" from cart
    Then cart does not contain product "Sauce Labs Backpack"
    And cart badge is "1"

  Scenario: 14 - Adding the same product twice is prevented by UI state
    When I add product "Sauce Labs Backpack"
    Then remove button is displayed for "Sauce Labs Backpack"
    And cart badge is "1"

  Scenario: 15 - Empty cart has no badge and no unexpected products
    When I open the cart
    Then cart badge is "0"
    And cart does not contain product "Sauce Labs Backpack"
