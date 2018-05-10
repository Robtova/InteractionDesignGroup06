package uk.ac.cam.group06.tests;

import static org.junit.Assert.*;

import java.net.MalformedURLException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import uk.ac.cam.group06.api.API;


public class APITest {

	@Before
	public void setup(){
		try {
			API.updateLongitudeAndLatitude("cambridge", "uk");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void coordinates() {
		Assert.assertEquals(API.getLongitude(), 0.12, 0.001D);
	}
	
	@Test
	public void getForecast() {
		try {
			API.getFiveDayForecast("cambridge", "uk");
			Assert.assertEquals(false, API.getFiveDayForecast("cambridge", "uk").getForecast().isEmpty());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void getPollution(){
		try {
			Assert.assertNotEquals(null, API.getCurrentInformation("cambridge", "uk").getNitrogenDioxide());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void checkLocationObject() {
		try {
			Assert.assertNotEquals(null, API.getCurrentInformation("cambridge", "uk").getCityName());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
