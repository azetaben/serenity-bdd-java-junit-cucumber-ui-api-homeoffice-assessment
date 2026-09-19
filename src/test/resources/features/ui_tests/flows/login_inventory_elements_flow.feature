@ElementVisibilityFlow @regression @all
Feature: Login and inventory element visibility flow
  As a SauceDemo user
  I want to verify login and inventory page elements
  So that I know the main controls, links, buttons, and images are available

  @TC_ELEMENT_FLOW_001
  Scenario: Verify login page elements and inventory page elements after login
    Given I navigate to "/"
    Then I should be on the "login" page
    And the page title should be "Swag Labs"
    And the page URL should be "https://www.saucedemo.com/"
    And the login form should be present and visible
    And the accepted usernames are displayed
      | standard_user           |
      | locked_out_user         |
      | problem_user            |
      | performance_glitch_user |
      | error_user              |
      | visual_user             |
    And the password for all users is "secret_sauce"
    When I login as an accepted "standard" user
    Then I should be on the "inventory" page
    And the inventory page is displayed
    And the header logo is displayed
    And the product sort dropdown is displayed
    And the product sort options are displayed
    And I should see 6 inventory items
    And I should see the following inventory products
      | Sauce Labs Backpack               |
      | Sauce Labs Bike Light             |
      | Sauce Labs Bolt T-Shirt           |
      | Sauce Labs Fleece Jacket          |
      | Sauce Labs Onesie                 |
      | Test.allTheThings() T-Shirt (Red) |
    And I should see inventory images for:
      | Sauce Labs Backpack               |
      | Sauce Labs Bike Light             |
      | Sauce Labs Bolt T-Shirt           |
      | Sauce Labs Fleece Jacket          |
      | Sauce Labs Onesie                 |
      | Test.allTheThings() T-Shirt (Red) |
    And I should see Add to cart buttons for:
      | Sauce Labs Backpack               |
      | Sauce Labs Bike Light             |
      | Sauce Labs Bolt T-Shirt           |
      | Sauce Labs Fleece Jacket          |
      | Sauce Labs Onesie                 |
      | Test.allTheThings() T-Shirt (Red) |
    When I open the navigation menu
    Then the navigation menu is displayed
    And the navigation menu links are displayed
    When I close the navigation menu
    Then the navigation menu is hidden
    And the footer is displayed
    And the footer social links are displayed
