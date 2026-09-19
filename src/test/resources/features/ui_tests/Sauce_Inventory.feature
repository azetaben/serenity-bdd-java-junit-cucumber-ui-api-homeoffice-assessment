@UI
Feature: Inventory
  As a user, I want to sort the inventory catalogue so I can find items by name or price.

  Background:
    Given I open the browser on the SauceDemo page
    When I enter the credentials "standard_user" and "secret_sauce"

  @UI3 @Smoke
  Scenario: Sort inventory by price low to high
    Given I am on the inventory page
    When I sort the inventory by "price low to high"
    Then the first inventory item should be "Sauce Labs Onesie" priced at "$7.99"
