package pages;

import io.appium.java_client.MobileDriver;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.MeasurementType;

import static utils.TestRunner.printToLog;

public class DarioAppMainPage extends BasePage {

    @FindBy(id = "com.labstyle.darioandroid:id/plus")
    public WebElement plusBtn;

    @FindBy(id = "com.labstyle.darioandroid:id/plusGlucoseLayout")
    public WebElement plusBloodSugarOption;

    @FindBy(id = "com.labstyle.darioandroid:id/fetValue")
    public WebElement avgBloodGlucoseValue;

    @FindBy(id = "com.labstyle.darioandroid:id/header")
    public WebElement pleaseSyncHeaderMsg;

    @FindBy(id = "com.labstyle.darioandroid:id/button_ok")
    public WebElement pleaseSyncMsgOkBtn;


    public DarioAppMainPage(MobileDriver mobileDriver) {
        super(mobileDriver);
    }

    public DarioDataEntryPage takeMeasurement(MeasurementType measurementType) {
//       dismissSyncMsgIfShown();
        wait.until(ExpectedConditions.visibilityOf(plusBtn)).click();
        switch (measurementType) {
            case BLOOD_SUGAR:
                wait.until(ExpectedConditions.visibilityOf(plusBloodSugarOption)).click();
                printToLog("Taking " + measurementType.toString() + " measurement");
                break;
            case BLOOD_PRESSURE:
                break;
            case WEIGHT:
                break;
            default:
        }
        return new DarioDataEntryPage(mobileDriver);
    }

    public int getAvgBloodGlucoseValue() {
        if (avgBloodGlucoseValue.isDisplayed()) {
            printToLog("Blood Glucose Value: " + avgBloodGlucoseValue.getText());
            return Integer.parseInt(avgBloodGlucoseValue.getText());
        } else {
            printToLog("Unable fo get avg Blood Glucose Value");
        }
        return -1;
    }

    public DarioStatisticsPage openStatisticsPage() {
        avgBloodGlucoseValue.click();
        return new DarioStatisticsPage(mobileDriver);
    }

    public void dismissSyncMsgIfShown(){
        try{
            if (pleaseSyncHeaderMsg.isDisplayed()){
                pleaseSyncMsgOkBtn.click();
                printToLog("Sync msg was dismissed");
            }
        } catch (NoSuchElementException exception){
            // Do nothing
        }
    }


}
