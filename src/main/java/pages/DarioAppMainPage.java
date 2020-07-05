package pages;

import io.appium.java_client.MobileDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class DarioAppMainPage extends BasePage {

    @FindBy(id = "com.labstyle.darioandroid:id/plus")
    public WebElement plusBtn;

    @FindBy(id = "com.labstyle.darioandroid:id/plusGlucoseLayout")
    public WebElement plusBloodSugarOption;

    public DarioAppMainPage(MobileDriver mobileDriver) {
        super(mobileDriver);
    }

    public void takeMeasurement(){
        wait.until(ExpectedConditions.visibilityOf(plusBtn)).click();
        wait.until(ExpectedConditions.visibilityOf(plusBloodSugarOption)).click();

    }


}
