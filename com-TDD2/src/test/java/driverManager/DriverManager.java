package driverManager;

import browserManager.BrowserManager;
import browserManager.ChromeManager;
import browserManager.EdgeManager;
import browserManager.FirefoxManager;

public class DriverManager {

	public static BrowserManager getBrowserManager(String browser) {

		switch (browser.toLowerCase()) {
		case "edge":
			return new EdgeManager();
		case "chrome":
			return new ChromeManager();
		case "firefox":
			return new FirefoxManager();
		default:
			throw new IllegalArgumentException("no matching browser");

		}
	}
}
