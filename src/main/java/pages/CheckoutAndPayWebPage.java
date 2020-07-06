package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static utils.TestRunner.printToLog;

public class CheckoutAndPayWebPage extends BaseWebPage {

    @FindBy(css = "button#place_order")
    public WebElement submitOrderBtn;

    @FindAll({
            @FindBy(css = "tr.order-total td strong span")})
    public List<WebElement> totalOrderSum;    // 1st item in the list

    private static final By selectPayPalBtn = By.cssSelector("label[for='payment_method_paypal']");

    public CheckoutAndPayWebPage(WebDriver driver) {
        super(driver);
        waitForPageToLoad(selectPayPalBtn);
    }

    public boolean selectPayPalAsPaymentMethod() {
        boolean isSelected = false;

        try {
            WebDriverWait wait = new WebDriverWait(driver, 10);
            wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector("div.blockUI.blockOverlay"))));
            wait.until(ExpectedConditions.elementToBeClickable(selectPayPalBtn)).click();
            printToLog("PayPal was selected as payment method");
            isSelected = true;
        } catch (Exception ex) {
            printToLog("CheckoutAndPayPage.selectPayPal: " + ex.getMessage());
        }
        return isSelected;
    }

    public boolean submitOrder() {
        boolean isSubmitted = false;
        try {
            wait.until(ExpectedConditions.visibilityOfAllElements(totalOrderSum));
            wait.until(ExpectedConditions.visibilityOf(submitOrderBtn));
            if (submitOrderBtn.isDisplayed()) {
                submitOrderBtn.click();
                printToLog("*********************************");
                printToLog("Order submitted!");
                if (totalOrderSum.size() > 0) {
                    printToLog("Total sum: " + totalOrderSum.get(0).getText());
                }
                printToLog("*********************************");
                isSubmitted = true;
                Thread.sleep(5000);
            }
        } catch (Exception ex) {
            printToLog("CheckoutAndPayPage.submitOrder: " + ex.getMessage());
        }
        return isSubmitted;
    }

}
