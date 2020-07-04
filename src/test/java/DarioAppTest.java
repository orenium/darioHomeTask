import org.testng.annotations.Test;
import pages.GooglePlayAppPage;
import pages.GooglePlayMainPage;
import pages.GooglePlaySearchResultsPage;

public class DarioAppTest extends BaseTest {
    //    private GoogleHomePage googleHomePage;
//    private GoogleSearchResultsPage resultsPage;
    private GooglePlayMainPage googlePlayAppPage;
    private GooglePlaySearchResultsPage resultsPage;
    private GooglePlayAppPage appPage;


    @Test
    public void darioAppTest() throws InterruptedException {
//        googleHomePage = new GoogleHomePage(mobileDriver);
//        resultsPage = googleHomePage.runGoogleSearch("dario app");
//        googlePlayAppPage = resultsPage.openAppPageInGooGePlay();
//        Assert.assertTrue(googlePlayAppPage.installApp());

        googlePlayAppPage = new GooglePlayMainPage(mobileDriver);
        resultsPage = googlePlayAppPage.searchAnApp("dario");
        appPage = resultsPage.selectDarioApp();
        appPage.installApp();
        System.out.println("Ended");


    }


}
