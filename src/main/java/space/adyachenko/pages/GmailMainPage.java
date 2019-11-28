package space.adyachenko.pages;

import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import space.adyachenko.generic.GenericWebPage;

import java.util.List;

public class GmailMainPage extends GenericWebPage {
    private static final Logger logger = LogManager.getLogger("GmailMainPage");

    @FindBy(xpath = "//div[@class='UI']")
    WebElement mailSection;

    @FindBy(xpath = "//div[text()='Compose']")
    WebElement composeButton;

    @FindBy(xpath = "//input[@aria-label='Search mail']")
    WebElement searchField;

    @FindBy(xpath = "//button[@aria-label='Search Mail']")
    WebElement searchButton;

    By visibleMailTables = By.xpath(".//div[@class='Cp']//table");

    public GmailMainPage(WebDriver driver) {
        super(driver);
        waitForElementVisibility(driver, mailSection, 20);
    }

    @Step("Pressing button Compose to create new mail")
    public GmailSendMail newMail() {
        composeButton.click();
        return new GmailSendMail(driver);
    }

    public List<WebElement> getMailsFromVisibleTab() {
        return mailSection.findElements(visibleMailTables);
    }

    @Step("Search mail via Search input by text {0}")
    public GmailSearchPage searchMail(String searchString) {
        searchField.sendKeys(searchString);
        searchButton.click();
        return new GmailSearchPage(driver);
    }

}
