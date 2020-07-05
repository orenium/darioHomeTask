package pages;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.aspectj.apache.bcel.ExceptionConstants;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

import static utils.TestRunner.*;

public class GooglePlayMainPage extends BasePage {

    @FindBy(id = "com.android.vending:id/search_bar_hint")
    public WebElement searchBar;

    @FindBy(id = "com.android.vending:id/search_bar_text_input")
    public WebElement searchBarInput;

    public GooglePlayMainPage(MobileDriver driver) {
        super(driver);
        printToLog("Opening Google Play store");
    }

    public GooglePlaySearchResultsPage searchAnApp(String appToFind){

        try{
            printToLog("App to find: "+ appToFind);
            wait.until(ExpectedConditions.elementToBeClickable(searchBar)).click();
            wait.until(ExpectedConditions.elementToBeClickable(searchBarInput)).sendKeys(appToFind);
            if (mobileDriver instanceof AndroidDriver) {
                ((AndroidDriver) mobileDriver).pressKey(new KeyEvent(AndroidKey.ENTER));
            }
        } catch (Exception ex){
            printToLog("GooglePlayAppPage.searchAnApp: "+ ex.getMessage());
        }
        return new GooglePlaySearchResultsPage(mobileDriver);
    }


}
