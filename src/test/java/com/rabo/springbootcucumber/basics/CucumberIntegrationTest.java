package com.rabo.springbootcucumber.basics;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features/employee",dryRun= false,
        plugin = {"pretty", "html:target/cucumber/employee"}, tags = {"@smoke"},
        extraGlue = "io.tpd.springbootcucumber.bagcommons")
public class CucumberIntegrationTest {
}
