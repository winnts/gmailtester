package space.adyachenko.generic;

import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;

public class GenericWebPage {
    private static final Logger logger = LogManager.getLogger("GenericWebPage");
    public final WebDriver driver;

    public GenericWebPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @Step("Waiting for Element to be Visible {1}")
    public static WebElement waitForElementVisibility(WebDriver driver, WebElement element, Integer timeout) {
        logger.debug("waitForElementVisibility : {}", element);
        WebElement webElement;
        try {
            webElement = (new FluentWait<>(driver))
                    .withTimeout(Duration.ofSeconds(timeout))
                    .pollingEvery(Duration.ofMillis(500))
                    .ignoring(StaleElementReferenceException.class, NoSuchElementException.class)
                    .until(ExpectedConditions.visibilityOf(element));
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw e;
        }
        return webElement;
    }

    @Step("Waiting for Element to be Clickable {1}")
    public static WebElement waitForElementClickable(WebDriver driver, WebElement element, Integer timeout) {
        logger.debug("waitForElementClickable : {}", element);
        WebElement webElement;
        try {
            webElement = (new FluentWait<>(driver))
                    .withTimeout(Duration.ofSeconds(timeout))
                    .pollingEvery(Duration.ofMillis(500))
                    .ignoring(StaleElementReferenceException.class, NoSuchElementException.class)
                    .until(ExpectedConditions.elementToBeClickable(element));
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw e;
        }
        return webElement;
    }

    @Step("Waiting for Element to Disappear {1}")
    public static Boolean waitForElementDisappear(WebDriver driver, WebElement element, Integer timeout) {
        logger.debug("waitForElementDisapear : {}", element);
        Boolean invisibleCondition = false;
        try {
            invisibleCondition = (new FluentWait<>(driver))
                    .withTimeout(Duration.ofSeconds(timeout))
                    .pollingEvery(Duration.ofMillis(500))
                    .ignoring(StaleElementReferenceException.class, NoSuchElementException.class)
                    .until(ExpectedConditions.invisibilityOf(element));
        } catch (Exception e) {
            invisibleCondition = true;
        }
        return invisibleCondition;
    }

    public void sleep(int time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
