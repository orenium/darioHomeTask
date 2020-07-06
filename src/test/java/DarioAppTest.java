import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
import utils.MeasurementType;

import static utils.TestRunner.printToLog;
import static utils.TestRunner.report;

public class DarioAppTest extends BaseTest {

    private final String FB_EMAIL = System.getProperty("facebookEmail");
    private final String FB_PASS = System.getProperty("facebookPassword");
    private int expectedValue, actualValue =-1;

    @Test
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
        DarioAppMainPage appMainPage = facebookPage.loginWithFacebook(FB_EMAIL, FB_PASS);
        Thread.sleep(3000);
        report.endLevel();

        report.startLevel("Accept all permissions");
        appMainPage.acceptAlertsIfShown(3);
        report.endLevel();

        report.startLevel("Take new BLOOD SUGAR measurement");
        DarioDataEntryPage dataEntryPage = appMainPage.takeMeasurement(MeasurementType.BLOOD_SUGAR);
        dataEntryPage.swipeToSetValue();
        expectedValue = dataEntryPage.getValue();
        appMainPage = dataEntryPage.ClickDoneButton();
        report.endLevel();

        report.startLevel("Verify value was save at the log");
        DarioStatisticsPage statisticsPage = appMainPage.openStatisticsPage();
        actualValue = statisticsPage.getLastInsertedValue();
        report.endLevel();
        printToLog("Expected Value: " + expectedValue);
        printToLog("Actual Value: " + actualValue);
        Assert.assertTrue(((expectedValue != (-1)) && (expectedValue == actualValue)),
                "Test failed - Values are not equal");
    }


}
