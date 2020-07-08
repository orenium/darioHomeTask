package utils;

import il.co.topq.difido.ReportDispatcher;
import il.co.topq.difido.ReportManager;
import il.co.topq.difido.model.Enums;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.android.nativekey.KeyEventMetaModifier;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.*;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestRunner {

    private static final String REPORTING_FILE_PATH = "/Users/orenbroshi/IdeaProjects/darioHomeTask/test-output/difido/current/index.html";
    public static ReportDispatcher report = ReportManager.getInstance();

    public static void takeScreenShot(WebDriver driver) {
        try {
            File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            report.addImage(scrFile, "Screenshot: " + getCurrentTimeStampAsStrings());
        } catch (Exception e) {
            report.log(e.getMessage(), Enums.Status.error);
        }
    }

    private static String getCurrentTimeStampAsStrings() {
        LocalDateTime ldt = new LocalDateTime();
        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy, MMMM dd, HH:mm:ss");
        return fmt.print(ldt);
    }

    public static void openHtmlReportFile(boolean quitDriver, WebDriver driver) {
        try {
            File htmlFile = new File(REPORTING_FILE_PATH);
            Desktop.getDesktop().browse(htmlFile.toURI());
            if (quitDriver && driver != null) {
                driver.quit();
            }
        } catch (Exception ex) {
            printToLog("BaseTest.openReportHtmlFile: " + ex.getMessage());
        }
    }

    public static void printToLog(String content) {
        report.log(content);
    }

    public static List<String> getAllConnectedDevicesUdid() {
        String line;
        List<String> connectedDevices = new ArrayList<String>();
        try {
            Runtime rt = java.lang.Runtime.getRuntime();
            // Start a new process: UNIX command ls
            Process p = rt.exec("adb devices");
            // Get process’ output: its InputStream
            InputStream is = p.getInputStream();
            BufferedReader reader = new java.io.BufferedReader(new InputStreamReader(is));
            // And print each line
            line = (reader.readLine());
            while ((line = reader.readLine()) != null) {
                if (line.contains("device")) {
                    connectedDevices.add((line.split(" ")[0]).split("\t")[0]);
                }
            }
            is.close();

        } catch (Exception ex) {
            printToLog("TestRunner.getAllConnectedDevices: " + ex.getMessage());
        }
        return connectedDevices;
    }

    public static String getConnectedDeviceName() {
        String line;
        String connectedDeviceName = "";
        try {
            Runtime rt = java.lang.Runtime.getRuntime();
            // Start a new process: UNIX command ls
            Process p = rt.exec("adb devices -l");
            // Get process’ output: its InputStream
            InputStream is = p.getInputStream();
            BufferedReader reader = new java.io.BufferedReader(new InputStreamReader(is));
            // And print each line
            line = (reader.readLine());
            while ((line = reader.readLine()) != null) {
                if (line.contains("product")) {
                    connectedDeviceName = line.split(" ")[19].substring(7);
                }
            }
            is.close();

        } catch (Exception ex) {
            printToLog("TestRunner.getConnectedDeviceName: " + ex.getMessage());
        }
        return connectedDeviceName;
    }

    public static void sendKeysAsChars(WebElement webElement, String str) {
        if (webElement != null && str.length() != 0) {
            for (int i = 0; i < str.length(); i++) {
                char c = str.charAt(i);
                String s = new StringBuilder().append(c).toString();
                webElement.sendKeys(s);
            }
        }
    }

    public static String getConnectedDeviceOsVersion() {
        String line;
        String osVersion = "";
        try {
            Runtime rt = java.lang.Runtime.getRuntime();
            // Start a new process: UNIX command ls
            Process p = rt.exec("adb shell getprop ro.build.version.release");
            // Get process’ output: its InputStream
            InputStream is = p.getInputStream();
            BufferedReader reader = new java.io.BufferedReader(new InputStreamReader(is));
            // And print each line
            line = (reader.readLine());
            osVersion = line;
            if (osVersion.length() > 0) {
//                printToLog("Android version: " + osVersion);
            }
            is.close();
        } catch (Exception ex) {
            printToLog("TestRunner.getConnectedDeviceOsVersion: " + ex.getMessage());
        }
        return osVersion;
    }

    public static int getCharKeyCode(String str) {
        KeyStroke ks = null;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            ks = KeyStroke.getKeyStroke(c, 0);
            System.out.println(c + " : " + ks.getKeyCode());
        }
        return ks.getKeyCode();
    }

    public static void sendKeyByVirtualKeyboard(MobileDriver driver) throws InterruptedException {
        //todo: change this to be generic
        if (driver != null) {
            ((AndroidDriver) driver).pressKey(new KeyEvent(AndroidKey.O).withMetaModifier(KeyEventMetaModifier.SHIFT_ON));
            ((AndroidDriver) driver).pressKey(new KeyEvent(AndroidKey.R));
            ((AndroidDriver) driver).pressKey(new KeyEvent(AndroidKey.E));
            ((AndroidDriver) driver).pressKey(new KeyEvent(AndroidKey.N));
            ((AndroidDriver) driver).pressKey(new KeyEvent(AndroidKey.B));
            ((AndroidDriver) driver).pressKey(new KeyEvent(AndroidKey.DIGIT_1));
            ((AndroidDriver) driver).pressKey(new KeyEvent(AndroidKey.DIGIT_9));
            ((AndroidDriver) driver).pressKey(new KeyEvent(AndroidKey.DIGIT_8));
            ((AndroidDriver) driver).pressKey(new KeyEvent(AndroidKey.DIGIT_3));
        }

    }

    public static Point getElementLocation(WebElement element) {
        Point location = null;
        if (element != null) {
            location = element.getLocation();
        }
        return location;
    }

    public static Dimension getElementDimensions(WebElement element) {
        Dimension elementSize = null;
        if (element != null) {
            elementSize = element.getSize();
        }
        return elementSize;
    }

    public static Point getElementCenterPoint(WebElement element) {
        Point centerPoint = null;
        try {
            if (element != null) {
                Point elementLocation = element.getLocation();
                Dimension elementSize = element.getSize();
                centerPoint = new Point(elementLocation.getX() + elementSize.width / 2, elementLocation.getY() + elementSize.height / 2);
            }
        } catch (Exception ex) {
            printToLog("TestRunner.getElementCenterPoint: " + ex.getMessage());
        }
        return centerPoint;
    }

    public static SwipeDirection getRandomSwipeDirection() {

        SwipeDirection[] directions = SwipeDirection.values();
        Random random = new Random();
        SwipeDirection direction;
        do {
            direction = directions[random.nextInt(directions.length)];
        } while (direction == SwipeDirection.UP || direction == SwipeDirection.DOWN);
        return direction;

    }


}
