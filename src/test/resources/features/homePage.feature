Feature: HomePage feature
  This feature deals with all components of the home page of the application

  Scenario: Verify the home page of the application
    Given An unauthorized user navigates to the home page
    When Navigates the Amazon home page
    Then A user should see the Password assistance page