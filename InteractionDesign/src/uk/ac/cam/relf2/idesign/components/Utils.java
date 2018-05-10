package uk.ac.cam.relf2.idesign.components;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
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
	
	/**
	 * Load an image from a specific path.
	 * 
	 * @param name - path name
	 * @return an Image object of the texture
	 */
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
	
	/**
	 * Loads a section of an image from a specific path.
	 * 
	 * @param name - path name
	 * @param x - the source x coordinate
	 * @param y - the source y coordinate
	 * @param w - the desired width
	 * @param h - the desired height
	 * @return an Image object of the desired section of the texture
	 */
	public static Image loadImageSection(String name, int x, int y, int w, int h) {
		URL url = Utils.class.getResource(name);
		
		Image img = null;
		try {
			img = ImageIO.read(url);
		} catch (IOException | IllegalArgumentException e) {
			img = ERROR;
		} 
		
		BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics g = bi.getGraphics();
		
		g.drawImage(img, 0, 0, w, h, x, y, x+w, y+h, null);
		
		g.dispose();
		
		return bi;
	}
	
	/**
	 * Left pads a string with another string up to a certain length.
	 * 
	 * @param s - the string to pad
	 * @param len - desired length
	 * @param pad - the string used to pad
	 * @return the padded string
	 */
	public static String leftPad(String s, int len, String pad) {
		while(s.length() < len) {
			s = pad + s;
		}
		
		return s;
	}
	
	/**
	 * Returns a string of the date and time.
	 * 
	 * @param addD - if 0, the the date used is todays, 1 tomorrows and so on. If > 0 then no time is shown.
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
