package browserManager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ChromeManager implements BrowserManager {
	@Override
	public WebDriver getDriver() {
		ChromeOptions chromeOptions=new ChromeOptions();
        WebDriverManager.chromedriver().setup();
         String headless=System.getProperty("headless","false");
        if(headless.equals("true")){
            chromeOptions.addArguments("--headless");
        }
        return new ChromeDriver(chromeOptions);
	}

}
