@login_logout @edgeUrlOuterHtml @all
Feature: login edge coverage
  As a user
  I want page transitions and login errors checked with generic page steps
  So that URL-specific checks are not duplicated

  Background:


  @TC_EDGE_URL_HTML_007
  Scenario Outline: Logout from "<page_name>" shows login page
    Given I navigate to "<base_url>"
    Then I should be on the "login" page
    When I login as an accepted "<user>" user
    Then I should be on the "<page_name>" page
    And I close the google password manager popup
    And I should see 6 product items
    When I "<action>" from the "<page_name>" page
    Then I should be on the "login" page
    Examples:
      | base_url | user     | page_name | action |
      | /        | standard | inventory | logout |

  @TC_EDGE_URL_HTML_008
  Scenario Outline: Logout from "<page_name>" via cart page
    Given I navigate to "<base_url>"
    Then I should be on the "login" page
    When I login as an accepted "<user>" user
    Then I should be on the "inventory" page
    And I close the google password manager popup
    And I should see 6 product items
    And I open the shopping cart
    Then I should be on the "<page_name>" page
    When I "<action>" from the "<page_name>" page
    Then I should be on the "login" page
    Examples:
      | base_url | user     | page_name | action |
      | /        | standard | cart      | logout |


  @TC_EDGE_URL_HTML_009
  Scenario Outline: Logout from "<page_name>" via "<page_name>" page
    Given I navigate to "<base_url>"
    Then I should be on the "login" page
    When I login as an accepted "<user>" user
    And I should see 6 product items
    And I close the google password manager popup
    And I open "Sauce Labs Backpack" from the inventory page
    Then I should be on the "<page_name>" page
    When I "<action>" from the "<page_name>" page
    Then I should be on the "login" page
    Examples:
      | base_url | user     | page_name      | action |
      | /        | standard | inventory item | logout |

  @TC_EDGE_URL_HTML_009
  Scenario Outline: Logout from "<page_name>" via checkout your information page
    Given I navigate to "<base_url>"
    Then I should be on the "login" page
    When I login as an accepted "<user>" user
    Then I should be on the "inventory" page
    And I close the google password manager popup
    And I open the shopping cart
    Then I should be on the "cart" page
    And I tap "checkout"
    Then I should be on the "<page_name>" page
    And I input checkout information "John" "Doe" "M1 7TT"
    When I "<action>" from the "<page_name>" page
    Then I should be on the "login" page
    Examples:
      | base_url | user     | page_name                 | action |
      | /        | standard | checkout your information | logout |

  @TC_EDGE_URL_HTML_010
  Scenario Outline: Logout from "<page_name>" via checkout overview page
    Given I navigate to "<base_url>"
    Then I should be on the "login" page
    When I login as an accepted "<user>" user
    Then I should be on the "inventory" page
    And I close the google password manager popup
    And I open the shopping cart
    Then I should be on the "cart" page
    And I tap "checkout"
    Then I should be on the "checkout your information" page
    And I input checkout information "John" "Doe" "M1 7TT"
    And I continue from the checkout your information page
    Then I should be on the "<page_name>" page
    When I "<action>" from the "<page_name>" page
    Then I should be on the "login" page
    Examples:
      | base_url | user     | page_name         | action |
      | /        | standard | checkout overview | logout |

  @TC_EDGE_URL_HTML_011
  Scenario Outline: Logout from "<page_name>" via checkout complete page
    Given I navigate to "<base_url>"
    Then I should be on the "login" page
    When I login as an accepted "<user>" user
    And I close the google password manager popup
    Then I should be on the "inventory" page
    And I close the google password manager popup
    And I open the shopping cart
    And I close the google password manager popup
    Then I should be on the "cart" page
    And I tap "checkout"
    When I enter checkout information first name "John" last name "Doe" postal code "12345"
    #And I continue from the checkout your information page
    And I "Continue" from the "checkout your information" page
    And I finish from the checkout overview page
    Then I should be on the "<page_name>" page
    When I "<action>" from the "<page_name>" page

    Then I should be on the "login" page
    Examples:
      | base_url | user     | page_name         | action |
      | /        | standard | checkout complete | logout |

  @TC_EDGE_URL_HTML_012
  Scenario Outline: Back Home from checkout complete page returns to inventory
    Given I navigate to "<base_url>"
    Then I should be on the "login" page
    When I login as an accepted "<user>" user
    Then I should be on the "inventory" page
    And I open the shopping cart
    Then I should be on the "cart" page
    And I tap "checkout"
    When I enter checkout information first name "John" last name "Doe" postal code "12345"
    And I "Continue" from the "checkout your information" page
    And I finish from the checkout overview page
    Then I should be on the "checkout complete" page
    When I "<action>" from the "checkout complete" page
    Then I should be on the "inventory" page
    Examples:
      | base_url | user     | action    |
      | /        | standard | Back Home |





