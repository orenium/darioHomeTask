package pages;

import io.appium.java_client.MobileDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

import static utils.TestRunner.printToLog;

public class DarioDataEntryPage extends BasePage {

    @FindBy(id = "com.labstyle.darioandroid:id/qde_add_glucose_manually_button")
    public WebElement addBtn;

    @FindBy(id = "com.labstyle.darioandroid:id/done_button")
    public WebElement doneBtn;


    public DarioDataEntryPage(MobileDriver mobileDriver) {
        super(mobileDriver);
    }


    public void swipeToSetValue() {

        try {
            //Instancing page width and height
            mobileDriver.hideKeyboard();
            int windowWidth = mobileDriver.manage().window().getSize().width;
            int windowHeight = mobileDriver.manage().window().getSize().height;
            int startX, endX, startY, endY;
            startX = startY = endX = endY = 0;
//                case LEFT:
            startY = endY = 630;
            startX = 500;
            endX = 200;

//                case RIGHT:
//                      startY = endY = 630;
//                    startX = 500;
//                    endX = 850;

            //Swiping
            PointOption startPoint = new PointOption();
            PointOption endPoint = new PointOption();
            WaitOptions waitOptions = new WaitOptions();
            touchAction.press(startPoint.withCoordinates(startX, startY)).waitAction(waitOptions.withDuration(Duration.ofSeconds(2))).moveTo(endPoint.withCoordinates(endX, endY)).release().perform();
//            printToLog("DarioDataEntryPage.swipeToSetValue(" + swipeDirection.toString() + "): Swiped " + swipeDirection.toString() + " successfully");
        } catch (Exception ex) {
            printToLog("DarioDataEntryPage.swipeToSetValue: Error details: " + ex.getMessage());
        }
    }


}
