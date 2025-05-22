package browserManager;

import java.util.UUID;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class EdgeManager implements BrowserManager {

	@Override
	public WebDriver getDriver() {
		
        WebDriverManager.edgedriver().setup();
		EdgeOptions edgeOptions = new EdgeOptions();

//		System.setProperty("webdriver.edge.driver", ".\\src\\test\\resources\\msedgedriver.exe");

//		System.setProperty("webdriver.edge.verboseLogging", "true");
//		
//		String uniqueProfile = "C:/temp/edge_profile_" + UUID.randomUUID();
//		edgeOptions.addArguments("user-data-dir=" + uniqueProfile);
		
		edgeOptions.addArguments("--edge-skip-compat-layer-relaunch");

		String headless = System.getProperty("headless", "false");
		if (headless.equals("true")) {
			edgeOptions.addArguments("--headless");
		}
		return new EdgeDriver(edgeOptions);
	}

}
