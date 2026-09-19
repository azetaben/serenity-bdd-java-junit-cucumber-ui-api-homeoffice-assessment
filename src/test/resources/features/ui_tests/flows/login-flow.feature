Feature: Login Flow

    Background:
        Given I am logged in as "standard_user"
        Then I should be on the "inventory" page

    @TC_LF_001
    Scenario: Should see 6 product items
        And I should see 6 product items
