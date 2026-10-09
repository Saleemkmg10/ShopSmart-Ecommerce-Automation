
package com.shopsmart.tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ExtentReportManager {

    private static ExtentReports extent;

    public static synchronized ExtentReports getReport() {

        if (extent == null) {
            try {
                Path reportFolder = Paths.get(
                        "target", "reports"
                );

                Files.createDirectories(reportFolder);

                Path reportPath = reportFolder.resolve(
                        "ShopSmartTestReport.html"
                );

                ExtentSparkReporter sparkReporter =
                        new ExtentSparkReporter(
                                reportPath.toString()
                        );

                sparkReporter.config().setDocumentTitle(
                        "ShopSmart Automation Report"
                );

                sparkReporter.config().setReportName(
                        "ShopSmart Test Execution Results"
                );

                extent = new ExtentReports();
                extent.attachReporter(sparkReporter);

                extent.setSystemInfo(
                        "Project", "ShopSmart"
                );

                extent.setSystemInfo(
                        "Automation", "Selenium"
                );

                extent.setSystemInfo(
                        "Framework", "TestNG"
                );

                extent.setSystemInfo(
                        "Language", "Java"
                );

            } catch (IOException e) {
                throw new RuntimeException(
                        "Failed to create report folder", e
                );
            }
        }

        return extent;
    }

    public static synchronized void flushReport() {
        if (extent != null) {
            extent.flush();
        }
    }
}