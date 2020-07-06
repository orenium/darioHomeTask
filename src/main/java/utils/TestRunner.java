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
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

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

    public static java.util.List<String> getAllConnectedDevices() {
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
                    connectedDevices.add(line.split(" ")[0]);
                }
            }
            is.close();

        } catch (Exception ex) {
            printToLog("BaseTest.getAllConnectedDevices: " + ex.getMessage());
        }
        return connectedDevices;
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


}
