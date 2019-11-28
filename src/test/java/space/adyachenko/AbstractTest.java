package space.adyachenko;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AbstractTest {
    private static final Logger logger = LogManager.getLogger("AbstractTest");
    protected WebDriver driver = null;

    public WebDriver launchBrowser(String browser) {
        if (browser.equalsIgnoreCase("Chrome") || (browser == null)) {
            driver = new ChromeDriver();
        } else if (browser.equalsIgnoreCase("FireFox")) {
            driver = new FirefoxDriver();
        } else if (browser.equalsIgnoreCase("Explorer")) {
            driver = new InternetExplorerDriver();
        } else if (browser.equalsIgnoreCase("Safari")) {
            driver = new SafariDriver();
        }
        return driver;
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        try {
            driverQuit();
        } catch (Exception e) {
            logger.error("Could not close WebDriver: {}", e.getMessage());
        }
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        driverQuit();
    }

    protected void driverQuit() {
        if (driver != null) {
            try {
                driver.quit();
            } catch (Exception e) {
                logger.error("Could not close WebDriver: {}", e.getMessage());
            } finally {
                driver = null;
            }
        }
    }

    public WebDriver getDriver(){
        return driver;
    }

    public Properties loadPropertiesFile() {

        Properties prop = new Properties();

        try (InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            prop.load(resourceAsStream);
        } catch (IOException e) {}

        return prop;

    }
}