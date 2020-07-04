package pages;

import io.appium.java_client.MobileDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.SwipeDirection;

import static utils.TestRunner.printToLog;

public class DarioWelcomePage extends BasePage {

    @FindBy(id = "com.labstyle.darioandroid:id/welcome_get_started_login_button")
    public WebElement loginBtn;

    public DarioWelcomePage(MobileDriver mobileDriver) {
        super(mobileDriver);
    }

    public DarioLoginPage swipeTutorial() {
        try {
            for (int i = 0; i < 9; i++) {
                swipe(SwipeDirection.LEFT);
            }
            wait.until(ExpectedConditions.visibilityOf(loginBtn)).click();
            printToLog("Tutorial finished");
        } catch (Exception ex) {
            printToLog("DarioWelcomePage.swipeTutorial: " + ex.getMessage());
        }
        return new DarioLoginPage(mobileDriver);
    }
}
