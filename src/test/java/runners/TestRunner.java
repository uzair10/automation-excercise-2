package runners; // Defines the package for the test runner class.

import org.junit.runner.RunWith; // Imports the Cucumber class that provides a custom runner for JUnit.

import io.cucumber.junit.Cucumber; // Imports the annotation to specify options for running Cucumber.
import io.cucumber.junit.CucumberOptions; // Imports JUnit's RunWith annotation, allowing us to specify a custom runner.

@RunWith(Cucumber.class) // Instructs JUnit to run the tests using the Cucumber class as the test runner.
@CucumberOptions(
    features = "src/test/resources/features", // Specifies the directory containing the feature files.
    glue = {"stepdefinitions"}, // Specifies the package where Cucumber will look for step definition methods.
    plugin = {"pretty", "html:target/cucumber-reports.html"}, // Configures report plugins; "pretty" for console output and "html" to generate an HTML report in the target folder.
    monochrome = true // Makes the console output more readable by removing ANSI color codes.
)
public class TestRunner {
    // This class is empty.
    // Its purpose is to serve as the entry point for JUnit when running Cucumber tests.
}
