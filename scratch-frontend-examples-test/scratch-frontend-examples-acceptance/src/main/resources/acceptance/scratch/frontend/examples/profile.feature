Feature: Profile
  As a user I would like to view my profile to make sure my account details are correct

  Scenario: Can view the profile page
    Given I am signed in
    When I view my profile
    Then I should see my details