package uk.ac.cam.group06.idesign;

import java.util.ArrayList;

public class WeatherWarning {
	private int mLevel;					// simplified warning level - 0: green, 1-2 yellow, 3: red
	private ArrayList<String> mContributors;	// contributors to the warning level - cold/hot, humid, windy 
	
	// Thresholds for various measurements
	private int highWind = 20;
	private int lowTemp = 10;
	private int highTemp = 25;
	private int highHumidity = 70;
	
	/**
	 * Calculate the warning level for the hour's weather
	 * 
	 * @param temperature - temperature 
	 * @param humidity - humidity 
	 * @param windspeed - windspeed
	 */
	public WeatherWarning(String temperature, String humidity, String windspeed) {
		// Checks which measurements are over the thresholds & adds them to the warning 
		mContributors = new ArrayList<String>();
		mLevel = 0;
		
		if(Integer.parseInt(temperature) < lowTemp) {
			mContributors.add("cold (" + temperature + "*C)");
			mLevel++;
		}else if(Integer.parseInt(temperature) > highTemp){
			mContributors.add("hot (" + temperature + "*C)");
			mLevel++;
		}
		
		if(Integer.parseInt(humidity) > highHumidity) {
			mContributors.add("humid (" + humidity + "%)" );
			mLevel++;
		}
		
		if(Integer.parseInt(windspeed) > highWind) {
			mContributors.add("windy (" + humidity + ")" );
			mLevel++;
		}
	}
	
	public ArrayList<String> getContributors(){
		return mContributors;
	}
	
	public int getLevel() {
		return mLevel;
	}
	
	
}
