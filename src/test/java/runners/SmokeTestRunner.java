package runners;

import org.junit.platform.suite.api.*;

/**
 * Smoke Test Runner - Executes critical path tests for quick validation
 * Runs tests tagged with @smoke for fast feedback
 */
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = "cucumber.filter.tags", value = "@smoke")
@ConfigurationParameter(key = "cucumber.plugin", value = "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm")
@ConfigurationParameter(key = "cucumber.glue", value = "stepdefinitions,hooks")
public class SmokeTestRunner {
}