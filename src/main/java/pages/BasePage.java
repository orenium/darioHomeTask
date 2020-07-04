package pages;

import io.appium.java_client.MobileDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Set;

import static java.time.Duration.ofSeconds;
import static utils.TestRunner.printToLog;

public abstract class BasePage {

    protected MobileDriver mobileDriver;
    public WebDriverWait wait;

    public BasePage(MobileDriver mobileDriver) {
        this.mobileDriver = mobileDriver;
        PageFactory.initElements(new AppiumFieldDecorator(mobileDriver, ofSeconds(10)), this);
        wait = new WebDriverWait(mobileDriver, 10);
    }

    public void switchContext(String context) {

        try {
            Set contexts = mobileDriver.getContextHandles();
            for (Object s : contexts) {
                if (s.toString().contains(context)) {
                    mobileDriver.context(s.toString());
                    printToLog("context was changed to " + context);
                }
            }
        } catch (Exception ex) {
            printToLog("BasePage.switchContext: " + ex.getMessage());
        }
    }

    public void allowNotificationsPopup() {

        try {
            switchContext("NATIVE_APP");
            mobileDriver.findElement(By.xpath(".//android.widget.Button[@text='Allow']")).click();
            System.out.println("Allow notifications button was clicked");
            Thread.sleep(2000);
            switchContext("CHROMIUM");
        } catch (Exception ex) {
            printToLog("BasePage.allowNotificationsPopup: " + ex.getMessage());
        }

    }

    public void blockNotificationsPopup() {

        try {
            switchContext("NATIVE_APP");
            mobileDriver.findElement(By.xpath(".//android.widget.Button[@text='Block']")).click();
            printToLog("Block notifications button was clicked");
            Thread.sleep(2000);
            switchContext("CHROMIUM");
        } catch (Exception ex) {
            printToLog("BasePage.allowNotificationsPopup: " + ex.getMessage());
        }

    }




}
