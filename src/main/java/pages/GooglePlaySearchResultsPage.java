package pages;

import io.appium.java_client.MobileDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.TestRunner;

public class GooglePlaySearchResultsPage extends BasePage{

    @FindBy(id = "com.android.vending:id/search_results_list")
    public WebElement resultsContainer;

    public GooglePlaySearchResultsPage(MobileDriver mobileDriver) {
        super(mobileDriver);
    }

    public GooglePlayAppPage selectDarioApp(){
        if (resultsContainer.isDisplayed()){
            resultsContainer.findElement(By.className("android.widget.LinearLayout")).click();
        }
        TestRunner.printToLog("Dario app was selected");
        return new GooglePlayAppPage(mobileDriver);
    }

}
