package utils;

// Import necessary classes for file handling and properties.
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class DriverFactory {

    // Create a static Properties object to hold configuration settings.
    private static Properties properties = new Properties();

    // Static initializer block runs once when the class is loaded.
    static {
        // Open the "config.properties" file from the resources folder.
        try (InputStream input = DriverFactory.class.getClassLoader().getResourceAsStream("config.properties")) {
            // Check if the input stream is null, meaning the file wasn't found.
            if (input == null) {
                // Log an error message if the file cannot be found.
                System.err.println("Unable to find config.properties");
            } else {
                // Load the configuration properties from the file.
                properties.load(input);
            }
        } catch (IOException e) {
            // Print the stack trace if an I/O error occurs during file loading.
            e.printStackTrace();
        }
    }

  public static WebDriver createDriver() {
    // Set the system property for geckodriver.
    System.setProperty("webdriver.gecko.driver", properties.getProperty("webdriver.gecko.driver"));

    // Create FirefoxOptions instance.
    FirefoxOptions options = new FirefoxOptions();
    
    // Get the Firefox binary path from the config file.
    String firefoxBinaryPath = properties.getProperty("firefox.binary.path");
    if (firefoxBinaryPath != null && !firefoxBinaryPath.trim().isEmpty()) {
        options.setBinary(firefoxBinaryPath);
    }
    
    // Return a new FirefoxDriver instance with the configured options.
    return new FirefoxDriver(options);
}

}
