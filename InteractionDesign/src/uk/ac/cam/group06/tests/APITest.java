package uk.ac.cam.group06.tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import uk.ac.cam.group06.api.API;
import uk.ac.cam.group06.api.CityNotFoundException;
import uk.ac.cam.group06.api.HourlyLocationInformation;
import uk.ac.cam.group06.api.LocationInformation;


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
	public void getCloudiness_forCambridge_notNull() throws MalformedURLException {
		LocationInformation li = API.getCurrentInformation("cambridge", "uk");
		Assert.assertNotEquals(null, li.getCloudCover());
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
	
	@Test (expected = CityNotFoundException.class)
	public void validCity_booleanCheck() throws IOException {
		API.getCurrentInformation("cambrdge", "uk");
	}
	
	@Test
	public void checkCountryCodes_Uzbekistan() {
		HashMap<String, String> countryCode = API.getCountrycodeMap();
		
		Assert.assertEquals(countryCode.get("UZ"), "Uzbekistan");
	}

}
