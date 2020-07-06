package pages;

import io.appium.java_client.MobileDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.SwipeDirection;

import static utils.TestRunner.*;

public class DarioDataEntryPage extends BasePage {

    @FindBy(id = "com.labstyle.darioandroid:id/qde_add_glucose_manually_button")
    public WebElement addBtn;

    @FindBy(id = "com.labstyle.darioandroid:id/done_button")
    public WebElement doneBtn;

    @FindBy(id = "com.labstyle.darioandroid:id/fetValue")
    public WebElement valueElement;

    @FindBy(id = "com.labstyle.darioandroid:id/rule")
    public WebElement valueSlider;

    private int shownValue = 0;

    public DarioDataEntryPage(MobileDriver mobileDriver) {
        super(mobileDriver);
    }


    public void swipeToSetValue() {

        try {
            wait.until(ExpectedConditions.elementToBeClickable(addBtn)).click();
            if (valueSlider.isDisplayed()) {
                swipeElement(valueSlider, getRandomSwipeDirection());
                takeScreenShot(mobileDriver);
                setValue();
            }
        } catch (Exception ex) {
            printToLog("DarioDataEntryPage.swipeToSetValue: Error details: " + ex.getMessage());
        }
    }

    public void setValue() {
        if (valueElement.isDisplayed()) {
            shownValue = Integer.parseInt(valueElement.getText());
            printToLog("New Value was set: " + shownValue);
        }
    }

    public DarioAppMainPage ClickDoneButton() {
        try {
            if (doneBtn.isDisplayed()) {
                doneBtn.click();
            }
//            ShownValue = Integer.parseInt(valueElement.getText());
//            printToLog("Value: " + ShownValue + " was recorded");
        } catch (Exception ex) {
            printToLog("DarioDataEntryPage.addNewValue: " + ex.getMessage());
        }
        return new DarioAppMainPage(mobileDriver);
    }

    public int getValue() {
        try {
            wait.until(ExpectedConditions.visibilityOf(valueElement));
        } catch (Exception ex) {
            printToLog("DarioDataEntryPage.getValue: " + ex.getMessage());
        }
        return shownValue;
    }

}

