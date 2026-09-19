@login_logout @navigation @regression @all
Feature: navigation menu links
  As a user
  I want the navigation menu links to work
  So that I can move through the application

  Background:
    Given I navigate to "/"
    Then I should be on the "login" page
    When I login as an accepted "standard" user
    Then I should be on the "inventory" page
    And I close the google password manager popup
    When I open the navigation menu
    And the navigation menu links are displayed
    And I should see the following links:
      | All Items       |
      | About           |
      | Logout          |
      | Reset App State |
    And I close the navigation menu

  @TC_LLF_014
  Scenario: All Items link is clickable
    When I open the navigation menu
    When I select All Items from the navigation menu
    Then I should be on the "inventory" page

  @TC_LLF_016
  Scenario: Logout link is clickable
    When I select Logout from the navigation menu
    Then I should be on the "login" page

  @TC_LLF_017
  Scenario: Reset App State link is clickable
    When I select Reset App State from the navigation menu
    Then I should be on the "inventory" page

  @TC_LLF_015
  Scenario: About link is clickable
    When I select About from the navigation menu
