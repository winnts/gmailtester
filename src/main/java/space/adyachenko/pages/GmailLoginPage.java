package space.adyachenko.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import space.adyachenko.generic.GenericWebPage;

public class GmailLoginPage extends GenericWebPage {

    @FindBy(id = "identifierId")
    WebElement loginField;

    @FindBy(xpath = "//input[@name='password']")
    WebElement passwordField;

    @FindBy(id = "identifierNext")
    WebElement loginNext;

    @FindBy(id = "passwordNext")
    WebElement passwordNext;

    public GmailLoginPage(WebDriver driver){
        super(driver);
        waitForElementVisibility(driver, loginField, 10);
    }

    @Step("Login to GMail with login {0} and password {1}")
    public GmailMainPage loginToGmail(String email, String password){
        loginField.sendKeys(email);
        loginNext.click();
        waitForElementVisibility(driver, passwordField, 10);
        passwordField.sendKeys(password);
        waitForElementClickable(driver, passwordNext, 10);
        passwordNext.click();
        return new GmailMainPage(driver);
    }

}
