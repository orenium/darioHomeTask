package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Random;

import static utils.TestRunner.printToLog;

public class DarioShopHomePage extends BaseWebPage {

    @FindAll({
            @FindBy(css = "section.section-products.bg-gray [class='add-to-cart-button']")})
    public List<WebElement> addToCartButtons;

    @FindAll({
            @FindBy(css = "section.section-products.bg-gray a.button.product_type_simple.remove_from_cart_button")})
    public List<WebElement> removeItemFromCartButtons;

    @FindBy(css = "span.cart-content-num")
    public WebElement itemsInCartCounter;

    @FindBy(css = "div.cart-content > a > i")
    public WebElement cartIcon;

    @FindAll({
            @FindBy(css = "label.btn.change-meter-popup.flex-grow-1.mx-2.px-0.pb-0")})
    public List<WebElement> connectorTypeOption;

    public DarioShopHomePage(WebDriver driver) {
        super(driver);
    }

    public void addRandomProductToCart() {
        Random random = new Random();
        int selectedIndex = -1;
        String itemName = "";
        String buttonText = "";
        try {
            if (addToCartButtons.size() > 0) {
                getNumItemsInCart();
                selectedIndex = random.nextInt(addToCartButtons.size());
                if (addToCartButtons.get(selectedIndex).getText().equalsIgnoreCase("add")) {
                    moveToElement(addToCartButtons.get(selectedIndex));
                    if (selectedIndex == 0) {
                        addToCartButtons.get(selectedIndex).click();
                        selectedIndex = random.nextInt(connectorTypeOption.size());
                        wait.until(ExpectedConditions.elementToBeClickable(connectorTypeOption.get(selectedIndex))).click();
                        driver.findElements(By.cssSelector("div.modal-body div div div.add-to-cart-button-switcher")).get(selectedIndex).click();
                        printToLog(driver.findElements(By.cssSelector("div.modal-body span.d-block.mt-2.py-2")).get(selectedIndex).getText()
                                + " was selected");
                        Thread.sleep(5000);
                        driver.findElement(By.cssSelector("div.modal-body button.close")).click();  // Close the selection window
                    } else {
                        itemName = driver.findElements(By.cssSelector("div.accordion button")).get(selectedIndex).getText();
                        addToCartButtons.get(selectedIndex).click();
                        Thread.sleep(5000);
                        printToLog(itemName + " was added to the cart ");
                        buttonText = addToCartButtons.get(selectedIndex).getText();
                        if (buttonText.equalsIgnoreCase("remove")) {
                            printToLog("Item: " + itemName + " was successfully added to cart");
                        }
                    }
                }
            }

        } catch (Exception ex) {
            printToLog("DarioShopHomePage.addRandomProductToCart: " + ex.getMessage());
        }

    }

    public int getNumItemsInCart() {
        int cartItems = Integer.parseInt(itemsInCartCounter.getText());
        printToLog("The cart currently contains " + cartItems + " item/s");
        return cartItems;
    }

    public CartInfoPage openCartInfoPage() {
        if (cartIcon.isDisplayed()) {
            cartIcon.click();
        }
        return new CartInfoPage(driver);
    }


}
