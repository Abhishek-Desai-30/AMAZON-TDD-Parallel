package testCase;

import driverManager.Base2;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(listeners.ExtentReportListener.class)
public class LandingPageTest2 extends Base2 {

    @Test(testName = "AMZ: Verify user is able to click on Best Seller tab")
    public void test1() {
        getDriver().get("https://www.amazon.in/");
        getWait().until(ExpectedConditions.titleContains("Amazon"));
        getLandingPage().clickBestsellers();
        captureScreenshot("Clicked Bestseller");
    }

    @Test(testName = "AMZ: Verify user is able to click on open all categories")
    public void test2() {
        getDriver().get("https://www.amazon.in/");
        getWait().until(ExpectedConditions.titleContains("Amazon"));
        getLandingPage().displayedAmazonLogo();
        getLandingPage().clickOpenAllCategoriesMenu();
        getLandingPage().trendingText();
        captureScreenshot("Categories menu clicked");
        Assert.assertEquals(getLandingPage().trendingText(), "Trending");
    }
}