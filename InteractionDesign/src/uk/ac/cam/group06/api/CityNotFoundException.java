package uk.ac.cam.group06.api;

public class CityNotFoundException extends RuntimeException {
	
	// Exception thrown on FileNotFound reported by the OpenWeatherMap API 
	public CityNotFoundException(String city) {
		System.out.println("Sorry " + city + " is not a city");
	}

}
