package pages;

import io.appium.java_client.MobileDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static utils.TestRunner.printToLog;

public class DarioStatisticsPage extends BasePage {

    @FindBy(id = "com.labstyle.darioandroid:id/stat_measurement_hypos")
    public WebElement hyposSquareBtn;

    @FindBy(id = "com.labstyle.darioandroid:id/statistics_details_close_button")
    public WebElement closeWindowBtn;

    @FindAll({
            @FindBy(id = "com.labstyle.darioandroid:id/statistics_detail_row_value")})
    public List<WebElement> insertedValues;

    @FindAll({
            @FindBy(className = "android.widget.LinearLayout")})
    public List<WebElement> linearLayoutViews;




    public DarioStatisticsPage(MobileDriver mobileDriver) {
        super(mobileDriver);
    }

    public int getLastInsertedValue() {
        try {
            hyposSquareBtn.click();
            if (insertedValues.size() > 0) {
                printToLog("Last inserted value: " + insertedValues.get(0).getText());
                return Integer.parseInt(insertedValues.get(0).getText());
            } else {
                printToLog("Unable to find inserted values");
            }
        } catch (Exception ex) {
            printToLog("DarioStatisticsPage.getLastInsertedValue: " + ex.getMessage());
        }
        return -1;
    }

    public void closeStatisticsWindow(){
        if (closeWindowBtn.isDisplayed()){
            closeWindowBtn.click();
            printToLog("Statistics window was closed");
        }

    }


}
