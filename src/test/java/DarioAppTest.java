import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
import utils.MeasurementType;

import static utils.TestRunner.printToLog;
import static utils.TestRunner.report;

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

        report.startLevel("Get the app from the Google Play store");
        GooglePlayMainPage googlePlayAppPage = new GooglePlayMainPage(mobileDriver);
        GooglePlaySearchResultsPage resultsPage = googlePlayAppPage.searchAnApp("dario");
        GooglePlayAppPage appPage = resultsPage.selectDarioApp();
        appPage.installOpenApp();
        report.endLevel();

        report.startLevel("Skip tutorial and login");
        DarioWelcomePage welcomePage = new DarioWelcomePage(mobileDriver);
        DarioLoginPage loginPage = welcomePage.swipeTutorial();
        LoginWIthFacebookPage facebookPage = loginPage.loginWithFb();
        appMainPage = facebookPage.loginWithFacebook(FB_EMAIL, FB_PASS);
        Thread.sleep(3000);
        report.endLevel();

        report.startLevel("Take new BLOOD SUGAR measurement");
        appMainPage.acceptAlertsIfShown(3);
        DarioDataEntryPage dataEntryPage = appMainPage.takeMeasurement(MeasurementType.BLOOD_SUGAR);
        dataEntryPage.swipeToSetValue();
        lastValueEntered = dataEntryPage.getValue();
        appMainPage = dataEntryPage.ClickDoneButton();
        report.endLevel();

        report.startLevel("Verify value was save at the log");
        DarioStatisticsPage statisticsPage = appMainPage.openStatisticsPage();
        lastValueRecorded = statisticsPage.getLastInsertedValue();
        report.endLevel();
        printToLog("lastValueEntered: " + lastValueEntered);
        printToLog("lastValueRecorded: " + lastValueRecorded);
        Assert.assertTrue(((lastValueEntered != (-1)) && (lastValueEntered == lastValueRecorded)),
                "Test failed - Values are not equal");
    }


}
