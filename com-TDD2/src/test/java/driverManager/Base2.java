package driverManager;

import java.lang.reflect.Method;
import java.time.Duration;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.*;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;

import listeners.ExtentReportListener;
import pages.LandingPage;
import pages.YoutubePage;
import utility.Author;

public class Base2 {

    private static ExtentReports extent;

    protected static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    protected static ThreadLocal<WebDriverWait> wait = new ThreadLocal<>();
    protected static ThreadLocal<LandingPage> landpg = new ThreadLocal<>();
    protected static ThreadLocal<YoutubePage> ytpg = new ThreadLocal<>();
    protected static ThreadLocal<String> browserName = new ThreadLocal<>();

    @Parameters({ "browser" })
    @BeforeMethod(alwaysRun = true)
    public void setupDriver(@Optional("chrome") String browser, Method method) {
        // Only set up driver if not already present for this thread
        if (getDriver() == null) {
            System.out.println("=== Browser Param Received: " + browser + " ===");
            CreateDriver.getInstance().setDriver(browser);
            WebDriver drv = CreateDriver.getInstance().getDriver();
            driver.set(drv);
            wait.set(new WebDriverWait(drv, Duration.ofSeconds(10)));
            browserName.set(browser);
            if (drv == null) {
                throw new IllegalStateException("WebDriver initialization failed.");
            }
        }

        // Always create fresh page objects for each test
        WebDriver drv = getDriver();
        landpg.set(new LandingPage(drv));
        ytpg.set(new YoutubePage(drv));

        if (extent == null) {
            extent = ExtentReportListener.getExtentReports();
        }

        Author authorAnnotation = method.getAnnotation(Author.class);
        String author = (authorAnnotation != null) ? authorAnnotation.value() : "Unknown";

        Test testAnnotation = method.getAnnotation(Test.class);
        String testName = (testAnnotation != null && !testAnnotation.testName().isEmpty())
                ? testAnnotation.testName()
                : method.getName();

        String browserUsed = getBrowserName();
        String testNameWithBrowser = testName + " [" + browserUsed.toUpperCase() + "]";

        ExtentTest extentTest = extent.createTest(testNameWithBrowser)
                .assignAuthor(author)
                .assignDevice(browserUsed);

        ExtentReportListener.setTest(extentTest);
    }

    public static String getBrowserName() {
        return browserName.get();
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static WebDriverWait getWait() {
        return wait.get();
    }

    public LandingPage getLandingPage() {
        return landpg.get();
    }

    public YoutubePage getYoutubePage() {
        return ytpg.get();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            ExtentReportListener.getTest().fail(result.getThrowable());
        } else if (result.getStatus() == ITestResult.SKIP) {
            ExtentReportListener.getTest().skip("Test skipped");
        } else {
            ExtentReportListener.getTest().pass("Test passed");
        }
        extent.flush();

        // Quit driver after each test to avoid browser leak in parallel runs
        if (getDriver() != null) {
            CreateDriver.getInstance().quitDriver();
            driver.remove();
            wait.remove();
            landpg.remove();
            ytpg.remove();
            browserName.remove();
        }
    }

    public static void captureScreenshot(String stepName) {
        ExtentTest extentTest = ExtentReportListener.getTest();
        try {
            String base64Screenshot = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BASE64);
            extentTest.info(stepName,
                    MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());
        } catch (Exception e) {
            extentTest.warning("Failed to capture screenshot for step: " + stepName);
        }
    }
}