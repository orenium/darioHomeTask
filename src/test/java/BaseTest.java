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
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

import static utils.TestRunner.*;

@Listeners(il.co.topq.difido.ReportManagerHook.class)
public class BaseTest implements Action {

    public WebDriver driver = null;
    public MobileDriver mobileDriver;
    private static final String APPIUM_LOCALHOST_URL = "http://localhost:4723/wd/hub";
    private static final String GOOGLE_PLAY_APP_PACKAGE = "com.android.vending";
    private static final String GOOGLE_PLAY_APP_ACTIVITY = "com.google.android.finsky.activities.MainActivity";
    private static final String AVD = "Pixel_2";
    private static final String ANDROID_VERSION = "9";
    private static final String EMULATOR_UDID = "emulator-5554";
    private boolean isRealDevice = Boolean.parseBoolean(System.getProperty("isRealDevice"));
    private boolean unInstallApp = Boolean.parseBoolean(System.getProperty("unInstallApp"));

    @BeforeSuite
    public void setup() {
        if (System.getProperty("testType").equalsIgnoreCase("web")) {
            driver = getNewChromeWebDriver();
        } else {
            if (unInstallApp) {
                uninstallDarioApp();
            } else {
                clearDarioAppDate();
            }
            mobileDriver = getNewAndroidDriver();
        }
//        addRunProperties();
    }

    private void addRunProperties() throws UnknownHostException {
        if (!System.getProperty("testType").equalsIgnoreCase("web")) {
            report.addRunProperty("Device name", AVD);
        }
        report.addRunProperty("Operating System", System.getProperty("os.name"));
        report.addRunProperty("Local machine", InetAddress.getLocalHost().getHostName());
    }

    private AndroidDriver getNewAndroidDriver() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        try {
            printToLog("Setting Android mobile driver...");
            capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, Platform.ANDROID);
            capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.ANDROID_UIAUTOMATOR2);
            capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, GOOGLE_PLAY_APP_PACKAGE);
            capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, GOOGLE_PLAY_APP_ACTIVITY);
            if (isRealDevice) {
                // get the 1st connected phone
                if (getAllConnectedDevicesUdid().size() > 0) {
                    printToLog("Connected Device: " + getConnectedDeviceName() + " | Android " + getConnectedDeviceOsVersion());
                    capabilities.setCapability(MobileCapabilityType.UDID, getAllConnectedDevicesUdid().get(0));
                } else {
                    printToLog("-- ANDROID DEVICE IS NOT CONNECTED  --");
                }
            } else {   // Emulator
                capabilities.setCapability(MobileCapabilityType.UDID, EMULATOR_UDID);
                capabilities.setCapability(AndroidMobileCapabilityType.AVD, AVD);
                capabilities.setCapability(AndroidMobileCapabilityType.VERSION, ANDROID_VERSION);
                printToLog("Device: " + AVD + " | Android " + ANDROID_VERSION);
            }
            mobileDriver = new AndroidDriver(new URL(APPIUM_LOCALHOST_URL), capabilities);
            mobileDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        } catch (Exception ex) {
            printToLog("BaseTest.getNewAndroidDriver: " + ex.getMessage());
        }
        return (AndroidDriver) mobileDriver;
    }

    private WebDriver getNewChromeWebDriver() {
        printToLog("Setting new ChromeWebDriver...");
        WebDriverManager.chromedriver().setup();
        WebDriver WebDriver = new ChromeDriver();
//        Capabilities capabilities = ((RemoteWebDriver) WebDriver).getCapabilities();
//        report.addRunProperty("Browser", capabilities.getBrowserName() + " " + capabilities.getVersion());
        return WebDriver;
    }


    @AfterClass
    public void afterClass() {
        if (driver != null) {
            takeScreenShot(driver);
        }
        if (mobileDriver != null) {
            takeScreenShot(mobileDriver);
        }
    }

    @AfterSuite
    public void afterSuite() {
        openHtmlReportFile(true, driver);
        if (!isRealDevice) {
//            quiteMobileEmulator();
        }
    }

    private void quiteMobileEmulator() {
        try {
            Runtime runtime = Runtime.getRuntime();
            runtime.exec("adb -s emulator-5554 emu kill");
        } catch (IOException e) {
            printToLog("BaseTesl.clearDarioAppDate: " + e.getMessage());
        }

    }

    private void clearDarioAppDate() {
        try {
            Runtime runtime = Runtime.getRuntime();
            runtime.exec("adb shell pm clear com.labstyle.darioandroid");
            printToLog("Dario app data was cleared...");
        } catch (IOException e) {
            printToLog("BaseTesl.clearDarioAppDate: " + e.getMessage());
        }
    }

    private void uninstallDarioApp() {
        try {
            Runtime runtime = Runtime.getRuntime();
            runtime.exec("adb uninstall com.labstyle.darioandroid");
            printToLog("Dario app was removed from device");
        } catch (IOException e) {
            printToLog("BaseTesl.clearDarioAppDate: " + e.getMessage());
        }
    }


    public void perform() {

    }

    public void openURL(String url) {
        if (driver == null) {
            driver = getNewChromeWebDriver();
        }
        driver.get(url);
        printToLog("Opening Url: " + url);
    }
}
