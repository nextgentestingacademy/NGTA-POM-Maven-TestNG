package utils;

import java.lang.reflect.Method;
import java.util.Map;

import org.testng.SkipException;
import org.testng.annotations.DataProvider;

public class DataProviderUtils {
	
	@DataProvider(name="fromLocation")
	public Object[][] getFromLocation(){
		return new Object[][] {
			{"London"}
		};
	}
	
	@DataProvider(name="passengerDetails")
	public Object[][] getPassengerDetail(){
		return new Object[][] {
			{"Mr","Sachin", "Patkar"},
			{"Mrs","Snehal", "Patkar"}
		};
	}
	
	@DataProvider(name="flightData")
	public Object[][] getFlightData(Method method){
			String testName = method.getName();
			Map<String,String> data = ExcelUtils.getTestData(testName);
			
			if(data.isEmpty()) {
				System.out.println("No test data found for " + testName);
			}
			
			if(data.get("Execution").trim().equalsIgnoreCase("no")) {
				System.out.println("Execution flag is No for " + testName);
			}
			
			return new Object[][] {{data}};
		}
}
