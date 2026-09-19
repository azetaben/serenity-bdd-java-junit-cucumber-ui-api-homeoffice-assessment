@LoginLogoutAllPages @login_logout @regression @all
Feature: Login and logout from all authenticated pages
  As an accepted SauceDemo user
  I want to logout from each authenticated page
  So that session termination is verified across the application

  Background:
    Given I navigate to "/"
    Then I should be on the "login" page
    When I login as an accepted "standard" user
    Then I should be on the "inventory" page
    And I close the google password manager popup

  @TC_LOGOUT_ALL_PAGES_001
  Scenario: Logout from inventory page
    And the inventory page is displayed
    When I logout from the "inventory" page
    Then I should be on the "login" page
    And the login page is displayed

  @TC_LOGOUT_ALL_PAGES_002
  Scenario: Logout from inventory item page
    When I open "Sauce Labs Backpack" from the inventory page
    Then I should be on the "inventory item" page
    And the inventory item detail page is displayed
    When I logout from the "inventory item" page
    Then I should be on the "login" page
    And the login page is displayed

  @TC_LOGOUT_ALL_PAGES_003
  Scenario: Logout from cart page
    When I add "Sauce Labs Backpack" to the cart
    And I open the shopping cart
    Then I should be on the "cart" page
    And the cart page is displayed
    When I logout from the "cart" page
    Then I should be on the "login" page
    And the login page is displayed

  @TC_LOGOUT_ALL_PAGES_004
  Scenario: Logout from checkout your information page
    When I add "Sauce Labs Backpack" to the cart
    And I open the shopping cart
    Then I should be on the "cart" page
    When I checkout from the cart
    Then I should be on the "checkout your information" page
    And the checkout your information page is displayed
    When I logout from the "checkout your information" page
    Then I should be on the "login" page
    And the login page is displayed

  @TC_LOGOUT_ALL_PAGES_005
  Scenario: Logout from checkout overview page
    When I add "Sauce Labs Backpack" to the cart
    And I open the shopping cart
    Then I should be on the "cart" page
    When I checkout from the cart
    Then I should be on the "checkout your information" page
    When I enter checkout information first name "John" last name "Doe" postal code "12345"
    And I continue from the checkout your information page
    Then I should be on the "checkout overview" page
    And the checkout overview page is displayed
    When I logout from the "checkout overview" page
    Then I should be on the "login" page
    And the login page is displayed

  @TC_LOGOUT_ALL_PAGES_006
  Scenario: Logout from checkout complete page
    When I add "Sauce Labs Backpack" to the cart
    And I open the shopping cart
    Then I should be on the "cart" page
    When I checkout from the cart
    Then I should be on the "checkout your information" page
    When I enter checkout information first name "John" last name "Doe" postal code "12345"
    And I continue from the checkout your information page
    Then I should be on the "checkout overview" page
    When I finish from the checkout overview page
    Then I should be on the "checkout complete" page
    And the checkout complete page is displayed
    When I "logout" from the "checkout complete" page
    Then I should be on the "login" page
    And the login page is displayed
