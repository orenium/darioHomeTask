import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CartInfoWebPage;
import pages.CheckoutAndPayWebPage;
import pages.DarioShopHomeWebPage;
import utils.URLs;

import static utils.TestRunner.report;

public class AddToCartTest extends BaseTest {

    @Test
    public void addItemToCartTest() {
        report.startLevel("Open URL:" + URLs.DARIO_SHOP_URL);
        openURL(URLs.DARIO_SHOP_URL);
        DarioShopHomeWebPage darioShopHomePage = new DarioShopHomeWebPage(driver);
        report.endLevel();

        report.startLevel("Add random product to cart");
        darioShopHomePage.addRandomProductToCart();
        report.endLevel();

        report.startLevel("Open Cart page");
        CartInfoWebPage cartInfoPage = darioShopHomePage.openCartInfoPage();
        report.endLevel();

        report.startLevel("Try to place order without filling the required filed");
        cartInfoPage.placeOrder();
        report.endLevel();

        report.startLevel("Fill the required filed and place order");
        cartInfoPage.insertValidDataAll();
        cartInfoPage.acceptTerms();
        CheckoutAndPayWebPage checkoutAndPayPage = cartInfoPage.placeOrder();
        report.endLevel();

        report.startLevel("Select PayPal as payment method");
        checkoutAndPayPage.selectPayPalAsPaymentMethod();
        report.endLevel();

        report.startLevel("Submit order");
        checkoutAndPayPage.submitOrder();
        report.endLevel();

        Assert.assertTrue(driver.getCurrentUrl().contains("paypal.com/"));

    }


}
