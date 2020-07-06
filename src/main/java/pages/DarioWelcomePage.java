package pages;

import io.appium.java_client.MobileDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.SwipeDirection;

import static utils.TestRunner.printToLog;

public class DarioWelcomePage extends BasePage {

    @FindBy(id = "com.labstyle.darioandroid:id/welcome_get_started_login_button")
    public WebElement loginBtn;

    public DarioWelcomePage(MobileDriver mobileDriver) throws InterruptedException {
        super(mobileDriver);
        Thread.sleep(3000);
    }

    public DarioLoginPage swipeTutorial() {
        try {
            Thread.sleep(10000);
            swipe(SwipeDirection.RIGHT);
            if (!loginBtn.isDisplayed()) {
                Thread.sleep(5000);
                swipe(SwipeDirection.RIGHT);
            }
            loginBtn.click();
            printToLog("Tutorial finished");
        } catch (Exception ex) {
            printToLog("DarioWelcomePage.swipeTutorial: " + ex.getMessage());
        }
        return new DarioLoginPage(mobileDriver);
    }

}
