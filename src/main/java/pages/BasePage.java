package pages;

import io.appium.java_client.MobileDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.Alert;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.SwipeDirection;

import java.time.Duration;
import java.util.Set;

import static java.time.Duration.ofSeconds;
import static utils.TestRunner.printToLog;

public abstract class BasePage {

    protected MobileDriver mobileDriver;
    public WebDriverWait wait;
    public static TouchAction touchAction;

    public BasePage(MobileDriver mobileDriver) {
        this.mobileDriver = mobileDriver;
        PageFactory.initElements(new AppiumFieldDecorator(mobileDriver, ofSeconds(10)), this);
        wait = new WebDriverWait(mobileDriver, 10);
        touchAction = new TouchAction(mobileDriver);
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

    public void swipe(SwipeDirection swipeDirection) {

        try {
            //Instancing page width and height
            mobileDriver.hideKeyboard();
            int windowWidth = mobileDriver.manage().window().getSize().width;
            int windowHeight = mobileDriver.manage().window().getSize().height;
            int startX, endX, startY, endY;
            startX = startY = endX = endY = 0;


            //Checking which direction is given.
            switch (swipeDirection) {
                case UP:
                    startX = endX = windowWidth / 2;
                    startY = (int) (windowHeight * 0.2);
                    endY = (int) (windowHeight * 0.8);

                    break;
                case DOWN:
                    startX = endX = windowWidth / 2;
                    startY = (int) (windowHeight * 0.8);
                    endY = (int) (windowHeight * 0.2);

                    break;
                case LEFT:
                    startY = endY = windowHeight / 2;
                    startX = (int) (windowWidth * 0.9);
                    endX = (int) (windowWidth * 0.1);
                    break;
                case RIGHT:
                    startY = endY = windowHeight / 2;
                    startX = (int) (windowWidth * 0.1);
                    endX = (int) (windowWidth * 0.9);
                    break;
                default:

            }

            //Swiping
            PointOption startPoint = new PointOption();
            PointOption endPoint = new PointOption();
            WaitOptions waitOptions = new WaitOptions();
            touchAction.press(startPoint.withCoordinates(startX, startY)).waitAction(waitOptions.withDuration(Duration.ofSeconds(2))).moveTo(endPoint.withCoordinates(endX, endY)).release().perform();
            printToLog("BasePage.swipe(" + swipeDirection.toString() + "): Swiped " + swipeDirection.toString() + " successfully");
        } catch (Exception ex) {
            printToLog("BasePage.swipe(" + swipeDirection.toString() + "): Error details: " + ex.getMessage());
        }
    }

    public void acceptAlertsIfShown(int numOfExpectedAlerts) {
        try {
            for (int i = 0; i < numOfExpectedAlerts; i++) {
                wait.until(ExpectedConditions.alertIsPresent());
                Alert alert = mobileDriver.switchTo().alert();
                alert.accept();
                printToLog("Alert was accepted (" + (i + 1) + "/" + numOfExpectedAlerts + ")");
            }
        } catch (Exception ex) {
            printToLog("BasePage.acceptAlertsIfShown: " + ex.getMessage());
        }
    }


}
