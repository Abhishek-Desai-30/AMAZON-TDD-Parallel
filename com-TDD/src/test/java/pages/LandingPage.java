package pages;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LandingPage {
	
	protected WebDriver driver;
	private WebDriverWait wait;

	public LandingPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));	
		}
	
	@FindBy(xpath = "//a[@aria-label='Amazon.in']")
	WebElement amazonLogo;
	
	public void displayedAmazonLogo() {
		wait.until(ExpectedConditions.visibilityOf(amazonLogo));
		amazonLogo.isDisplayed();
	}
	
	@FindBy(xpath = "//a[contains(@aria-label,'Open All Categories Menu')]")
	WebElement openAllCategoriesMenu;
	
	@FindBy(xpath="//div[@id='hmenu-content']//div[contains(text(),'Trending')]")
	WebElement trending;
	
	public void clickOpenAllCategoriesMenu() {
		wait.until(ExpectedConditions.visibilityOf(amazonLogo));
		wait.until(ExpectedConditions.visibilityOf(openAllCategoriesMenu));
		wait.until(ExpectedConditions.elementToBeClickable(openAllCategoriesMenu));
        openAllCategoriesMenu.click();
    }
	
	public String trendingText() {
	    wait.until(ExpectedConditions.visibilityOf(trending));
	    return trending.getText();
	}
	
	
	@FindBy(xpath = "//a[contains(text(),'Bestsellers')]")
	WebElement bestsellers;
	
	public void clickBestsellers() {
        bestsellers.click();
    }
	
	@FindBy(xpath ="//span[contains(text(),'Amazon Bestsellers')]")
	WebElement bestsellersText;
	
	public String BestsellersText() {
        wait.until(ExpectedConditions.visibilityOf(bestsellersText));
        return bestsellersText.getText();
    }
}
