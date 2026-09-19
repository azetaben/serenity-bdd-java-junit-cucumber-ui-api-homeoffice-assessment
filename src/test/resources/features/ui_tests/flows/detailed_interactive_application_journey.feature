@DetailedE2E @e2e @regression @all
Feature: Detailed interactive SauceDemo application journey

  @TC_E2E_DETAILED_001
  Scenario: Complete an order after interacting with each application page
    Given I navigate to "/"
    Then I should be on the "login" page
    And the page title should be "Swag Labs"
    And the page URL should be "https://www.saucedemo.com/"
    And the login form should be present and visible
    And the accepted usernames are displayed
      | standard_user                |
      | locked_out_user              |
      | problem_user                 |
      | performance_glitch_user      |
      | error_user                   |
      | visual_user                  |
    And the password for all users is "secret_sauce"
    When I login as an accepted "standard" user
    Then I should be on the "inventory" page
    And I close the google password manager popup
    And the inventory page is displayed
    And the header logo is displayed
    And the product sort dropdown is displayed
    And the product sort options are displayed
    When I open the navigation menu
    Then the navigation menu is displayed
    And the navigation menu links are displayed
    When I close the navigation menu
    Then the navigation menu is hidden
    When I sort products by "Price (low to high)"
    Then the selected sort option should be "Price (low to high)"
    And the products should be sorted by "Price (low to high)"
    And I should see 6 inventory items
    And I should see the following inventory products
      | Sauce Labs Onesie               |
      | Sauce Labs Bike Light           |
      | Sauce Labs Bolt T-Shirt         |
      | Test.allTheThings() T-Shirt (Red) |
      | Sauce Labs Backpack             |
      | Sauce Labs Fleece Jacket        |
    And I should see inventory images for:
      | Sauce Labs Backpack             |
      | Sauce Labs Bike Light           |
      | Sauce Labs Bolt T-Shirt         |
      | Sauce Labs Fleece Jacket        |
      | Sauce Labs Onesie               |
      | Test.allTheThings() T-Shirt (Red) |
    And I should see Add to cart buttons for:
      | Sauce Labs Backpack             |
      | Sauce Labs Bike Light           |
      | Sauce Labs Bolt T-Shirt         |
      | Sauce Labs Fleece Jacket        |
      | Sauce Labs Onesie               |
      | Test.allTheThings() T-Shirt (Red) |
    And the footer is displayed
    And the footer social links are displayed
    When I open "Sauce Labs Bike Light" from the inventory page
    Then I should be on the "inventory item" page
    And the inventory item detail page is displayed
    And I should see inventory item details name "Sauce Labs Bike Light" description "A red light isn't the desired state in testing but it sure helps when riding your bike at night. Water-resistant with 3 lighting modes, 1 AAA battery included." price "$9.99"
    And I should see inventory item image for "Sauce Labs Bike Light"
    And I should see the inventory item Add to cart button
    When I add the product to cart from the inventory item page
    Then I should see the inventory item Remove button
    And the cart badge count should be 1
    When I remove the product from the inventory item page
    Then I should see the inventory item Add to cart button
    And cart badge icon should not be visible
    When I add the product to cart from the inventory item page
    Then the cart badge count should be 1
    When I go back to products from the inventory item page
    Then I should be on the "inventory" page
    And the inventory page is displayed
    When I add products to the cart:
      | Sauce Labs Backpack |
      | Sauce Labs Onesie   |
    Then the cart badge count should be 3
    And I should see a Remove button for "Sauce Labs Backpack"
    And I should see a Remove button for "Sauce Labs Bike Light"
    And I should see a Remove button for "Sauce Labs Onesie"
    When I open the shopping cart
    Then I should be on the "cart" page
    And the cart page is displayed
    And the cart headers are displayed
    And the cart footer buttons are displayed
    And I should see 3 cart items
    And I should see the following cart products
      | Sauce Labs Backpack   |
      | Sauce Labs Bike Light |
      | Sauce Labs Onesie     |
    And I should see the following cart product details
      | name                  | quantity | price  |
      | Sauce Labs Backpack   | 1        | $29.99 |
      | Sauce Labs Bike Light | 1        | $9.99  |
      | Sauce Labs Onesie     | 1        | $7.99  |
    And cart product "Sauce Labs Bike Light" has price "$9.99" and quantity 1
    When I remove "Sauce Labs Onesie" from the cart
    Then I should see 2 cart items
    And I should not see "Sauce Labs Onesie" in the cart
    When I continue shopping from the cart
    Then I should be on the "inventory" page
    And the inventory page is displayed
    And the cart badge count should be 2
    When I open the shopping cart
    Then the cart page is displayed
    And I should see 2 cart items
    When I checkout from the cart
    Then I should be on the "checkout information" page
    And the checkout your information page is displayed
    And the fields should be blank
    And the fields should be editable
    When I enter checkout information first name "Jane" last name "Smith" postal code "SW1A 1AA"
    And I continue from the checkout your information page
    Then I should be on the "checkout overview" page
    And the checkout overview page is displayed
    And the checkout overview headers are displayed
    And I should see 2 checkout overview items
    And I should see the following checkout overview products
      | Sauce Labs Backpack   |
      | Sauce Labs Bike Light |
    And I should see the following checkout overview product details
      | name                  | quantity | price  |
      | Sauce Labs Backpack   | 1        | $29.99 |
      | Sauce Labs Bike Light | 1        | $9.99  |
    And I should see checkout summary payment "SauceCard #31337" shipping "Free Pony Express Delivery!" subtotal "Item total: $39.98" tax "Tax: $3.20" total "Total: $43.18"
    When I finish from the checkout overview page
    Then I should be on the "checkout complete" page
    And the checkout complete page is displayed
    And I should see checkout complete header "Thank you for your order!" and text "Your order has been dispatched, and will arrive just as fast as the pony can get there!"
    And the order should be completed successfully
    And I should see a confirmation message
    And I should see the Generate PDF order button
    And I download the PDF order to file-download
    When I go back to products from the checkout complete page
    Then I should be on the "inventory" page
    And the inventory page is displayed
    When I open the navigation menu
    And I select Logout from the navigation menu
    Then I should be on the "login" page
    And the login form should be present and visible
