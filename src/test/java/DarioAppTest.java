import org.testng.annotations.Test;
import pages.*;

public class DarioAppTest extends BaseTest {
    //    private GoogleHomePage googleHomePage;
//    private GoogleSearchResultsPage resultsPage;
    private GooglePlayMainPage googlePlayAppPage;
    private GooglePlaySearchResultsPage resultsPage;
    private GooglePlayAppPage appPage;
    private DarioWelcomePage welcomePage;
    private DarioLoginPage loginPage;

    private final String EMAIL = "obroshi83@gmail.com";
    private final String PASSWORD = "Orenb1983";


    @Test
    public void darioAppTest() throws InterruptedException {
//        googleHomePage = new GoogleHomePage(mobileDriver);
//        resultsPage = googleHomePage.runGoogleSearch("dario app");
//        googlePlayAppPage = resultsPage.openAppPageInGooGePlay();
//        Assert.assertTrue(googlePlayAppPage.installApp());

        googlePlayAppPage = new GooglePlayMainPage(mobileDriver);
        resultsPage = googlePlayAppPage.searchAnApp("dario");
        appPage = resultsPage.selectDarioApp();
        if (appPage.installApp()) {
            welcomePage = new DarioWelcomePage(mobileDriver);
            loginPage = welcomePage.swipeTutorial();
        }
        loginPage.login(EMAIL,PASSWORD);


    }


}
