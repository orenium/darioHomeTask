package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static utils.TestRunner.printToLog;

public class CheckoutAndPayWebPage extends BaseWebPage {

    @FindBy(css = "button#place_order")
    public WebElement submitOrderBtn;

    private static final By selectPaypalBtn = By.cssSelector("label[for='payment_method_paypal']");

    public CheckoutAndPayWebPage(WebDriver driver) {
        super(driver);
        waitForPageToLoad(selectPaypalBtn);
    }

    public boolean selectPaypal() {
        boolean isSelected = false;

        try {
            WebDriverWait wait = new WebDriverWait(driver, 10);
            wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector("div.blockUI.blockOverlay"))));
            wait.until(ExpectedConditions.elementToBeClickable(selectPaypalBtn)).click();
            printToLog("Paypal was selected as payment method");
            isSelected = true;
        } catch (Exception ex) {
            printToLog("CheckoutAndPayPage.selectPaypal: " + ex.getMessage());
        }
        return isSelected;
    }

    public boolean submitOrder() {
        boolean isSubmitted = false;
        try {
            wait.until(ExpectedConditions.visibilityOf(submitOrderBtn));
            if (submitOrderBtn.isDisplayed()) {
                submitOrderBtn.click();
                isSubmitted = true;
                Thread.sleep(5000);
            }
        } catch (Exception ex) {
            printToLog("CheckoutAndPayPage.submitOrder: "+ ex.getMessage());
        }
        return isSubmitted;
    }

}
