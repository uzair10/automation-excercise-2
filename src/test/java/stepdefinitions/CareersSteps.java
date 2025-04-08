package stepdefinitions; // Define the package for the step definitions.

import org.junit.Assert; // Import Cucumber's @After annotation.
import org.openqa.selenium.WebDriver; // Import Cucumber's @Before annotation.

import io.cucumber.java.After; // Import Cucumber step definition annotations.
import io.cucumber.java.Before; // Import JUnit's assertion library.
import io.cucumber.java.en.And; // Import WebDriver interface.
import io.cucumber.java.en.Given; // Import the CareersPage page object.
import io.cucumber.java.en.Then; // Import the custom DriverFactory for WebDriver creation.
import io.cucumber.java.en.When;
import pages.CareersPage;
import utils.DriverFactory;

public class CareersSteps {

    private WebDriver driver; // WebDriver instance to control the browser.
    private CareersPage careersPage; // Page object representing actions on the Careers page.

    @Before
    public void setUp() {
        driver = DriverFactory.createDriver(); // Create a new WebDriver instance using our custom factory.
        driver.manage().window().maximize(); // Maximize the browser window.
        careersPage = new CareersPage(driver); // Initialize the CareersPage object with the current WebDriver.
    }

    @After
    public void tearDown() {
        if (driver != null) { // Check if the driver is not null.
            driver.quit(); // Quit the WebDriver session to close the browser.
        }
    }

    @Given("I open the risk.lexisnexis website")
    public void i_open_the_risk_lexisnexis_website() {
        careersPage.openWebsite("https://risk.lexisnexis.co.uk/"); // Call the openWebsite method with the specified URL.
    }

    @And("I accept cookies")
    public void i_accept_cookies() {
        careersPage.acceptCookies(); // Execute the method to accept cookies on the website.
    }

    @When("I navigate to the Careers page")
    public void i_navigate_to_the_careers_page() {
        careersPage.navigateToCareersPage(); // Navigate to the Careers page by clicking "About Us" and "Learn More", then switch to the new window.
    }

    @Then("I should see the Careers page")
    public void i_should_see_the_careers_page() {
        boolean loaded = careersPage.waitForCareerPageToLoad(20); // Wait for up to 20 seconds for the Careers page to load.
        Assert.assertTrue("Careers page did not load in time", loaded); // Assert that the Careers page loaded correctly.
    }

    @When("I click on Search jobs")
    public void i_click_on_search_jobs() {
        careersPage.clickSearchJobs(); // Click on the "Search jobs" button on the Careers page.
    }

    @When("I search for {string}")
    public void i_search_for(String keyword) {
        careersPage.searchForJob(keyword); // Enter the specified search keyword and trigger the search.
    }

    @Then("I should see the exact job title {string} in the search results")
    public void i_should_see_the_exact_job_title_in_the_search_results(String expectedTitle) {
        boolean found = careersPage.isExactTitleFound(expectedTitle); // Check if the expected exact job title is present in the search results.
        if (!found) { // If the expected title is not found,
            // Retrieve all job titles to provide a detailed failure message.
            java.util.List<String> allTitles = careersPage.getAllJobTitles();
            Assert.fail("Expected exact title '" + expectedTitle + 
                        "' not found in search results. Found titles: " + allTitles); // Fail the test and print the list of found job titles.
        }
    }
}
