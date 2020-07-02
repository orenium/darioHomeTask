import org.testng.annotations.Test;
import pages.CartInfoPage;
import pages.DarioShopHomePage;

public class AddToCartTest extends BaseTest {
    DarioShopHomePage darioShopHomePage;
    CartInfoPage cartInfoPage;

    @Test
    public void addItemToCartTest() throws InterruptedException {
        driver.get("https://shop.mydario.com/");
        darioShopHomePage = new DarioShopHomePage(driver);
        darioShopHomePage.addRandomProductToCart();

//        Assert.assertTrue(darioShopHomePage.getNumItemsInCart() > 0);

        cartInfoPage = darioShopHomePage.openCartInfoPage();
        cartInfoPage.printCartSummaryText();
    }


}
