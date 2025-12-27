package test;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import base.BaseTest;
import page.HomePage;
import utils.DataProviderUtils;
import utils.Log;

public class HomeTest extends BaseTest{
	HomePage home;
	
	@BeforeMethod(alwaysRun = true)
	public void callLoginPage() {
		home = new HomePage();
	}
	
	@Test(enabled=false,dataProvider="fromLocation",dataProviderClass=DataProviderUtils.class)
	public void enterSearchFlightDetails(String fromLoc) throws InterruptedException {
		Log.info("Test Case: enterSearchFlightDetails has started");
		home.closeLoginPopUp();
		Log.info("Login pop up window closed");
		home.closeBotPopUp();
		Log.info("Chat bot pop up window closed");
		Assert.assertTrue(home.enterFromCity(fromLoc,"Gatwick"));
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
	
	@Test(dataProvider="flightData",dataProviderClass=DataProviderUtils.class)
	public void searchFlightDetails(Map<String,String> data) throws InterruptedException {
		Log.info("Test Case: searchFlightDetails has started");
		home.closeLoginPopUp();
		Log.info("Login pop up window closed");
		home.closeBotPopUp();
		Log.info("Chat bot pop up window closed");
		Assert.assertTrue(home.enterFromCity(data.get("FromCity"),data.get("FromCitySuggest")));
		Log.info("From Location entered as 'London - Gatwick'");
		Assert.assertTrue(home.enterToCity(data.get("ToCity"),data.get("ToCitySuggest")));
		Log.info("From Location entered as 'Mumbai - Navi Mumbai'");
		home.clickDeparture();
		Log.info("Clicked on Departure Calendar");
		Assert.assertTrue(home.selectFromDate("December 2025","25"));
		Log.info("Departure date selected as '25 December 2025");
		home.clickSearch();
		Log.info("Test Case: searchFlightDetails has completed");
	}
}