package uk.ac.cam.group06.tests;

import static org.junit.Assert.*;

import java.net.MalformedURLException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import uk.ac.cam.group06.api.API;
import uk.ac.cam.group06.api.HourlyLocationInformation;


public class APITest {

	@Before
	public void setup() throws MalformedURLException{
		API.updateLongitudeAndLatitude("cambridge", "uk");
	}
	
	@Test
	public void coordinates_forCambridge() {
		Assert.assertEquals(API.getLongitude(), 0.12, 0.001D);
	}
	
	@Test
	public void getFiveDayForecast_forCambridge_shouldBeNonEmpty() throws MalformedURLException {
		API.getFiveDayForecast("cambridge", "uk");
		Assert.assertEquals(false, API.getFiveDayForecast("cambridge", "uk").getForecast().isEmpty());
	}
	
	@Test
	public void getNitrogenDioxide_forCambridge() throws MalformedURLException{
		Assert.assertNotEquals(Double.NaN, API.getCurrentInformation("cambridge", "uk").getCarbonMonoxide());
	}
	
	@Test
	public void checkHourlyData_forCambridge() throws MalformedURLException {
		HourlyLocationInformation hli = API.getFiveDayForecast("cambridge", "uk");

		Assert.assertEquals(false, hli.getForecast().isEmpty());
	}
	
	@Test
	public void checkLocationObject() throws MalformedURLException {
		System.out.println(API.getCurrentInformation("cambridge", "uk").getCityName());
		
		Assert.assertNotEquals(null, API.getCurrentInformation("cambridge", "uk").getCityName());
	}

}
