import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;


public class Testing {

	public static void main(String[] args) throws MalformedURLException {
		WebDriver driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), DesiredCapabilities.firefox());
		
		driver.get("http://www.google.com");
		
		// RemoteWebDriver does not implement the takesSCreenshot class if the driver
		// does not have the Capabilities to take a screenshot then Augmenter will add
		// the TakesScreenshot methods to the instance.
		WebDriver augmentedDriver = new Augmenter().augment(driver);
		File screenshot = ((TakesScreenshot)augmentedDriver).getScreenshotAs(OutputType.FILE);
		

	}

}
