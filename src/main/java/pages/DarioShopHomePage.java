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
    private List<WebElement> addToCartButtons;

    @FindBy(css = "span.cart-content-num")
    private WebElement itemsInCartCounter;

    public DarioShopHomePage(WebDriver driver) {
        super(driver);
    }

    public void addRandomProductToCart() {
        WebDriverWait wait = new WebDriverWait(driver,10);
        Random random = new Random();
        int selectedIndex = -1;
        int itemsBeforeAdding = -1;
        int itemsAfterAdding = -1;
        try {
            if (addToCartButtons.size() > 0) {
                itemsBeforeAdding = getNumItemsInCart();
                selectedIndex = random.nextInt(addToCartButtons.size());
                addToCartButtons.get(selectedIndex).click();
                printToLog("product index " + selectedIndex + " was added to the cart: ");
                printToLog(driver.findElements(By.cssSelector("div.accordion button")).get(selectedIndex).getText());
                wait.until(ExpectedConditions.textToBePresentInElementValue(By.cssSelector("button product_type_simple remove_from_cart_button"), String.valueOf(itemsBeforeAdding++)));
                printToLog(String.valueOf(getNumItemsInCart()));
            }

        } catch (Exception ex) {
            printToLog("DarioShopHomePage.addRandomProductToCart: " + ex.getMessage());
        }

    }

    public int getNumItemsInCart() {
        int cartItems = Integer.parseInt(itemsInCartCounter.getText());
        printToLog("The cart now contain " + cartItems + " item/s");
        return cartItems;
    }


}
