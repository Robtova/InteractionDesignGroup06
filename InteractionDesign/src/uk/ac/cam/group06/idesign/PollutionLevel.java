package uk.ac.cam.group06.idesign;

import sun.security.ssl.Debug;
import uk.ac.cam.group06.api.LocationInformation;

public class PollutionLevel  {
	private int mLevel = 0;					// simplified pollution ranking: 0-green, 1-yellow, 2-red
	private LocationInformation locinfo;
	
	private double nitrogenDioxide;		
	private double carbonMonoxide;
	private double sulphurDioxide;
	
	
	// Pollution bands
	private double ndMed = 97.435e-6;
	private double ndHigh = 194.870e-6;
	
	private double cmMed = 10.000e-6;
	private double cmHigh = 35.000e-6;
	
	private double sdMed = 93.492e-6; 
	private double sdHigh =  186.633e-6;

	
	public PollutionLevel(LocationInformation locRequested){
		locinfo = locRequested;
		
		nitrogenDioxide = Double.isNaN(locinfo.getNitrogenDioxide()) ? 0.0 : locinfo.getNitrogenDioxide();
		carbonMonoxide = Double.isNaN(locinfo.getCarbonMonoxide()) ? 0.0 : locinfo.getCarbonMonoxide();
		sulphurDioxide = Double.isNaN(locinfo.getSulphurDioxide()) ? 0.0 : locinfo.getSulphurDioxide();
				
		mLevel = calculateLevel();
	}
	
	private int calculateLevel(){
		// calculates simplified pollution ranking
		int ndScore = 0, cmScore = 0, sdScore = 0, warningNumber = 0;
		int overallScore;

		// if the pollutant is in the medium band, 1 point, if it's high, 2 points
		if(nitrogenDioxide > ndMed) {ndScore = (nitrogenDioxide > ndHigh) ? 2 : 1;}
		if(carbonMonoxide > cmMed) {cmScore = (carbonMonoxide > cmHigh) ? 2 : 1;}
		if(sulphurDioxide > sdMed) {sdScore = (sulphurDioxide > sdHigh) ? 2 : 1;}
		
		// combine the overall points for overall score, ranging from 0 to 6
		overallScore = ndScore + cmScore + sdScore;

		// change depending on if user is an asthma sufferer (don't have pollen data so just pretend)
		if(SettingsScreen.getForAsthmatics()) {
			int temp = Integer.valueOf(locinfo.getTemperature());
			overallScore += 2;
			if(temp >= 24) overallScore++; // Add 1 point for > 24
			if(temp <= 10) overallScore++; // Add 1 point for < 10
			if(temp < 5) overallScore++; // Add 2 points for < 5
		}

		// puts the overall score into bands: [0-2], [3-4], [5-6]
		if(overallScore > 2) {warningNumber = (overallScore > 4) ? 2 : 1;}
		return warningNumber;
	}
	
	public int getLevel() {
		return mLevel;
	}
}
