package uk.ac.cam.group06.api.store;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import uk.ac.cam.group06.api.API;
import uk.ac.cam.group06.api.CityNotFoundException;
import uk.ac.cam.group06.api.HourlyLocationInformation;
import uk.ac.cam.group06.api.LocationInformation;

public class DataStore {
	
	private static Map<String, HourlyLocationInformation> mHourlyInfo = new HashMap<String, HourlyLocationInformation>();

	private static Map<String, LocationInformation> mCurrentInfo = new HashMap<String, LocationInformation>();
	
	/**
	 * Returns the current weather information for the city specified. 
	 * If not already loaded into memory the data is loaded from the API.
	 * 
	 * @param city - the city for which data is wanted
	 * @param countryCode - the country code for the location
	 * @return LocationInformation object with current weather info
	 * @throws CityNotFoundException 
	 */
	public static LocationInformation getCurrentInformation(String city, String countryCode) throws CityNotFoundException {
		if(!mCurrentInfo.containsKey(city+countryCode)) {
			try {
				mCurrentInfo.put(city+countryCode, API.getCurrentInformation(city, countryCode));
			} catch (MalformedURLException e) {
				e.printStackTrace();
				return getCurrentInformation("cambridge", "uk");
			}
		}
		return mCurrentInfo.get(city+countryCode);
	}
	
	/**
	 * Returns the five day forecast for the city specified. 
	 * If not already loaded into memory the data is loaded from the API.
	 * 
	 * @param city - the city for which data is wanted
	 * @param countryCode - the country code for the location
	 * @return HourlyLocationInformation object with five day forecast information
	 * @throws CityNotFoundException 
	 */
	public static HourlyLocationInformation getFiveDayForecast(String city, String countryCode) throws CityNotFoundException {
		if(!mHourlyInfo.containsKey(city+countryCode)) {
			try {
				mHourlyInfo.put(city+countryCode, API.getFiveDayForecast(city, countryCode));
			} catch (MalformedURLException e) {
				e.printStackTrace();
				return getFiveDayForecast("cambridge", "uk");
			}
		}
		return mHourlyInfo.get(city+countryCode);
	}
}
