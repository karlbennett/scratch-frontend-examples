Feature: Sign Out
  As a user I would like to be able sign out of the application so that I can secure my account.

  Scenario: Can sign out of an account
    Given I am signed in
    When I sign out
    Then I should be on the homepage
    And I should see that I am signed out