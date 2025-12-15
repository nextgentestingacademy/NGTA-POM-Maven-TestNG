package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {
	public WebDriver driver;
	
	@BeforeMethod(alwaysRun = true)
	public void launchApp() {
		System.out.println("Launching Application");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://www.makemytrip.com/");
	}
	
	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		System.out.println("Closing Application");
		if(driver!=null) {
			driver.quit();
		}
	}
	
}
