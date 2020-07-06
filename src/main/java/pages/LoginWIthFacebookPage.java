package pages;

import io.appium.java_client.MobileDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static utils.TestRunner.printToLog;

public class LoginWIthFacebookPage extends BasePage {

    @FindAll({
            @FindBy(className = "android.widget.EditText")})
    public List<WebElement> inputFields;

    @FindBy(className = "android.widget.Button")
    public WebElement loginBtn;

    public LoginWIthFacebookPage(MobileDriver mobileDriver) {
        super(mobileDriver);
    }

    public boolean loginWithFacebook(String fbEmail, String fbPassword) {
        int retry = 2;
        boolean isLoggedIn = false;
        try {
//            WebDriverWait wait = new WebDriverWait(mobileDriver, 20);
            while (!isLoggedIn && retry > 0) {
                if (inputFields.size() > 0) {
                    printToLog("Logging in with Facebook");
                    inputFields.get(0).sendKeys(fbEmail);
                    inputFields.get(1).sendKeys(fbPassword);
                    loginBtn.click();
                    isLoggedIn = true;
                    Thread.sleep(3000);
                } else {
                    printToLog("Failed to login with Facebook");
                }
            }
            ((AndroidDriver) mobileDriver).findElementByAndroidUIAutomator("new UiSelector().text(\"Continue\")").click();
            Thread.sleep(5000);
        } catch (Exception ex) {
            printToLog("DarioLoginPage.loginWithFacebook: " + ex.getMessage());
        }

        return isLoggedIn; // new DarioAppMainPage(mobileDriver);
    }
}