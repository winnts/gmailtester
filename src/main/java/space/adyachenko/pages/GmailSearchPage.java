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


public class GmailSearchPage extends GenericWebPage {
    private static final Logger logger = LogManager.getLogger("GmailSearchPage");

    @FindBy(xpath = "//div[@role='main']")
    WebElement searchResultTable;

    @FindBy(xpath = "//div[@class='UI']")
    WebElement mainMailTable;

    @FindBy(xpath = "//button[@aria-label='Search Mail']")
    WebElement searchButton;

    @FindBy(xpath = "//div[@gh='tm']//div[@role='button' and @title='Delete']")
    WebElement deleteButton;

    By mailCheckbox = By.xpath(".//td//div[@role='checkbox']");

    public GmailSearchPage(WebDriver driver) {
        super(driver);
        waitForElementDisappear(driver, mainMailTable, 10);
        waitForElementVisibility(driver, searchResultTable, 10);
    }

    @Step("Searching for mail with subject {0}")
    public WebElement getSearchResults(String search) {
        WebElement resultRow = null;
        List<WebElement> resultTables = searchResultTable.findElements(By.xpath(".//table"));
        try {
            for (WebElement resultTable : resultTables) {
                if (resultTable.getText().contains(search)) {
                    resultRow = resultTable.findElement(By.xpath(".//tr"));
                }
            }
        } catch (NullPointerException e) {
            logger.info("Search results are Empty");
        }
        return resultRow;
    }

    @Step("Checking if mail with subject {0} exist")
    public Boolean mailExist(String search) {
        if (getSearchResults(search) != null) {
            return true;
        } else
            return false;
    }

    @Step("Waiting for mail with subject {0}")
    public Boolean waitForMail(String search) {
        boolean mailReceived = false;
        for (int timer = 0; timer <= 180; timer++) {
            if (mailExist(search)) {
                mailReceived = true;
                break;
            } else {
                sleep(5000);
                searchButton.click();
            }
        }
        return mailReceived;
    }

    @Step("Selecting mail with subject {0}")
    public GmailSearchPage selectMailBySubject(String subject) {
        getSearchResults(subject).findElement(mailCheckbox).click();
        return this;
    }

    @Step("Deleting selected message")
    public GmailSearchPage deleteSelectedMail() {
        waitForElementVisibility(driver, deleteButton, 10);
        deleteButton.click();
        return this;
    }
}
