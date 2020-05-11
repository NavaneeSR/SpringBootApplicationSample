package com.rabo.springbootcucumber.basics;

import org.springframework.http.HttpStatus;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

import io.cucumber.java.After;
import io.cucumber.java.Before;

public class Hooks {
	static WireMockServer wireMockServer;

	@Before
	public static void stubSetup() {
		wireMockServer = new WireMockServer(wireMockConfig().port(8082)); 
		wireMockServer.start();
		WireMock.configureFor("localhost", 8082);
		
		stubFor(post(urlEqualTo("/employee/createEmployee")).willReturn(aResponse().withStatus(HttpStatus.OK.value())
				.withHeader("Content-Type", "application/json").withBodyFile("employee-response.json")));

		stubFor(post(urlEqualTo("/employee/createWithArray")).willReturn(aResponse().withStatus(HttpStatus.OK.value())
				.withHeader("Content-Type", "application/json").withBodyFile("employee-array-req.json")));

		stubFor(post(urlEqualTo("/employee/createWithList")).willReturn(aResponse().withStatus(HttpStatus.OK.value())
				.withHeader("Content-Type", "application/json").withBodyFile("employee-list-req.json")));

		String empName = "BretTest";
		String password = "pass123";

		stubFor(get(urlEqualTo("/employee/login?employeeName="+empName+"&password="+password+"")).willReturn(aResponse().withStatus(HttpStatus.OK.value())
				.withHeader("Content-Type", "application/json").withBodyFile("employee-login-success.json")));

		stubFor(get(urlEqualTo("/employee/logout")).willReturn(aResponse().withStatus(HttpStatus.OK.value())
				.withHeader("Content-Type", "application/json").withBodyFile("employee-logout-success.json")));

		stubFor(get(urlPathEqualTo("/employee/"+empName)).willReturn(aResponse().withStatus(HttpStatus.OK.value())
				.withHeader("Content-Type", "application/json").withBodyFile("employee-response.json")));
	}
	
	@After
	public void closeWire() {
		wireMockServer.stop();
	}
}
