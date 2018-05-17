package uk.ac.cam.group06.api;

public class ISOCode {
	private final String mCountryName;
	private final String mISOCode;
	
	public ISOCode(String countryName, String isoCode) {
		this.mCountryName = countryName;
		this.mISOCode = isoCode;
	}
	
	/*
	 * This method is used for getting the associated country name with the 
	 * ISO 3166 Code. This way it can be easily used with the drop-down menu 
	 * of the search functionality within the app.
	 */
	@Override
	public String toString(){
		return this.mCountryName;
	}
	
	public String getISOCode() {
		return this.mISOCode;
	}

}
