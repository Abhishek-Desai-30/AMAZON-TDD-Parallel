package testCase;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class EdgeTest {
    public static void main(String[] args) throws Exception {
//        System.setProperty("webdriver.edge.driver", ".\\src\\test\\resources\\msedgedriver.exe");
        
        WebDriverManager.edgedriver().setup();

        // Kill all edge processes first (optional but helps)
        Runtime.getRuntime().exec("taskkill /F /IM msedge.exe");
        Thread.sleep(10000);
        
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--edge-skip-compat-layer-relaunch");
        options.addArguments("user-data-dir=C:/Temp/EdgeProfile_" + System.currentTimeMillis());
        WebDriver driver = new EdgeDriver(options);


        driver.get("https://www.google.com");
        System.out.println("Title: " + driver.getTitle());

        driver.quit();
    }
}
