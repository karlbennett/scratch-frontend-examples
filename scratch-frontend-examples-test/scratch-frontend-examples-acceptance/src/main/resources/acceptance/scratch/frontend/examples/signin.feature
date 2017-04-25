Feature: Sign In
  As a user I would like to sign in so that I can use the signed in functionality of the web application.

  Scenario: Can sign in to an account
    Given I am an existing user
    When I sign in
    Then I should be on the homepage
    And I should see that I am signed in
    And I should be able to sign out