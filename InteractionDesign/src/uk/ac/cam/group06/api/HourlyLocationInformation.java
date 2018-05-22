package uk.ac.cam.group06.api;

import java.util.LinkedList;

public class HourlyLocationInformation {
	private String cityName;
	private String countryCode;
	private LinkedList<HourlyData> hourlyData;
	
	/*
	 * A class that acts as the collection for the hourly information
	 * objects of a specific location storing cityname and countrycode as
	 * well as a linkedlist of time intervals
	 */
	
	public HourlyLocationInformation(String cityName, String countryCode) {
		this.cityName = cityName;
		this.countryCode = countryCode;
		hourlyData = new LinkedList<HourlyData>();
	}
	
	public void addData(HourlyData hd) {
		hourlyData.addLast(hd);
	}
	
	/*
	 * Note that the way the API adds the data means that it is already sorted
	 * by date - could cause errors in the future.
	 */
	public LinkedList<HourlyData> getForecast() {
		return hourlyData;
	}

}
