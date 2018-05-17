package uk.ac.cam.group06.api;

public class ISOCode {
	private final String mCountryName;
	private final String mISOCode;
	
	public ISOCode(String countryName, String isoCode) {
		this.mCountryName = countryName;
		this.mISOCode = isoCode.toLowerCase();
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mISOCode == null) ? 0 : mISOCode.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ISOCode other = (ISOCode) obj;
		if (mISOCode == null) {
			if (other.mISOCode != null)
				return false;
		} else if (!mISOCode.equals(other.mISOCode))
			return false;
		return true;
	}
}
