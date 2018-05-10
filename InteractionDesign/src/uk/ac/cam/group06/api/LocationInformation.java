package uk.ac.cam.group06.api;

public class LocationInformation {
	private String cityName;
	private String countryCode;
	
	//Weather Variables
	private String temperature;
	private String weatherCondition;
	private String weatherDescription;
	private String icon;
	private String humidity;
	private String windSpeed;
	private String windDirection;
	
	//Pollution Variables
	private double nitrogenDioxide;
	private double carbonMonoxide;
	private double sulphurDioxide;
	
	public LocationInformation(String cityName, String countryCode) {
		this.cityName = cityName;
		this.countryCode = countryCode;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
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

	public double getNitrogenDioxide() {
		return nitrogenDioxide;
	}

	public void setNitrogenDioxide(double nitrogenDioxide) {
		this.nitrogenDioxide = nitrogenDioxide;
	}

	public double getCarbonMonoxide() {
		return carbonMonoxide;
	}

	public void setCarbonMonoxide(double carbonMonoxide) {
		this.carbonMonoxide = carbonMonoxide;
	}

	public double getSulphurDioxide() {
		return sulphurDioxide;
	}

	public void setSulphurDioxide(double sulphurDioxide) {
		this.sulphurDioxide = sulphurDioxide;
	}
}
