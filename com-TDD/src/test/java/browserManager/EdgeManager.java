package browserManager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class EdgeManager implements BrowserManager{

	@Override
	public WebDriver getDriver() {
		EdgeOptions edgeOptions=new EdgeOptions();
		WebDriverManager.edgedriver().setup();
		String headless=System.getProperty("headless","false");
        if(headless.equals("true")){
            edgeOptions.addArguments("--headless");
        }
        return new EdgeDriver (edgeOptions);
	}
	
}
