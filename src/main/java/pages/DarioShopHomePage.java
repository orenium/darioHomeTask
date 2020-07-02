package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Random;

import static utils.TestRunner.printToLog;

public class DarioShopHomePage extends BaseWebPage {

    @FindAll({
            @FindBy(css = "section.section-products.bg-gray [class='add-to-cart-button']")})
    private List<WebElement> addToCartButtons;

    @FindAll({
            @FindBy(css = "section.section-products.bg-gray a.button.product_type_simple.remove_from_cart_button")})
    private List<WebElement> removeItemFromCartButtons;

    @FindBy(css = "span.cart-content-num")
    private WebElement itemsInCartCounter;

    @FindBy(css = "div.cart-content > a > i")
    private WebElement cartIcon;

    public DarioShopHomePage(WebDriver driver) {
        super(driver);
    }

    public void addRandomProductToCart() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
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
                    itemName = driver.findElements(By.cssSelector("div.accordion button")).get(selectedIndex).getText();
                    addToCartButtons.get(selectedIndex).click();
                    Thread.sleep(5000);
                    printToLog("product index " + selectedIndex + " was added to the cart ");
                    buttonText = addToCartButtons.get(selectedIndex).getText();
                    if (buttonText.equalsIgnoreCase("remove")) {
                        printToLog("Item: " + itemName + " was successfully added to cart");
                    } else {
                        printToLog("Failed to add " + itemName);
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
