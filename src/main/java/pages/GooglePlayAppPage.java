package pages;

import io.appium.java_client.MobileDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static utils.TestRunner.printToLog;

public class GooglePlayAppPage extends BasePage {

    @FindBy(id = "com.android.vending:id/right_button")
    public WebElement openAppBtn;


    public GooglePlayAppPage(MobileDriver mobileDriver) {
        super(mobileDriver);
    }


    public boolean installOpenApp() {
        boolean isInstalled = false;
        List<WebElement> buttons = null;
        try {
            buttons = mobileDriver.findElements(By.className("android.widget.Button"));
            for (WebElement button : buttons) {
                if (button.getText().equalsIgnoreCase("install")) {
                    button.click();
                    printToLog("Installing dario app...");
                    WebDriverWait wait = new WebDriverWait(mobileDriver, 120);
                    wait.until(ExpectedConditions.elementToBeClickable(openAppBtn)).click();
                    printToLog("Dario app was successfully installed!");
                    Thread.sleep(10000);
                    isInstalled = true;
                    break;
                } else if (button.getText().equalsIgnoreCase("open")) {
                    button.click();
                    printToLog("Opening Dario app..");
                    Thread.sleep(4000);
                    isInstalled = true;
                    break;
                }
            }
        } catch (Exception ex) {
            printToLog("GooglePlayAppPage.installApp: " + ex.getMessage());
        }
        return isInstalled;
    }
}
