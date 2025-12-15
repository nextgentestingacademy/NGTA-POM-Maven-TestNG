package test;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import base.BaseTest;
import page.HomePage;
import utils.Log;

public class HomeTest extends BaseTest{
	HomePage home;
	
	@BeforeMethod(alwaysRun = true)
	public void callLoginPage() {
		home = new HomePage(driver);
	}
	
	@Test
	public void enterSearchFlightDetails() throws InterruptedException {
		Log.info("Test Case: enterSearchFlightDetails has started");
		home.closeLoginPopUp();
		Log.info("Login pop up window closed");
		home.closeBotPopUp();
		Log.info("Chat bot pop up window closed");
		Assert.assertTrue(home.enterFromCity("London","Gatwick"));
		Log.info("From Location entered as 'London - Gatwick'");
		Assert.assertTrue(home.enterToCity("Mumbai","Navi Mumbai"));
		Log.info("From Location entered as 'Mumbai - Navi Mumbai'");
		home.clickDeparture();
		Log.info("Clicked on Departure Calendar");
		Assert.assertTrue(home.selectFromDate("December 2025","25"));
		Log.info("Departure date selected as '25 December 2025");
		home.clickSearch();
		Log.info("Test Case: enterSearchFlightDetails has completed");
	}
}