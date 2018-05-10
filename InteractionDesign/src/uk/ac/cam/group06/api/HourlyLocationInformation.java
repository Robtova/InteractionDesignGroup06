package uk.ac.cam.group06.api;

import java.util.LinkedList;

public class HourlyLocationInformation {
	private String cityName;
	private String countryCode;
	private LinkedList<HourlyData> hourlyData;
	
	public HourlyLocationInformation(String cityName, String countryCode) {
		this.cityName = cityName;
		this.countryCode = countryCode;
		hourlyData = new LinkedList<HourlyData>();
	}
	
	public void addData(HourlyData hd) {
		hourlyData.addLast(hd);
	}
	
	public LinkedList<HourlyData> getForecast() {
		return hourlyData;
	}

}
