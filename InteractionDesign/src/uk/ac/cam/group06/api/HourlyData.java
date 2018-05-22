package uk.ac.cam.group06.api;

import java.util.Date;

public class HourlyData {
	private Date date;
	private String temperature;
	private String weatherCondition;
	private String weatherDescription;
	private String icon;
	private String humidity;
	private String windSpeed;
	private String windDirection;
	
	/*
	 * A Class that encompasses the idea of a locations information
	 * but for breaking down into time intervals. Doesn't extend the LocationInformation
	 * class because it represents a slightly different concept and also the 
	 * data provided by OWM API is different for time intervals
	 * 
	 */
	
	public HourlyData(Date date){
		this.date = date;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}

	public String getTemperature() {
		return temperature;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}

	public String getWeatherCondition() {
		return weatherCondition;
	}

	public void setWeatherCondition(String weatherCondition) {
		this.weatherCondition = weatherCondition;
	}

	public String getWeatherDescription() {
		return weatherDescription;
	}

	public void setWeatherDescription(String weatherDescription) {
		this.weatherDescription = weatherDescription;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getHumidity() {
		return humidity;
	}

	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}

	public String getWindSpeed() {
		return windSpeed;
	}

	public void setWindSpeed(String windSpeed) {
		this.windSpeed = windSpeed;
	}

	public String getWindDirection() {
		return windDirection;
	}

	public void setWindDirection(String windDirection) {
		this.windDirection = windDirection;
	}
	
}
