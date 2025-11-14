Feature: API checks

  Scenario: API Health Check
    And I make api call to "https://practice.expandtesting.com/notes/api/health-check" with method "GET"
    And I validate the response status code is 200
    And I validate the response body has "message" as "Notes API is Running"

  Scenario: API Post request
    Given I set api params as
      | email    | testwedfgvb@gmail.com |
      | name     | playwrightUser1987ytghbn     |
      | password | Test@12345                  |
    And I make api call to "https://practice.expandtesting.com/notes/api/users/register" with method "POST"
    And I validate the response status code is 201
    And I validate the response body has "message" as "User account created successfully"





