package space.adyachenko;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import space.adyachenko.listeners.TestListener;
import space.adyachenko.pages.GmailLoginPage;
import space.adyachenko.pages.GmailMainPage;
import space.adyachenko.pages.GmailSearchPage;
import space.adyachenko.utils.Utilities;

import java.util.Properties;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

@Listeners({ TestListener.class })
public class GmailTest extends AbstractTest{

    @Test(priority = 0, description = "Sending and deleting mail via gmail.com")
    public void gmail(){
        Properties prop = loadPropertiesFile();
        String mailSubject = prop.getProperty("gmail.subject") + " " + Utilities.uniqueId();

        launchBrowser(prop.getProperty("browser"));
        driver.get("https://gmail.com");

        GmailLoginPage gmailLoginPage = new GmailLoginPage(driver);
        gmailLoginPage.loginToGmail(prop.getProperty("gmail.login"), prop.getProperty("gmail.password"))
                .newMail()
                .sendMail(prop.getProperty("gmail.login"), mailSubject, prop.getProperty("gmail.messageText"));
        GmailMainPage gmailMainPage = new GmailMainPage(driver);
        gmailMainPage.searchMail(mailSubject);

        GmailSearchPage gmailSearchPage = new GmailSearchPage(driver);
        assertTrue(gmailSearchPage.waitForMail(mailSubject));

        gmailSearchPage.selectMailBySubject(mailSubject).deleteSelectedMail();
        gmailSearchPage = new GmailSearchPage(driver);
        assertFalse(gmailSearchPage.mailExist(mailSubject));
    }
}
