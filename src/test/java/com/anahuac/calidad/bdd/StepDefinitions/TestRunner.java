package com.anahuac.calidad.bdd.StepDefinitions;

import org.junit.runner.RunWith;   
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;  

@RunWith(Cucumber.class) 
@CucumberOptions(features="src/test/java/com/anahuac/calidad/bdd/Features",glue={"src/test/java/com/anahuac/calidad/bdd/StepDefinitions"},
monochrome=true,
plugin = { "pretty", "html:target/HtmlReports"
, "pretty", "json:target/JSONReports/cucumber.json"
,
		"pretty",  "junit:target/JUnitReports/cucumber.xml"},
tags="@smoketest"
)
public class TestRunner {
}