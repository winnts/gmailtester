package space.adyachenko.listeners;

import com.google.common.io.Files;
import io.qameta.allure.Attachment;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import space.adyachenko.AbstractTest;
import space.adyachenko.utils.Utilities;

import java.io.File;
import java.io.IOException;

public class TestListener extends AbstractTest implements ITestListener {
    private static final Logger logger = LogManager.getLogger("TestListener");

    @Override
    public void onTestStart(ITestResult result) {

    }

    @Override
    public void onTestSuccess(ITestResult result) {

    }


    @Override
    public void onTestFailure(ITestResult result) {
        Object testClass = result.getInstance();
        WebDriver driver = ((AbstractTest) testClass).getDriver();
        try {
            failProcessing(driver, result);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }

    @Override
    public void onStart(ITestContext context) {

    }

    @Override
    public void onFinish(ITestContext context) {

    }

    protected void failProcessing(WebDriver driver, ITestResult result) throws IOException {
        File screenshot = screenCapture(driver, Utilities.uniqueId() + "_"
                + result.getMethod().getMethodName());
        saveScreenshotAttachment(screenshot);
        attachPageSource(driver);
    }

    @Attachment(value = "Screenshot", type = "image/png")
    public byte[] saveScreenshotAttachment(File screenshot) throws IOException {
        return screenshot == null ? null : Files.toByteArray(screenshot);
    }

    @Attachment(value = "Page source html", type = "text/html")
    public String attachPageSource(WebDriver driver) {
        return driver == null ? null : driver.switchTo().defaultContent().getPageSource();
    }

    public static File screenCapture(WebDriver driver, String name) {
        File screenshotPath = null;
        try {
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String path = new File(".").getCanonicalPath() + "/target/screenShots/" + name + ".png";
            logger.info("screenCapture: {}", path);
            screenshotPath = new File(path);
            FileUtils.copyFile(screenshot, screenshotPath);
        } catch (Exception e) {
            logger.warn("Screen capture failed: {}", e.getMessage());
        }
        return screenshotPath;
    }
}
