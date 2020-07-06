import io.appium.java_client.MobileDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobileCapabilityType;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import static io.appium.java_client.remote.AndroidMobileCapabilityType.AUTO_GRANT_PERMISSIONS;
import static utils.TestRunner.*;

@Listeners(il.co.topq.difido.ReportManagerHook.class)
public class BaseTest implements Action {

    public WebDriver driver = null;
    public MobileDriver mobileDriver;
    private static final String APPIUM_LOCALHOST_URL = "http://localhost:4723/wd/hub";
    private static final String APP_PATH = "/Users/orenbroshi/IdeaProjects/darioHomeTask/src/main/resources/base.apk";
    private static final String GOOGLE_PLAY_APP_PACKAGE = "com.android.vending";
    private static final String GOOGLE_PLAY_APP_ACTIVITY = "com.google.android.finsky.activities.MainActivity";
    private static final String AVD = "Pixel_2";
    private boolean isRealDevice = Boolean.parseBoolean(System.getProperty("isRealDevice"));
    private boolean unInstallApp = Boolean.parseBoolean(System.getProperty("unInstallApp"));

    @BeforeClass
    public void setup() {
//        driver = getNewChromeWebDriver();
        getRandomSwipeDirection();
        if (unInstallApp){
            uninstallDarioApp();
        } else {
            clearDarioAppDate();
        }
        mobileDriver = getNewAndroidDriver();

    }

    private AndroidDriver getNewAndroidDriver() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        printToLog("Setting Android mobile driver...");
        try {
            if (isRealDevice) {
                // get the 1st connected phone
                if (getAllConnectedDevices().size() > 0)
                    capabilities.setCapability(MobileCapabilityType.UDID, getAllConnectedDevices().get(0));
                else {
                    printToLog("-- ANDROID DEVICE IS NOT CONNECTED  --");
                }
            } else {   // Emulator
                capabilities.setCapability(MobileCapabilityType.UDID, "emulator-5554");
                capabilities.setCapability(AndroidMobileCapabilityType.AVD, AVD);
                report.addRunProperty("Device name", AVD);
            }
            capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, Platform.ANDROID);
            capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.ANDROID_UIAUTOMATOR2);
            capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, GOOGLE_PLAY_APP_PACKAGE);
            capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, GOOGLE_PLAY_APP_ACTIVITY);

            mobileDriver = new AndroidDriver(new URL(APPIUM_LOCALHOST_URL), capabilities);
            mobileDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        } catch (Exception ex) {
            printToLog("BaseTest.getNewAndroidDriver: " + ex.getMessage());
        }
        return (AndroidDriver) mobileDriver;
    }

    private WebDriver getNewChromeWebDriver() {
        WebDriverManager.chromedriver().setup();
        return new ChromeDriver();
    }


    @AfterClass
    public void afterClass() {
        if (driver != null) {
            takeScreenShot(driver);
        }
        if (mobileDriver != null) {
            takeScreenShot(mobileDriver);
        }
        openHtmlReportFile(true, driver);

    }

    private void clearDarioAppDate() {
        try {
            Runtime runtime = Runtime.getRuntime();
            runtime.exec("adb shell pm clear com.labstyle.darioandroid");
            printToLog("Dario app data was cleared...");
        } catch (IOException e) {
            printToLog("BaseTesl.clearDarioAppDate: "+ e.getMessage());
        }
    }

    private void uninstallDarioApp() {
        try {
            Runtime runtime = Runtime.getRuntime();
            runtime.exec("adb uninstall com.labstyle.darioandroid");
            printToLog("Dario app was removed from device");
        } catch (IOException e) {
            printToLog("BaseTesl.clearDarioAppDate: "+ e.getMessage());
        }
    }


    public void perform() {

    }

    public void openURL(WebDriver driver, String url) {
        if (driver != null) {
            driver.get(url);
        }
    }
}
