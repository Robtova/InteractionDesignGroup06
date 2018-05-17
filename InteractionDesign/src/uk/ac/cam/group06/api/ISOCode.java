package uk.ac.cam.group06.api;

public class ISOCode {
	private final String mCountryName;
	private final String mISOCode;
	
	public ISOCode(String countryName, String isoCode) {
		this.mCountryName = countryName;
		this.mISOCode = isoCode;
	}
	
	@Override
	public String toString(){
		return this.mCountryName;
	}
	
	public String getISOCode() {
		return this.mISOCode;
	}

}
