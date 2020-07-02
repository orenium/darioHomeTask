package utils;

import il.co.topq.difido.ReportDispatcher;
import il.co.topq.difido.ReportManager;
import il.co.topq.difido.model.Enums;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.awt.*;
import java.io.File;
import java.net.InetAddress;

public class TestRunner {

    private static final String REPORTING_FILE_PATH = "/Documents/DarioHomeTask/test-output/difido/current/index.html";
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
            String machineName = InetAddress.getLocalHost().getHostName();
            String currentUser = machineName.substring(0, machineName.indexOf("-"));
            File htmlFile = new File("/Users/" + currentUser + REPORTING_FILE_PATH);

            Desktop.getDesktop().browse(htmlFile.toURI());
            if (quitDriver){
                driver.quit();
            }
        } catch (Exception ex) {
            System.out.println("BaseTest.openReportHtmlFile: " + ex.getMessage());
        }
    }

    public static void printToLog(String content){
        report.log(content);
    }




}
