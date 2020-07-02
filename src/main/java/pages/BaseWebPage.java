package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static utils.TestRunner.printToLog;

public abstract class BaseWebPage {

    protected WebDriver driver;

    public BaseWebPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    protected boolean waitForPageToload(By bySelector) {
        boolean isLoaded = false;

        try {
            WebDriverWait wait = new WebDriverWait(driver, 10);
            wait.until(ExpectedConditions.presenceOfElementLocated(bySelector));
            isLoaded = true;
        } catch (Exception ex) {
            printToLog("baseWebPage.waitForPageToload: " + ex.getMessage());
        }

        return isLoaded;

    }

}
