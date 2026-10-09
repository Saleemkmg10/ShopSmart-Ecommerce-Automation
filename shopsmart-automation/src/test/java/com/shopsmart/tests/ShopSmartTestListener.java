package com.shopsmart.tests;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ShopSmartTestListener implements ITestListener {

    private static final ThreadLocal<ExtentTest> currentTest =
            new ThreadLocal<>();

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest test = ExtentReportManager.getReport()
                .createTest(result.getMethod().getMethodName());

        currentTest.set(test);
        test.log(Status.INFO, "Test execution started");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentTest test = currentTest.get();

        if (test != null) {
            test.log(Status.PASS, "Test passed successfully");
        }

        currentTest.remove();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentTest test = currentTest.get();

        if (test != null) {
            test.log(Status.FAIL, "Test failed");

            if (result.getThrowable() != null) {
                test.log(
                        Status.FAIL,
                        result.getThrowable().toString()
                );
            }
        }

        Object testInstance = result.getInstance();

        if (testInstance instanceof ShopSmartUITest) {
            ShopSmartUITest uiTest =
                    (ShopSmartUITest) testInstance;

            WebDriver driver = uiTest.getDriver();

            if (driver != null) {
                try {
                    Path folder = Paths.get(
                            "target", "screenshots"
                    );

                    Files.createDirectories(folder);

                    String timestamp = LocalDateTime.now()
                            .format(
                                    DateTimeFormatter.ofPattern(
                                            "yyyyMMdd_HHmmss_SSS"
                                    )
                            );

                    String fileName =
                            result.getMethod().getMethodName()
                                    + "_" + timestamp + ".png";

                    File source = ((TakesScreenshot) driver)
                            .getScreenshotAs(OutputType.FILE);

                    Path destination = folder.resolve(fileName);

                    Files.copy(source.toPath(), destination);

                    System.out.println(
                            "Failure screenshot saved: "
                                    + destination.toAbsolutePath()
                    );

                    if (test != null) {
                        test.log(
                                Status.INFO,
                                "Screenshot saved at: "
                                        + destination.toAbsolutePath()
                        );
                    }

                } catch (IOException | RuntimeException e) {
                    System.err.println(
                            "Could not save screenshot: "
                                    + e.getMessage()
                    );
                }
            }
        }

        currentTest.remove();
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentTest test = currentTest.get();

        if (test == null) {
            test = ExtentReportManager.getReport()
                    .createTest(result.getMethod().getMethodName());
        }

        test.log(Status.SKIP, "Test was skipped");

        if (result.getThrowable() != null) {
            test.log(
                    Status.SKIP,
                    result.getThrowable().toString()
            );
        }

        currentTest.remove();
    }

    @Override
    public void onFinish(
            org.testng.ITestContext context
    ) {
        ExtentReportManager.flushReport();

        System.out.println(
                "HTML test report generated at: "
                        + Paths.get(
                        "target",
                        "reports",
                        "ShopSmartTestReport.html"
                ).toAbsolutePath()
        );
    }
}

