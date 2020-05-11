Feature: Bag functionalities


  Scenario: Putting one thing in the bag
    Given the user accessing base URI
    When the user send a get request '/api/employee/'
    Then the user should see the response code as 200
