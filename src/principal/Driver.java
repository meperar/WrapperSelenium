package principal;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Driver {
	public static WebDriver driver;
	public static void Chrome()
	{ String exePath = "C:\\Users\\me-la\\Desktop\\chromedriver_win32\\chromedriver.exe";
	System.setProperty("webdriver.chrome.driver", exePath);
	ChromeOptions options = new ChromeOptions();
	options.addArguments("--start-maximized");
	driver = new ChromeDriver(options);
	// driver.quit();
	}

}
