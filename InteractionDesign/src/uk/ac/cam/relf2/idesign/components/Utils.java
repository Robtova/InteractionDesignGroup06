package uk.ac.cam.relf2.idesign.components;

import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.Locale;

import javax.imageio.ImageIO;

public class Utils {
	
	public final static Image ERROR; 
	
	static {
		ERROR = loadImage("/weather_icons_coded/error.png");
	}
	
	public static Image loadImage(String name) {
		URL url = Utils.class.getResource(name);
		
		Image img = null;
		try {
			img = ImageIO.read(url);
		} catch (IOException | IllegalArgumentException e) {
			img = ERROR;
			e.printStackTrace();
		} 
		
		return img;
	}
	
	public static String leftPad(String s, int dig, String pad) {
		while(s.length() < dig) {
			s = pad + s;
		}
		
		return s;
	}
	
	/**
	 * 
	 * @param addD - if 0, the the date used is todays, 1 tomorrows and so on.
	 * @return A string of the date, and time.
	 */
	public static String getDateTimeString(int addD) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, addD);
		
		String date = cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.UK);
		
		date += " " + cal.get(Calendar.DAY_OF_MONTH);
		if(date.endsWith("1")) date+="st";
		else if(date.endsWith("2")) date+="nd";
		else if(date.endsWith("3")) date+="rd";
		else date+="th";
		
		date += " " + cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.UK);
		
		if(addD > 0) return date;
		
		return date += ", " + Utils.leftPad("" + (cal.get(Calendar.AM_PM)*12 + cal.get(Calendar.HOUR)), 2, "0") + ":" + Utils.leftPad("" + cal.get(Calendar.MINUTE), 2, "0");
	}
}
