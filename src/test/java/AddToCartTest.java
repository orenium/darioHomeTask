import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CartInfoWebPage;
import pages.CheckoutAndPayWebPage;
import pages.DarioShopHomeWebPage;
import utils.URLs;

public class AddToCartTest extends BaseTest {
    private DarioShopHomeWebPage darioShopHomePage;
    private CartInfoWebPage cartInfoPage;
    private CheckoutAndPayWebPage checkoutAndPayPage;

    @Test
    public void addItemToCartTest() {
        openURL(driver, URLs.DARIO_SHOP_URL);
        darioShopHomePage = new DarioShopHomeWebPage(driver);
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
