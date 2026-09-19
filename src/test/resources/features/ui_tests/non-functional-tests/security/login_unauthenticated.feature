@security @login @invalidLogin @regression
Feature: unauthenticated route checks
  As a user
  I want protected routes to redirect to login
  So that application pages cannot be accessed anonymously

  @TC_LLF_031
  Scenario Outline: Block unauthenticated direct navigation to protected routes
    Given I navigate to "<protected_url>"
    Then I should be on the "login" page
    And I should see login error "<error_message>"

    Examples:
      | protected_url           | error_message                                                                       |
      | /inventory.html         | Epic sadface: You can only access '/inventory.html' when you are logged in.         |
      | /cart.html              | Epic sadface: You can only access '/cart.html' when you are logged in.              |
      | /checkout-step-one.html | Epic sadface: You can only access '/checkout-step-one.html' when you are logged in. |
      | /checkout-step-two.html | Epic sadface: You can only access '/checkout-step-two.html' when you are logged in. |
      | /checkout-complete.html | Epic sadface: You can only access '/checkout-complete.html' when you are logged in. |
