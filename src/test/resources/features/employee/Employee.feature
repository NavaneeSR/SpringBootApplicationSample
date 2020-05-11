
Feature: Employee Service Validation with employee creation, updation and deletion

  Background:
    Given the user accessing base URI

  Scenario Outline: Validate the response message for creating an employee
    When the user making a POST call "/employee/createEmployee" with employee details <id>, "<employeeName>", "<firstName>", "<lastName>", "<email>", "<password>", "<phone>", <employeeStatus>
    Then the user should see the response code as 200
    Then validate the response for the employee details
    Examples:
      | id | employeeName | firstName | lastName | email           | password | phone      | employeeStatus |
      | 1  | BretTest     | Bret      | Test     | test1@gmail.com | pass123  | 1287457450 | 0              |

  Scenario: Validate the response message for creating an multiple employee records
    When the user making a POST call "/employee/createWithArray" request with array of employees detail
      | id | employeeName | firstName | lastName | email           | password | phone      | employeeStatus |
      | 1  | BretTest     | Bret      | Test     | test1@gmail.com | pass123  | 1287457450 | 0              |
    Then the user should see the response code as 200
    Then validate the response for the array of employees details

  Scenario: Validate the response message for creating an multiple employee records
    When the user making a POST call "/employee/createWithList" request with list of employees detail
      | id | employeeName | firstName | lastName | email           | password | phone      | employeeStatus |
      | 1  | BretTest     | Bret      | Test     | test1@gmail.com | pass123  | 1287457450 | 0              |
      | 2  | Bret1Test1   | Bret1     | Test1    | test2@gmail.com | pass123  | 1287457450 | 0              |
    Then the user should see the response code as 200
    Then validate the response for the list of employees details

  Scenario: Validate the response message when an employee is logged into system
    When the user making GET call "/employee/login" with "BretTest" and "pass123"
    Then the user should see the response code as 200
    Then validate the response message "Login successful" for employee logged into system

  Scenario: Validate the response message when an employee is logged out from system
    When the user making GET call "/employee/logout"
    Then the user should see the response code as 200
    Then validate the response message "Logout successful" for employee logged out from system

  Scenario: Validate the response message to get employee details by Name
    When the user making GET call "/employee/{employeeName}" employee by name "BretTest"
    Then the user should see the response code as 200
    Then validate the response for the employee details by name
      | id | employeeName | firstName | lastName | email           | password | phone      | employeeStatus |
      | 1  | BretTest     | Bret      | Test     | test1@gmail.com | pass123  | 1287457450 | 0              |