package testCase;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.testng.annotations.Test;

import pages.LandingPage;

public class TestParallelMethods {
	@Test
	public void testAmazonLogo() {
		EdgeOptions edgeOptions = new EdgeOptions();
		edgeOptions.addArguments("--edge-skip-compat-layer-relaunch");
		
		WebDriver driver = new EdgeDriver(edgeOptions);
		driver.get("https://www.amazon.in");
		LandingPage landingPage = new LandingPage(driver);
		landingPage.displayedAmazonLogo();
		driver.quit();
	}

	@Test
	public void testTrendingText() {
		EdgeOptions edgeOptions = new EdgeOptions();
		edgeOptions.addArguments("--edge-skip-compat-layer-relaunch");
		
		WebDriver driver = new EdgeDriver(edgeOptions);
		driver.get("https://www.amazon.in");
		LandingPage landingPage = new LandingPage(driver);
		landingPage.clickOpenAllCategoriesMenu();
		String trending = landingPage.trendingText();
		driver.quit();
	}
}
