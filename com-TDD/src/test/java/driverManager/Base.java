package driverManager;

import java.lang.reflect.Method;
import java.time.Duration;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;

import listeners.ExtentReportListener;
import pages.LandingPage;
import pages.YoutubePage;
import utility.Author;

public class Base {

	private static ExtentReports extent;

	protected static WebDriver driver;
	protected LandingPage landpg;
	protected YoutubePage ytpg;
	protected WebDriverWait wait;
	
	protected static ThreadLocal<String> browserName = new ThreadLocal<>();

	@BeforeClass
	@Parameters({ "browser" })
	public void setup(@Optional("Chrome") String browser) { //
		System.out.println("=== Browser Param Received: " + browser + " ===");
		CreateDriver.getInstance().setDriver(browser);
		driver = CreateDriver.getInstance().getDriver();
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		 browserName.set(browser);
		
		if (driver == null) {
			throw new IllegalStateException("WebDriver initialization failed.");
		}
		landpg = new LandingPage(driver);
		ytpg = new YoutubePage(driver);
//		driver.get("https://www.amazon.in/");
	}
	
	public static String getBrowserName() {
        return browserName.get();
    }
	

	
	@AfterSuite
	public void tearDown2() {
		CreateDriver.getInstance().quitDriver();
	}

	@BeforeMethod
	public void beforeMethodInit(Method method) {

		if (extent == null) {
			extent = ExtentReportListener.getExtentReports(); // Your Extent config method
		}

		Author authorAnnotation = method.getAnnotation(Author.class);
		String author = (authorAnnotation != null) ? authorAnnotation.value() : "Unknown";

		Test testAnnotation = method.getAnnotation(Test.class);
		String testName = (testAnnotation != null && !testAnnotation.testName().isEmpty()) ? testAnnotation.testName()
				: method.getName();
		
		 // Append browser name to test name
	    String browser = Base.getBrowserName();
	    String testNameWithBrowser = testName + " [" + browser.toUpperCase() + "]";

		ExtentTest extentTest = ExtentReportListener.getExtentReports().
									createTest(testNameWithBrowser).
									assignAuthor(author).
									assignDevice(browser);

		ExtentReportListener.setTest(extentTest);
		

	}


	@AfterMethod
	public void tearDown(ITestResult result) {
		if (result.getStatus() == ITestResult.FAILURE) {
			ExtentReportListener.getTest().fail(result.getThrowable());
		} else if (result.getStatus() == ITestResult.SKIP) {
			ExtentReportListener.getTest().skip("Test skipped");
		} else {
			ExtentReportListener.getTest().pass("Test passed");
		}

		extent.flush();
	}
	
	public static void captureScreenshot(String stepName) {
		ExtentTest extentTest = ExtentReportListener.getTest();
        try {
        	
            String base64Screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
            extentTest.info(stepName, 
                MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());
        } catch (Exception e) {
        	extentTest.warning("Failed to capture screenshot for step: " + stepName);
        }
    }


}
