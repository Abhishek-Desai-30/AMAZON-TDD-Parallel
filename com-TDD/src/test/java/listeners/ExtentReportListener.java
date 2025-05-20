
package listeners;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.aventstack.extentreports.reporter.configuration.ViewName;

import driverManager.CreateDriver;

public class ExtentReportListener implements ITestListener {

	private static ExtentReports extent;
	private static final ThreadLocal<ExtentTest> test = new ThreadLocal<>();
	private ExtentTest extentTest;
	private WebDriver driver;

	public static ExtentReports getExtentReports() {
		if (extent == null) {
			ExtentSparkReporter sparkReporter = new ExtentSparkReporter("target/ExtentReports/SparkReport.html");
			sparkReporter.viewConfigurer().viewOrder().as(new ViewName[] { ViewName.DASHBOARD, ViewName.TEST,
					ViewName.CATEGORY, ViewName.AUTHOR, ViewName.DEVICE, ViewName.EXCEPTION });
			extent = new ExtentReports();
			extent.setSystemInfo("OS", System.getProperty("os.name"));
			extent.setSystemInfo("Java Version", System.getProperty("java.version"));
			extent.setSystemInfo("User", System.getProperty("user.name"));

			sparkReporter.config().setReportName("Automation QA: Abhishek Desai");
			sparkReporter.config().setDocumentTitle("Amazon test");
			sparkReporter.config().setTimeStampFormat("dd-MMM-YYYY hh:mm:ss");
			sparkReporter.config().setTheme(Theme.STANDARD);
			sparkReporter.config().setTimelineEnabled(true);

			extent.attachReporter(sparkReporter);
		}
		return extent;
	}
	
	@Override
    public void onStart(ITestContext context) {
        getExtentReports(); // Initialize extent reports
        
    }
	
	@Override
	public void onTestStart(ITestResult result) {
//	    ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName());
//	    test.set(extentTest);
	}

	@Override
	public void onTestFailure(ITestResult result) {

            try {
                driver = CreateDriver.getInstance().getDriver();
                String base64Screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
                test.get().
                	fail(result.getThrowable(), 
                    MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());
            } catch (Exception e) {
                extentTest.fail("Test failed but screenshot capture failed: " + e.getMessage());
            }
        
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		test.get().skip("Test skipped" + result.getMethod().getMethodName() + " due to: " + result.getSkipCausedBy());
	}

	@Override
	public void onFinish(ITestContext context) {
		extent.flush();
	}
	
	 public static ExtentTest getTest() {
	        return test.get();
	    }
	
	public static void setTest(ExtentTest testInstance) {
        test.set(testInstance);
    }

}
