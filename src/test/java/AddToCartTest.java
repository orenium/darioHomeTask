import org.testng.Assert;
import org.testng.annotations.Test;
import pages.DarioShopHomePage;

public class AddToCartTest extends BaseTest {

    @Test
    public void addItemToCartTest() throws InterruptedException {
        driver.get("https://shop.mydario.com/");
        DarioShopHomePage darioShopHomePage = new DarioShopHomePage(driver);
        darioShopHomePage.addRandomProductToCart();

        Assert.assertTrue(darioShopHomePage.getNumItemsInCart() > 0);
    }


}
