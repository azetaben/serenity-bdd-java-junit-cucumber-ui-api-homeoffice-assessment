@performance @all
Feature: performance flow smoke checks
  As a test engineer
  I want common performance journeys to remain executable
  So that the feature suite tracks page flow health

  Background:
    Given I navigate to "/"
    Then I should be on the "login" page

  @TC_MC_013 @PageLoad
  Scenario: Login page is available
    Then the login page is displayed

  @TC_MC_014 @PageLoad @Interaction
  Scenario: Performance glitch user can reach inventory
    When I login with username "performance_glitch_user" and password "secret_sauce"
    Then Login result should be "SUCCESS"
    And I should be on the "inventory" page
    And I close the google password manager popup
    And the inventory page is displayed

  @TC_MC_015 @PageLoad
  Scenario: Standard user can complete the basic product flow
    When I login as an accepted "standard" user
    Then I should be on the "inventory" page
    When I add "Sauce Labs Backpack" to the cart from the inventory page
    And I open the shopping cart
    Then I should be on the "cart" page
    And the cart page is displayed
