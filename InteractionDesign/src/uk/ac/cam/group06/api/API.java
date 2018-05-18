package uk.ac.cam.group06.api;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonReader;

public class API {
	public static final String owApiKey = "6c5c12fe2311616982b42083d9c5a76e";
	private static double longitude = 0.0d;
	private static double latitude  = 0.0d;
	
	/**
	 * Updates the private longitude and latitude fields 
	 * @param city
	 * @param countryCode
	 * @throws MalformedURLException
	 * @throws CityNotFoundException 
	 */
	public static void updateLongitudeAndLatitude(String city, String countryCode) throws MalformedURLException, CityNotFoundException {
		String address = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "," + countryCode + "&APPID=" + owApiKey;
		URL url = new URL(address);
		
		try(InputStream is = url.openStream(); JsonReader rdr = Json.createReader(is)) {
			JsonObject obj = rdr.readObject();
			JsonObject coordObj = obj.getJsonObject("coord");
			longitude = coordObj.getJsonNumber("lon").doubleValue();
			latitude = coordObj.getJsonNumber("lat").doubleValue();
		} catch (FileNotFoundException f){
			throw new CityNotFoundException(city);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Returns a {@code LocationInformation} object which
	 * there exist the appropriate get methods to access
	 * the standard information needed
	 * @param city
	 * @param countryCode (ISO 3166)
	 * @return LocationInformation
	 * @throws MalformedURLException
	 * @throws CityNotFoundException 
	 */
	public static LocationInformation getCurrentInformation(String city, String countryCode) throws MalformedURLException, CityNotFoundException {
		LocationInformation location = new LocationInformation(city, countryCode);
		String address = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "," + countryCode + "&APPID=" + owApiKey;
		URL url = new URL(address);
		
		try(InputStream is = url.openStream(); JsonReader rdr = Json.createReader(is)) {
			JsonObject obj = rdr.readObject();
			JsonArray weatherData = obj.getJsonArray("weather");
			JsonObject wObj = weatherData.getJsonObject(0);
			location.setWeatherDescription(wObj.getString("description"));
			location.setWeatherCondition(wObj.getString("main"));
			location.setIcon(wObj.getString("icon"));
			
			JsonObject mainData = obj.getJsonObject("main");
			location.setTemperature(String.valueOf(kelvinToCelsius(mainData.getInt("temp"))));
			location.setHumidity(String.valueOf(mainData.getInt("humidity")));
			
			JsonObject windData = obj.getJsonObject("wind");
			location.setWindSpeed(String.valueOf(windData.getJsonNumber("speed").intValue()));
			
//			This line always throws a null pointer exception.
			JsonNumber windDirection = windData.getJsonNumber("deg");
			if(windDirection != null) location.setWindDirection(String.valueOf(windDirection.intValue()));
			
			JsonObject cloudData = obj.getJsonObject("clouds");
			location.setCloudCover(String.valueOf(cloudData.getJsonNumber("all").intValue()));
		} catch (FileNotFoundException f){
			throw new CityNotFoundException(city);
		} catch (IOException e) {
			System.out.println("Input stream failed");
			e.printStackTrace();
		}
		
		getPollutionLevels(location);
		
		return location;
	}
	
	/**
	 * Returns an {@code HourlyLocationInformation} object that contains the 
	 * HourlyData objects with each of the attributes including the {@code java.util.Date}
	 * Note that the HourlyData objects should be in the right order.
	 * @param city
	 * @param countryCode (ISO 3166)
	 * @return HourlyLocationInformation
	 * @throws MalformedURLException
	 * @throws CityNotFoundException 
	 */
	public static HourlyLocationInformation getFiveDayForecast(String city, String countryCode) throws MalformedURLException, CityNotFoundException {
		HourlyLocationInformation hli = new HourlyLocationInformation(city, countryCode);
		String address = "http://api.openweathermap.org/data/2.5/forecast?q=" + city + "," + countryCode + "&APPID=" + owApiKey;
		URL url = new URL(address);
		
		try(InputStream is = url.openStream(); JsonReader rdr = Json.createReader(is)) {
			JsonObject obj = rdr.readObject();
			JsonArray threeHourData = obj.getJsonArray("list");
			/*
			 * Parsing through the threeHourData and extracting the data, putting it in a HourlyData object
			 * and then adding this to an HourlyLocationInformation object where the forecast may be accessed from
			 */
			threeHourData.forEach(item -> {
				JsonObject jObj = (JsonObject) item;
				Date date = new Date((long) jObj.getInt("dt")*1000);
				HourlyData hd = new HourlyData(date);
				
				JsonObject mainData = jObj.getJsonObject("main");
				hd.setTemperature(String.valueOf(kelvinToCelsius(mainData.getInt("temp"))));
				hd.setHumidity(String.valueOf(mainData.getInt("humidity")));

				JsonArray weatherData = jObj.getJsonArray("weather");
				JsonObject wObj = weatherData.getJsonObject(0);
				hd.setWeatherDescription(wObj.getString("description"));
				hd.setWeatherCondition(wObj.getString("main"));
				hd.setIcon(wObj.getString("icon"));
				
				JsonObject windData = jObj.getJsonObject("wind");
				hd.setWindSpeed(String.valueOf(windData.getJsonNumber("speed").intValue()));
				hd.setWindDirection(String.valueOf(windData.getJsonNumber("deg").intValue()));
				
				hli.addData(hd);
			});
		} catch (FileNotFoundException f){
			throw new CityNotFoundException(city);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return hli;
	}

	public static double getLongitude() {
		return longitude;
	}

	public static double getLatitude() {
		return latitude;
	}
	
	/**
	 * Creates a LocationInformation object which stores the values of the pollution levels.
	 * Once you create a LocationInformation object, you can pass it through this function to add
	 * pollution level information - this can be quite costly as it makes three http requests so
	 * only do it if absolutely necessary
	 * @param locInfo - an already created LocationInformation object
	 * @return LocationInformation
	 * @throws MalformedURLException
	 * @throws CityNotFoundException 
	 */
	private static LocationInformation getPollutionLevels(LocationInformation locInfo) throws MalformedURLException, CityNotFoundException { 
		updateLongitudeAndLatitude(locInfo.getCityName(), locInfo.getCountryCode());
		
		//CARBON MONOXIDE DATA GATHERING
		String coAddress = "http://api.openweathermap.org/pollution/v1/co/" + Math.round(longitude) + "," + Math.round(latitude) + "/current.json?appid=" + owApiKey;
		URL coUrl = new URL(coAddress);
		
		try(InputStream is = coUrl.openStream(); JsonReader rdr = Json.createReader(is)) {
			JsonObject obj = rdr.readObject();
			JsonArray pollutionData = obj.getJsonArray("data");
			Double val = pollutionData.getJsonObject(0).getJsonNumber("value").doubleValue();
			locInfo.setCarbonMonoxide(val);
			
		} catch (FileNotFoundException f) {
			locInfo.setCarbonMonoxide(Double.NaN);
			System.out.println("Warning no Carbon Monoxide Data for" + locInfo.getCityName());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//SULPHUR DIOXIDE DATA GATHERING
		String so2Address = "http://api.openweathermap.org/pollution/v1/so2/" + Math.round(longitude) + "," + Math.round(latitude) + "/current.json?appid=" + owApiKey;
		URL so2Url = new URL(so2Address);
		
		try(InputStream is = so2Url.openStream(); JsonReader rdr = Json.createReader(is)) {
			JsonObject obj = rdr.readObject();
			JsonArray pollutionData = obj.getJsonArray("data");
			Double val = pollutionData.getJsonObject(0).getJsonNumber("value").doubleValue();
			locInfo.setSulphurDioxide(val);
			
		} catch (FileNotFoundException f) {
			locInfo.setSulphurDioxide(Double.NaN);
			System.out.println("Warning no Sulphur Dioxide Data for" + locInfo.getCityName());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//NITROGEN DIOXIDE DATA GATHERING
		String no2Address = "http://api.openweathermap.org/pollution/v1/no2/" + Math.round(longitude) + "," + Math.round(latitude) + "/current.json?appid=" + owApiKey;
		URL no2Url = new URL(no2Address);
		
		try(InputStream is = no2Url.openStream(); JsonReader rdr = Json.createReader(is)) {
			JsonObject obj = rdr.readObject();
			if(obj.size() > 1){
				JsonArray pollutionData = obj.getJsonArray("data");
				JsonNumber no2Data = pollutionData.getJsonObject(0).getJsonObject("no2").getJsonNumber("value");
				locInfo.setNitrogenDioxide(no2Data.doubleValue());
			}
		} catch (FileNotFoundException f) {
			locInfo.setNitrogenDioxide(Double.NaN);
			System.out.println("Warning no Nitrogen Dioxide Data for " + locInfo.getCityName());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return locInfo;
	}
	
	/**
	 * Creates a list of {@code ISOCode} Objects for the Search capabilities on the app. 
	 * @return {@code ArrayList<ISOCode>}
	 */
	public static ArrayList<ISOCode> getISOCodeList() {
		ArrayList<ISOCode> countryCodes = new ArrayList<ISOCode>();
		Scanner scanner;
		
		scanner = new Scanner(API.class.getResourceAsStream("/ISO_Countrycodes.csv"));
		scanner.useDelimiter(",");
		scanner.nextLine();
		while(scanner.hasNextLine()){
			String iso = scanner.next();
			String country = scanner.next();
			
			countryCodes.add(new ISOCode(country, iso));
			scanner.nextLine();
		}
		scanner.close();

		return countryCodes;
	}
	
	//PRIVATE METHODS
	private static int kelvinToCelsius(float temp) {
		return (int) (temp - 273.15);
	}
}
