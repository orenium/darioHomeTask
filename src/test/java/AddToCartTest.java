import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CartInfoPage;
import pages.CheckoutAndPayPage;
import pages.DarioShopHomePage;
import utils.TestRunner;
import utils.URLs;

public class AddToCartTest extends BaseTest {
    private DarioShopHomePage darioShopHomePage;
    private CartInfoPage cartInfoPage;
    private CheckoutAndPayPage checkoutAndPayPage;

    @Test
    public void addItemToCartTest() throws InterruptedException {
        openURL(URLs.DARIO_SHOP_URL);
        darioShopHomePage = new DarioShopHomePage(driver);
        darioShopHomePage.addRandomProductToCart();

//        Assert.assertTrue(darioShopHomePage.getNumItemsInCart() > 0);

        cartInfoPage = darioShopHomePage.openCartInfoPage();
        cartInfoPage.printCartSummaryText();
        cartInfoPage.placeOrder();
        cartInfoPage.insertValidDataAll();
        cartInfoPage.acceptTerms();
        checkoutAndPayPage = cartInfoPage.placeOrder();
        checkoutAndPayPage.selectPaypal();
        Assert.assertTrue(checkoutAndPayPage.submitOrder());

    }


}
