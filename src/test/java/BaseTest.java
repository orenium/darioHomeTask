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

import java.net.URL;
import java.util.concurrent.TimeUnit;

import static utils.TestRunner.printToLog;

@Listeners(il.co.topq.difido.ReportManagerHook.class)
public class BaseTest implements Action {

    public WebDriver driver = null;
    public MobileDriver mobileDriver;
    private static final String APPIUM_LOCALHOST_URL = "http://localhost:4723/wd/hub";
//    private static final String APP_PATH = "/Users/obroshi/Documents/DarioHomeTask/src/main/resources/Dario.apk";
    private static final String APP_PATH = "/Users/obroshi/Documents/DarioHomeTask/src/main/resources/apps.zip";
    private static final String APP_PACKAGE = "com.labstyle.darioandroid";
    private static final String APP_ACTIVITY = "net.hockyapp.android.LoginActivity";

    @BeforeClass
    public void setup() {

//        driver = getNewChromeWebDriver();
        mobileDriver = getNewAndroidDriver();

    }

    private AndroidDriver getNewAndroidDriver() {
        AndroidDriver androidDriver = null;
        DesiredCapabilities capabilities = new DesiredCapabilities();
        printToLog("Setting Android mobile driver...");
        try {
            capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, Platform.ANDROID);
            capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.ANDROID_UIAUTOMATOR2);
            capabilities.setCapability(MobileCapabilityType.UDID, "emulator-5554");
            capabilities.setCapability(AndroidMobileCapabilityType.AVD, "Pixel_2");
            capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, APP_PACKAGE);
            capabilities.setCapability(MobileCapabilityType.APP, APP_PATH);
            capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, APP_ACTIVITY);

            androidDriver  = new AndroidDriver(new URL(APPIUM_LOCALHOST_URL), capabilities);
            mobileDriver.installApp("/Users/johndoe/path/to/app.apk");
            mobileDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        } catch (Exception ex) {
            printToLog("BaseTest.getNewAndroidDriver: " + ex.getMessage());
        }


        return androidDriver;
    }

    private WebDriver getNewChromeWebDriver() {
        WebDriverManager.chromedriver().setup();
        return new ChromeDriver();
    }


    @AfterClass
    public void afterClass() {
        if (driver != null) {
            driver.quit();
        }

        if (mobileDriver != null) {
            mobileDriver.quit();
        }
    }

    @Test
    public void justAtest() {
//        driver.get("https://shop.mydario.com/");
        printToLog(String.valueOf(mobileDriver.getSessionDetails()));

        assert true;
    }


    public void perform() {

    }
}
