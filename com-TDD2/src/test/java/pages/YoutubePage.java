package pages;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class YoutubePage {

	protected WebDriver driver;
	private WebDriverWait wait;

	public YoutubePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));	
		}
	
	@FindBy(xpath = "//*[contains(text(), 'History')]")
	WebElement ytHistory;
	
	public void ytHistory() {
		wait.until(ExpectedConditions.visibilityOf(ytHistory));
		ytHistory.click();
	}
}
