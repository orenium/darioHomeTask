package pages;

import io.appium.java_client.MobileDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

import static utils.TestRunner.printToLog;

public class LoginWIthFacebookPage extends BasePage{

    @FindAll({
            @FindBy(className = "android.widget.EditText")})
    public List<WebElement> inputFields;

    @FindBy(className = "android.widget.Button")
    public WebElement loginBtn;

    public LoginWIthFacebookPage(MobileDriver mobileDriver) {
        super(mobileDriver);
    }

    public DarioAppMainPage loginWithFacebook(String fbEmail, String fbPassword) {
        try {
            printToLog("Logging in with Facebook");
            if (inputFields.size() > 0){
                inputFields.get(0).sendKeys(fbEmail);
                inputFields.get(1).sendKeys(fbPassword);
                loginBtn.click();
                Thread.sleep(3000);
            }
            ((AndroidDriver) mobileDriver).findElementByAndroidUIAutomator("new UiSelector().text(\"Continue\")").click();
            Thread.sleep(5000);
        } catch (Exception ex) {
            printToLog("DarioLoginPage.loginWithFacebook: " + ex.getMessage());
        }

        return new DarioAppMainPage(mobileDriver);
    }
}
