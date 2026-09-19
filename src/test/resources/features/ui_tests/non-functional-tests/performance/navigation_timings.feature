@performance @regression
Feature: navigation timing smoke checks
  As a test engineer
  I want login navigation to complete for supported users
  So that navigation timing coverage remains aligned to generic steps

  @TC_NT_001
  Scenario Outline: Login navigation reaches inventory for <username>
    Given I navigate to "/"
    Then I should be on the "login" page
    When I login with username "<username>" and password "<password>"
    Then Login result should be "SUCCESS"
    And I should be on the "inventory" page
    And I close the google password manager popup
    And the inventory page is displayed
    When I logout from the "inventory" page
    Then I should be on the "login" page

    Examples:
      | username                | password     |
      | performance_glitch_user | secret_sauce |
      | standard_user           | secret_sauce |
