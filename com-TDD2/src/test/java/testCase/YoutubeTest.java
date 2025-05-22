package testCase;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import driverManager.Base;

public class YoutubeTest  extends Base {
	
	@BeforeMethod
	public void setupURL() {
		driver.get("https://www.youtube.com/");
	}
	
	@Test(testName = "YT: Verify user is able to click on History")
	public void test1() {
		ytpg.ytHistory();
	}
	
}
