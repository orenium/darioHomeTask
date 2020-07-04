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

import java.net.URL;
import java.util.concurrent.TimeUnit;

import static utils.TestRunner.*;

@Listeners(il.co.topq.difido.ReportManagerHook.class)
public class BaseTest implements Action {

    public WebDriver driver = null;
    public MobileDriver mobileDriver;
    private static final String APPIUM_LOCALHOST_URL = "http://localhost:4723/wd/hub";
    private static final String APP_PATH = "/Users/orenbroshi/IdeaProjects/darioHomeTask/src/main/resources/base.apk";
    //    private static final String APP_PATH = "/Users/obroshi/Documents/DarioHomeTask/src/main/resources/apps.zip";
    private static final String APP_PACKAGE = "com.android.vending";
    private static final String APP_ACTIVITY = "com.google.android.finsky.activities.MainActivity";
    boolean isRealDevice = Boolean.parseBoolean(System.getProperty("isRealDevice"));

    @BeforeClass
    public void setup() {
//        driver = getNewChromeWebDriver();
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
                capabilities.setCapability(AndroidMobileCapabilityType.AVD, "Pixel_2");
            }
            capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, Platform.ANDROID);
            capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.ANDROID_UIAUTOMATOR2);
            capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, APP_PACKAGE);
//            capabilities.setCapability(MobileCapabilityType.APP, APP_PATH);
            capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, APP_ACTIVITY);
//            capabilities.setCapability(AndroidMobileCapabilityType.APPLICATION_NAME, " ");
//            capabilities.setCapability(AndroidMobileCapabilityType.BROWSER_NAME, "Chrome");
//            capabilities.setCapability("chromedriverExecutable", "/Users/orenbroshi/IdeaProjects/darioHomeTask/src/main/resources/chromedriver");
//            capabilities.setCapability(MobileCapabilityType.NO_RESET, false);

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
            openHtmlReportFile(true, driver);
        }

        if (mobileDriver != null) {
            takeScreenShot(mobileDriver);
            openHtmlReportFile(true, mobileDriver);
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
