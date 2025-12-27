package page;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import base.BasePage;
import utils.DriverFactory;

public class HomePage extends BasePage{
	//Page Factory element
	@FindBy(className="commonModal__close")
	WebElement btnLoginClose;
	
	@FindBy(id="fromCity")
	WebElement edtFrom;
	
	@FindBy(id="toCity")
	WebElement edtTo;
	
	@FindBy(xpath="//a[contains(@class,'widgetSearchBtn')]")
	WebElement btnSearch;
	
	@FindBy(xpath="//img[@alt='minimize']")
	WebElement btnBotClose;
	
	@FindBy(xpath="//ul[@role='listbox']/li")
	List<WebElement> autosuggLoc;

	@FindBy(xpath="//label[@for='departure']")
	WebElement calDeparture;

	@FindBy(xpath="//div[@class='DayPicker-Caption']/div")
	List<WebElement> calMonYear;

	@FindBy(className=".specialFareContainer")
	WebElement elmSpecialFare;
	
	//constructor
	public HomePage() {
		super();
		PageFactory.initElements(DriverFactory.getDriver(), this);
	}
	
	//page specific methods
	public boolean enterFromCity(String fromLoc,String exactFromLoc) throws InterruptedException {
		clickUsingOffset(10,10);

		return selectFromAutoSuggest(edtFrom,autosuggLoc,fromLoc,exactFromLoc);
	}
	
	public boolean enterToCity(String toLoc, String exactToLoc) throws InterruptedException {
		return selectFromAutoSuggest(edtTo,autosuggLoc,toLoc,exactToLoc);
	}
	
	public boolean selectFromDate(String date,String day) {
		int calendar=0;
		for(WebElement elm:calMonYear) {
			if(date.contains(elm.getText())) {
				calendar++;
				break;
			}
		}
	
		WebElement calMonth=DriverFactory.getDriver().findElement(By.xpath("(//div[@class='DayPicker-Month'])[" + calendar + "]"));
		List<WebElement> calDays  = calMonth.findElements(By.tagName("p"));
		for(WebElement elm:calDays) {
			if(elm.getText().equalsIgnoreCase(day)) {
				jsClick(elm);
				return true;
			}
		}
		return false;
	}
	
	public void closeLoginPopUp() {
		click(btnLoginClose);
	}
	
	public void closeBotPopUp() {
		click(btnBotClose);
	}
	
	public void clickDeparture() {
		jsClick(calDeparture);
	}
	
	public void clickSearch() {
		jsClick(btnSearch);
	}
}