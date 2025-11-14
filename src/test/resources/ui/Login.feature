Feature: Sample feature to open google and validate title

  Scenario: Open google and validate title
    Given I open the browser
    When I navigate to "https://www.google.com"
    Then the page title should be "Google"

  Scenario: Form validation
    Given I open the browser
    When I navigate to "https://practice.expandtesting.com/register"
    And I enter "playwright2354652345" on element having id "username"
    And I enter "Test@1234" on element having id "password"
    And I enter "Test@1234" on element having id "confirmPassword"
    And I click on submit button
    Then I should see a success message




