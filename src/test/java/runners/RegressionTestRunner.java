package runners;

import org.junit.platform.suite.api.*;

/**
 * Regression Test Runner - Executes comprehensive test suite
 * Runs tests tagged with @regression for full coverage
 */
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = "cucumber.filter.tags", value = "@regression")
@ConfigurationParameter(key = "cucumber.plugin", value = "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm")
@ConfigurationParameter(key = "cucumber.glue", value = "stepdefinitions,hooks")
public class RegressionTestRunner {
}