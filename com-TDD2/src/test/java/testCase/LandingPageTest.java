package testCase;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import driverManager.Base;

@Listeners(listeners.ExtentReportListener.class)
public class LandingPageTest extends Base {
	
	@BeforeMethod
	public void setupURL() {
		driver.get("https://www.amazon.in/");
		wait.until(ExpectedConditions.titleContains("Amazon"));
	}
	
	@Test(testName = "AMZ: Verify user is able to click on Best Seller tab")
	public void test1() {
		landpg.clickBestsellers();
		captureScreenshot("Clicked  Bestseller");
	}
	
	@Test(testName = "AMZ: Verify user is able to click on open all categories")
	public void test2() {
		landpg.displayedAmazonLogo();
		landpg.clickOpenAllCategoriesMenu();
		landpg.trendingText();
		captureScreenshot("Categories menu clicked");
		Assert.assertEquals(landpg.trendingText(), "Trending");
	}
	

	

}
