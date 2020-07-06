package pages;

import io.appium.java_client.MobileDriver;
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

    public DarioAppMainPage(MobileDriver mobileDriver) {
        super(mobileDriver);
    }

    public DarioDataEntryPage takeMeasurement(MeasurementType measurementType) {
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


}
