package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

import static utils.TestRunner.printToLog;
import static utils.TestRunner.takeScreenShot;

public class CartInfoWebPage extends BaseWebPage {
    @FindAll({
            @FindBy(css = "strong")})
    public List<WebElement> summaryText;

    @FindBy(css = "button#place_order")
    public WebElement placeOrderBtn;

    @FindAll({
            @FindBy(css = "span.woocommerce-input-wrapper.new-errors")})
    public List<WebElement> shippingAddressErrorMsgs;

    @FindBy(css = "input#terms")
    public WebElement acceptTermsCheckbox;

    @FindBy(css = "input#billing_first_name")
    public WebElement inputFirstName;

    @FindBy(css = "input#billing_last_name")
    public WebElement inputLastName;

    @FindBy(css = "input#billing_address_1")
    public WebElement inputAddress;

    @FindBy(css = "input#billing_address_2")
    public WebElement inputAddress2;

    @FindBy(css = "input#billing_city")
    public WebElement inputCity;

    @FindBy(css = "input#billing_postcode")
    public WebElement inputZipcode;

    @FindBy(css = "input#billing_email")
    public WebElement inputEmail;

    @FindBy(css = "input#billing_cell_phone")
    public WebElement inputpPhone;

    @FindBy(css = "span#select2-billing_state-container")
    public WebElement stateOptionSelection;

    @FindAll({
            @FindBy(css = "div[data-title='Product']")})
    public List<WebElement> productsInCart;




    private WebDriverWait wait;
    private int productsCounter = 0;

    public CartInfoWebPage(WebDriver driver) {
        super(driver);
        wait = new WebDriverWait(driver, 10);
        waitForPageToLoad(new By.ByCssSelector("button#place_order"));
        printCartSummaryText();
    }

    public void printCartSummaryText() {
        try {
            wait.until(ExpectedConditions.visibilityOfAllElements(productsInCart));
            if (summaryText.size() > 0) {
                printToLog(" ********************************* ");
                printToLog(summaryText.get(2).getText() + ":");
                for (String productName : getProductsNamesInCart()) {
                    productsCounter++;
                    printToLog(productName.replaceAll("\r\n", " "));
                }
                printToLog(" ********************************* ");
            }
        } catch (Exception ex) {
            printToLog("CartInfoPage.printCartSummaryText: " + ex.getMessage());
        }
    }

    public int getItemsInCartCounter() {
        return productsCounter;
    }

    public CheckoutAndPayWebPage placeOrder() {
        CheckoutAndPayWebPage checkoutAndPayPage = null;
        try {
            wait.until(ExpectedConditions.visibilityOf(placeOrderBtn)).click();
            if (isVerifiedInputs()) {
                printToLog("All fields are ok! Submitting order...");
                takeScreenShot(driver);
                checkoutAndPayPage = new CheckoutAndPayWebPage(driver);
            } else {
                printToLog("Unable to complete order, some inputs are invalid!");
            }
        } catch (Exception ex) {
            printToLog("CartInfoPage.placeOrder: " + ex.getMessage());
        }
        return checkoutAndPayPage;
    }

    private boolean isVerifiedInputs() {
        boolean isValidInputs = false;
        try {
            if (shippingAddressErrorMsgs.size() > 0) {
                printToLog("There are still " + shippingAddressErrorMsgs.size() + " invalid inputs");
                for (WebElement element : shippingAddressErrorMsgs) {
                    printToLog(element.getAttribute("data-error-meesage"));
                }
            } else {
                isValidInputs = true;
            }

        } catch (Exception ex) {
            printToLog("CartInfoPage.isVerifiedInputs: " + ex.getMessage());
        }
        return isValidInputs;
    }

    public boolean acceptTerms() {
        try {
            moveToElement(acceptTermsCheckbox);
            if (!acceptTermsCheckbox.isSelected()) {
                acceptTermsCheckbox.click();
                printToLog("Terms and conditions were accepted");
            } else {
                printToLog("Checkbox is already check");
            }
        } catch (Exception ex) {
            printToLog("CartInfoPage.acceptTerms: " + ex.getMessage());
        }
        return acceptTermsCheckbox.isSelected();
    }

    public void insertValidDataAll() {
        String errorMsg = "";
        try {
            if (shippingAddressErrorMsgs.size() > 0) {
                printToLog("Completing required data..");
                for (WebElement element : shippingAddressErrorMsgs) {
                    errorMsg = element.getAttribute("data-error-meesage");
                    insertValidDataField(errorMsg);
                }
            }

        } catch (Exception ex) {
            printToLog("CartInfoPage.insertValidData: " + ex.getMessage());
        }
    }

    private void insertValidDataField(String errorMsg) {

        WebElement stateInput;
        try {
            if (errorMsg.equalsIgnoreCase("First Name is required")) {
                inputFirstName.sendKeys("Lisa");
            } else if (errorMsg.equalsIgnoreCase("Last Name is required")) {
                inputLastName.sendKeys("Dumpty");
            } else if (errorMsg.equalsIgnoreCase("Address is required")) {
                inputAddress.sendKeys("5723 Morgan Ave");
                inputAddress2.sendKeys("6");
            } else if (errorMsg.equalsIgnoreCase("City / Town is required")) {
                inputCity.sendKeys("Los Angeles");
            } else if (errorMsg.equalsIgnoreCase("State is required")) {
                stateOptionSelection.click();
                stateInput = driver.findElement(By.cssSelector("input.select2-search__field"));
                stateInput.click();
                stateInput.sendKeys("california");
                driver.findElement(By.cssSelector("li.select2-results__option")).click();
            } else if (errorMsg.equalsIgnoreCase("Zip Code is required")) {
                inputZipcode.sendKeys("90011");
            } else if (errorMsg.equalsIgnoreCase("Email is required")) {
                inputEmail.sendKeys("obrodi20@gmail.com");
            } else if (errorMsg.equalsIgnoreCase("Cell Phone is required")) {
                inputpPhone.sendKeys("5676533");
            }
            Thread.sleep(700);
        } catch (Exception ex) {
            printToLog("CartInfoPage.insertValidDataField: " + ex.getMessage());
        }
    }

    public List<String> getProductsNamesInCart() {
        List<String> productsNames = new ArrayList<>();
        try {
            if (productsInCart.size() > 0) {
                for (WebElement productName : productsInCart) {
                    productsNames.add(productName.getText());
                }
            } else {
                printToLog("It seems like cart is empty");
            }
        } catch (Exception ex) {
            printToLog("CartInfoPage.getProductsNamesInCart: " + ex.getMessage());
        }
        return productsNames;
    }
}



