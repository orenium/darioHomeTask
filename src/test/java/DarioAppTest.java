import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
import utils.MeasurementType;

public class DarioAppTest extends BaseTest {


    private DarioAppMainPage appMainPage;
    private final String EMAIL = System.getProperty("loginEmail");
    private final String PASS = System.getProperty("loginPassword");
    private final String FB_EMAIL = System.getProperty("facebookEmail");
    private final String FB_PASS = System.getProperty("facebookPassword");

    private int lastValueEntered = -1;
    private int lastValueRecorded = -1;

    @Test(description = "This test starts at the Google play store and download / open the dario app")
    public void darioAppTest() throws InterruptedException {
//        googleHomePage = new GoogleHomePage(mobileDriver);
//        resultsPage = googleHomePage.runGoogleSearch("dario app");
//        googlePlayAppPage = resultsPage.openAppPageInGooGePlay();
//        Assert.assertTrue(googlePlayAppPage.installApp());

        GooglePlayMainPage googlePlayAppPage = new GooglePlayMainPage(mobileDriver);
        GooglePlaySearchResultsPage resultsPage = googlePlayAppPage.searchAnApp("dario");
        GooglePlayAppPage appPage = resultsPage.selectDarioApp();
        if (appPage.installOpenApp()) {
            DarioWelcomePage welcomePage = new DarioWelcomePage(mobileDriver);
            DarioLoginPage loginPage = welcomePage.swipeTutorial();

            LoginWIthFacebookPage facebookPage = loginPage.loginWithFb();
            appMainPage = facebookPage.loginWithFacebook(FB_EMAIL, FB_PASS);
            Thread.sleep(3000);
            appMainPage.acceptAlertsIfShown(3);
            DarioDataEntryPage dataEntryPage = appMainPage.takeMeasurement(MeasurementType.BLOOD_SUGAR);
            lastValueEntered = dataEntryPage.getValue();
            appMainPage = dataEntryPage.ClickDoneButton();

//            dataEntryPage = appMainPage.takeMeasurement(MeasurementType.BLOOD_SUGAR);
//            appMainPage = dataEntryPage.addNewValue();
        }
        DarioStatisticsPage statisticsPage = appMainPage.openStatisticsPage();
        lastValueRecorded = statisticsPage.getLastInsertedValue();
        System.out.println("lastValueEntered: "+ lastValueEntered);
        System.out.println("lastValueRecorded: "+ lastValueRecorded);
        System.out.println(lastValueEntered != (-1));
        System.out.println(lastValueEntered == lastValueRecorded);
        Assert.assertTrue((lastValueEntered != (-1)) && (lastValueEntered == lastValueRecorded));
    }

//    @Test (description = "This test take measurement when app is already installed")
//    public void addMeasurementTest(){
//
//    }


}
