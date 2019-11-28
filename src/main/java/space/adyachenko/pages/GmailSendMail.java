package space.adyachenko.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import space.adyachenko.generic.GenericWebPage;

public class GmailSendMail extends GenericWebPage {
    @FindBy(xpath = "//div[@aria-label='New Message']")
    WebElement sendMailModal;

    @FindBy(xpath = "//textarea[@name='to']")
    WebElement mailToField;

    @FindBy(xpath = "//input[@name='subjectbox']")
    WebElement subjectField;

    @FindBy(xpath = "//div[@role='textbox']")
    WebElement textBox;

    @FindBy(xpath = "//div[@role='button' and text()='Send']")
    WebElement sendButton;

    public GmailSendMail (WebDriver driver){
        super(driver);
        waitForElementVisibility(driver, sendMailModal, 10);
    }
    @Step("Creating new mail to: {0}, subject: {1}, text: {2}")
    public GmailSendMail composeMail(String to, String subject, String messageText){
        mailToField.sendKeys(to);
        subjectField.sendKeys(subject);
        String newMailBody = messageText + "\n" + textBox.getText();
        textBox.clear();
        textBox.sendKeys(newMailBody);
        return this;
    }

    @Step("Composing and Sending email to: {0}, with subject: {1}, message: {2}")
    public GmailMainPage sendMail(String to, String subject, String messageText){
        composeMail(to, subject, messageText);
        waitForElementClickable(driver, sendButton, 20);
        sendButton.click();
        waitForElementDisappear(driver, sendMailModal, 10);
        return new GmailMainPage(driver);
    }
}
