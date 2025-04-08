package pages;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CareersPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // Locator for the "About Us" link using XPath.
    private By aboutUsLink = By.xpath("//a[normalize-space()='About Us']");
    // Locator for the "Learn More" link using CSS selector.
    private By learnMoreLink = By.cssSelector("a.score-button.btn-clickable-area[href*='/corporate/careers']");
    // Locator for the "Search jobs" button using CSS selector.
    private By searchJobsButton = By.cssSelector("a.score-button.btn-red[href*='/about-us/careers/jobs']");
    // Locator for the search input field using CSS selector.
    private By searchInput = By.cssSelector("input.search-box-input.ais-search-box--input");
    // Locator for search results elements; not used for titles directly.
    private By searchResults = By.cssSelector(".search-result");
    // New locator for job title elements within search results.
    private By jobTitleLocator = By.cssSelector("h3.search-results-item-title a.search-results-item-title-url");
    // Locator for the Search button (the <span> with text "Search").
    private By searchButton = By.xpath("//span[normalize-space()='Search']");
    // Locator for the "Accept All Cookies" button.
    private By acceptCookiesButton = By.id("onetrust-accept-btn-handler");

    public CareersPage(WebDriver driver) {
        this.driver = driver;
        // Initialize an explicit wait with a 10-second timeout.
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    /**
     * Opens the specified URL.
     */
    public void openWebsite(String url) {
        driver.get(url);
    }

    /**
     * Accepts cookies by clicking the "Accept All Cookies" button.
     */
    public void acceptCookies() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(acceptCookiesButton)).click();
        } catch (TimeoutException e) {
            // If the cookie popup does not appear, proceed.
        }
    }

    /**
     * Clicks "About Us" and "Learn More", then switches focus to the new window.
     */
    public void navigateToCareersPage() {
        // Wait until any cookie overlay is gone.
        By cookieOverlay = By.cssSelector("div.onetrust-pc-dark-filter.ot-fade-in");
        wait.until(ExpectedConditions.invisibilityOfElementLocated(cookieOverlay));
        
        // Hover over and click on the "About Us" element.
        WebElement aboutUsElement = wait.until(ExpectedConditions.visibilityOfElementLocated(aboutUsLink));
        Actions actions = new Actions(driver);
        actions.moveToElement(aboutUsElement).perform();
        wait.until(ExpectedConditions.elementToBeClickable(aboutUsLink)).click();
        
        // Wait for the "Learn More" link to be clickable, then click it.
        wait.until(ExpectedConditions.elementToBeClickable(learnMoreLink)).click();
        
        // Switch to the new window after clicking "Learn More".
        switchToNewWindow();
    }

    /**
     * Switches the WebDriver context to the newly opened window.
     */
    public void switchToNewWindow() {
        String originalWindow = driver.getWindowHandle();
        Set<String> windowHandles = driver.getWindowHandles();
        for (String handle : windowHandles) {
            if (!handle.equals(originalWindow)) {
                driver.switchTo().window(handle);
                break;
            }
        }
    }
    
    /**
     * Waits until the Careers page is loaded by verifying that the URL contains the expected substring.
     */
    public boolean waitForCareerPageToLoad(int timeoutSeconds) {
        try {
            WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
            return longWait.until(ExpectedConditions.urlContains("https://risk.lexisnexis.com/about-us/careers"));
        } catch (TimeoutException e) {
            return false;
        }
    }
    
    /**
     * Clicks the "Search jobs" button on the Careers page.
     */
    public void clickSearchJobs() {
        wait.until(ExpectedConditions.elementToBeClickable(searchJobsButton)).click();
    }
    
    /**
     * Enters the provided keyword into the search input field.
     */
    public void enterSearchKeyword(String keyword) {
        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(searchInput));
        searchBox.clear();
        searchBox.sendKeys(keyword);
    }
    
    /**
     * Clicks the Search button (the element with text "Search").
     */
    public void clickSearchButton() {
        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
    }
    
    /**
     * Performs a search by entering the keyword and clicking the Search button.
     */
    public void searchForJob(String keyword) {
        enterSearchKeyword(keyword);
        clickSearchButton();
    }
    
    /**
     * Retrieves the visible text of all job title elements in the search results.
     * 
     * @return a List of job title strings.
     */
    public List<String> getAllJobTitles() {
        try {
            List<WebElement> titleElements = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(jobTitleLocator)
            );
            return titleElements.stream()
                        .map(WebElement::getText)
                        .map(String::trim)
                        .collect(Collectors.toList());
        } catch (TimeoutException e) {
            // Return an empty list if no titles are found.
            return Collections.emptyList();
        }
    }
    
    /**
     * Checks whether the expected keyword exactly matches any of the job titles in the search results.
     *
     * @param expectedTitle The exact title to match.
     * @return true if a job title matches exactly; false otherwise.
     */
    public boolean isExactTitleFound(String expectedTitle) {
        List<String> titles = getAllJobTitles();
        return titles.contains(expectedTitle);
    }
}
