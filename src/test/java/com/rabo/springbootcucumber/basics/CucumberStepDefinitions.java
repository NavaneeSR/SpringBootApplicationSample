package com.rabo.springbootcucumber.basics;

import com.rabo.model.Employee;
import com.rabo.model.EmployeesModel;
import com.rabo.model.SuccessMessage;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CucumberStepDefinitions {

    private final Logger log = LoggerFactory.getLogger(CucumberStepDefinitions.class);
    private RequestSpecification requestSpecification;
    private Response response;
    List<Employee> employeeList = new ArrayList<>();
    Employee employeeArray[];

    @Given("the user accessing base URI")
    public void the_user_accessing_base_URI() {
    	requestSpecification = RestAssured.with();
    	requestSpecification.baseUri("http://localhost:8082");
    }

    @When("the user send a get request {string}")
    public void the_user_send_a_get_request(String resource) {
    	System.out.println("When xxxxx");
    	Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/json");
    	response = requestSpecification.headers(headers).get(resource);
    	System.out.println("mock response  xxxxx"+response.prettyPrint());
    }

    @Then("the user should see the response code as {int}")
    public void the_user_should_see_the_response_code_as(Integer int1) {
    	System.out.println("response code xxxxx"+response.getStatusCode());
    	Assert.assertEquals(200, response.getStatusCode());
    }

    @When("the user making a POST call {string} with employee details {int}, {string}, {string}, {string}, {string}, {string}, {string}, {int}")
    public void the_user_making_a_POST_call_with_employee_details(String resource, Integer id, String empName, String fName, String lName, String email, String password, String phone, Integer empStatus) {
        System.out.println("When xxxxx");
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        Employee employee = new Employee();
        employee.setId(id);
        employee.setEmployeeName(empName);
        employee.setFirstName(fName);
        employee.setLastName(lName);
        employee.setEmail(email);
        employee.setPassword(password);
        employee.setPhone(phone);
        employee.setEmployeeStatus(empStatus);
        employeeList.add(employee);
        response = requestSpecification.headers(headers).body(employee).post(resource);
    }

    @When("the user making a POST call {string} request with array of employees detail")
    public void the_user_making_a_POST_call_request_with_array_employees_details(String resource, DataTable dataTable) {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        List<Map<String, String>> employeesInputList = dataTable.asMaps();
        for(Map<String, String> employee1: employeesInputList){
            Employee employee = new Employee();
            employee.setId(Integer.parseInt(employee1.get("id")));
            employee.setEmployeeName(employee1.get("employeeName"));
            employee.setFirstName(employee1.get("firstName"));
            employee.setLastName(employee1.get("lastName"));
            employee.setEmail(employee1.get("email"));
            employee.setPassword(employee1.get("password"));
            employee.setPhone(employee1.get("phone"));
            employee.setEmployeeStatus(Integer.parseInt(employee1.get("employeeStatus")));
            employeeArray[1] = employee;
        }
        response = requestSpecification.headers(headers).body(employeeArray).post(resource);
    }

    @When("the user making a POST call {string} request with list of employees detail")
    public void the_user_making_a_POST_call_request_with_list_employees_details(String resource, DataTable dataTable) {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        List<Map<String, String>> employeesInputList = dataTable.asMaps();
        for(Map<String, String> employee1: employeesInputList){
            Employee employee = new Employee();
            employee.setId(Integer.parseInt(employee1.get("id")));
            employee.setEmployeeName(employee1.get("employeeName"));
            employee.setFirstName(employee1.get("firstName"));
            employee.setLastName(employee1.get("lastName"));
            employee.setEmail(employee1.get("email"));
            employee.setPassword(employee1.get("password"));
            employee.setPhone(employee1.get("phone"));
            employee.setEmployeeStatus(Integer.parseInt(employee1.get("employeeStatus")));
            employeeList.add(employee);
        }
        response = requestSpecification.headers(headers).body(employeeList).post(resource);
    }

    @Then("validate the response for the employee details")
    public void validate_the_response_for_the_employee_details() {
        Employee employeeResponse = response.as(Employee.class);
        for(Employee employee: employeeList){
            Assert.assertEquals(employee.getId(), employeeResponse.getId());
            Assert.assertEquals(employee.getEmployeeName(), employeeResponse.getEmployeeName());
            Assert.assertEquals(employee.getFirstName(), employeeResponse.getFirstName());
            Assert.assertEquals(employee.getLastName(), employeeResponse.getLastName());
            Assert.assertEquals(employee.getEmail(), employeeResponse.getEmail());
            Assert.assertEquals(employee.getPassword(), employeeResponse.getPassword());
            Assert.assertEquals(employee.getPhone(), employeeResponse.getPhone());
            Assert.assertEquals(employee.getEmployeeStatus(), employeeResponse.getEmployeeStatus());
        }
    }

    @Then("validate the response for the list of employees details")
    public void validate_the_response_for_the_list_of_employees_details() {
        Employee[] employeesResponse = response.as(Employee[].class);
        for(int i=0;i<employeesResponse.length;i++){
            Assert.assertEquals(employeeList.get(i).getId(), employeesResponse[i].getId());
            Assert.assertEquals(employeeList.get(i).getEmployeeName(), employeesResponse[i].getEmployeeName());
            Assert.assertEquals(employeeList.get(i).getFirstName(), employeesResponse[i].getFirstName());
            Assert.assertEquals(employeeList.get(i).getLastName(), employeesResponse[i].getLastName());
            Assert.assertEquals(employeeList.get(i).getEmail(), employeesResponse[i].getEmail());
            Assert.assertEquals(employeeList.get(i).getPassword(), employeesResponse[i].getPassword());
            Assert.assertEquals(employeeList.get(i).getPhone(), employeesResponse[i].getPhone());
            Assert.assertEquals(employeeList.get(i).getEmployeeStatus(), employeesResponse[i].getEmployeeStatus());
        }
    }

    @Then("validate the response for the array of employees details")
    public void validate_the_response_for_the_array_of_employees_details() {
        EmployeesModel employeesModel = response.as(EmployeesModel.class);
        List<Employee> employeesResponse = employeesModel.getEmployees();
        Assert.assertEquals(employeeArray, employeesResponse);
    }

    @When("the user making GET call {string} with {string} and {string}")
    public void the_user_making_GET_call_with_and(String resource, String empName, String password) {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        Map<String, String> reqParams = new HashMap<>();
        reqParams.put("employeeName", empName);
        reqParams.put("password", password);
        response = requestSpecification.headers(headers).queryParams(reqParams).get(resource);
    }

    @Then("validate the response message {string} for employee logged into system")
    public void validate_the_response_message_for_employee_logged_into_system(String message) {
        SuccessMessage successMessage = response.as(SuccessMessage.class);
        Assert.assertEquals(message, successMessage.getSuccessMessage());
    }

    @When("the user making GET call {string}")
    public void the_user_making_GET_call(String resource) {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        response = requestSpecification.headers(headers).get(resource);
    }

    @Then("validate the response message {string} for employee logged out from system")
    public void validate_the_response_message_for_employee_logged_out_from_system(String message) {
        SuccessMessage successMessage = response.as(SuccessMessage.class);
        Assert.assertEquals(message, successMessage.getSuccessMessage());
    }

    @When("the user making GET call {string} employee by name {string}")
    public void the_user_making_GET_call_employee_by_name(String resource, String empName) {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        Map<String, String> reqParams = new HashMap<>();
        reqParams.put("employeeName", empName);
        response = requestSpecification.headers(headers).pathParams(reqParams).get(resource);
    }

    @Then("validate the response for the employee details by name")
    public void validate_the_response_for_the_employee_details_by_name(DataTable dataTable) {
        Employee employee = response.as(Employee.class);
        List<Map<String, String>> employeesInputList = dataTable.asMaps();
        for(Map<String, String> employee1: employeesInputList){
            Assert.assertEquals(employee.getId(), Integer.parseInt(employee1.get("id")));
            Assert.assertEquals(employee.getEmployeeName(), employee1.get("employeeName"));
            Assert.assertEquals(employee.getFirstName(), employee1.get("firstName"));
            Assert.assertEquals(employee.getLastName(), employee1.get("lastName"));
            Assert.assertEquals(employee.getEmail(), employee1.get("email"));
            Assert.assertEquals(employee.getPassword(), employee1.get("password"));
            Assert.assertEquals(employee.getPhone(), employee1.get("phone"));
            Assert.assertEquals(employee.getEmployeeStatus(), Integer.parseInt(employee1.get("employeeStatus")));
        }
    }

}
