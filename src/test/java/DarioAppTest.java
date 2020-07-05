import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
import utils.MeasurementType;

public class DarioAppTest extends BaseTest {

    GooglePlayMainPage googlePlayAppPage;
    GooglePlaySearchResultsPage resultsPage;
    GooglePlayAppPage appPage;
    DarioWelcomePage welcomePage;
    DarioLoginPage loginPage;
    LoginWIthFacebookPage facebookPage;
    DarioAppMainPage appMainPage;
    DarioDataEntryPage dataEntryPage;

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
//        appPage = resultsPage.selectDarioApp();
//        if (appPage.installApp()) {
//            welcomePage = new DarioWelcomePage(mobileDriver);
//            loginPage = welcomePage.swipeTutorial();
//        }
//        facebookPage = loginPage.loginWithFb();
//        appMainPage = facebookPage.loginWithFacebook("oren.broshi@gmail.com", "984350Ob1");
//        dataEntryPage = appMainPage.takeMeasurement(MeasurementType.BLOOD_SUGAR);
//        appMainPage = dataEntryPage.addNewValue();
//        Assert.assertTrue(appMainPage.getAvgBloodGlucoseValue() > 0);
    }


}
