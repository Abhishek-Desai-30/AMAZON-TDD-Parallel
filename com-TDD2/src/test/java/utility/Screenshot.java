package utility;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import driverManager.CreateDriver;

public class Screenshot {

	
	public String getSS(String fileName, WebDriver driver) {
	    File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

	    // Create the Screenshots directory if it doesn't exist
	    Path screenshotsDir = Paths.get("./Screenshots");
	    try {
	        if (!Files.exists(screenshotsDir)) {
	            Files.createDirectories(screenshotsDir); // Create directory if it doesn't exist
	        }

	        // Define paths for source and target files
	        String screenshotFilePath = screenshotsDir.toAbsolutePath().toString() + "/" + fileName + dateStamp() + ".png";
	        Path targetPath = Paths.get(screenshotsDir.toString(), fileName + dateStamp() + ".png");

	        // Move the screenshot file to the target path
	        Files.move(screenshot.toPath(), targetPath, StandardCopyOption.REPLACE_EXISTING);
	        System.out.println("Screenshot saved successfully to: " + targetPath.toString());
	        return screenshotFilePath; // 
	    } catch (IOException e) {
	        System.err.println("Error while saving screenshot: " + e.getMessage());
	    }
		return null;
	}
	
	public static String dateStamp() {
		Date date = new Date();
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy-HH-mm-ss");
		
		String formattedDate = sdf.format(date);
		
		return formattedDate;
	}

}
