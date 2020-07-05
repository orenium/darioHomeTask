package pages;

import io.appium.java_client.MobileDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static utils.TestRunner.printToLog;
import static utils.TestRunner.sendKeyByVirtualKeyboard;

public class DarioLoginPage extends BasePage {

    @FindAll({
            @FindBy(className = "android.widget.EditText")})
    public List<WebElement> inputFields;

    @FindBy(id = "com.labstyle.darioandroid:id/get_started_connect_with_facebook_button")
    public WebElement loginWithFbBtn;

    public DarioLoginPage(MobileDriver mobileDriver) {
        super(mobileDriver);
    }

    public boolean login(String email, String pass) throws InterruptedException {
        boolean isLoggedIn = false;
        if (inputFields.size() > 0) {
            inputFields.get(0).click();
            inputFields.get(0).sendKeys(email);
            System.out.println(inputFields.get(1).getText());
            inputFields.get(1).clear();
            inputFields.get(1).click();
//            inputFields.get(1).sendKeys(pass);
            sendKeyByVirtualKeyboard(mobileDriver);
            Thread.sleep(3000);
//            sendKeysAsChars(inputFields.get(1),pass);

            List<WebElement> frameLayouts = mobileDriver.findElements(By.className("android.widget.FrameLayout"));
            printToLog("Logging in with email: " + email + "  |   Password: " + pass);
            frameLayouts.get(5).click();   // Login button
            isLoggedIn = true;
        }
        return isLoggedIn;
    }

    public LoginWIthFacebookPage loginWithFb(){
        loginWithFbBtn.click();
        return new LoginWIthFacebookPage(mobileDriver);
    }




}
