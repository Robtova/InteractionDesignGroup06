package uk.ac.cam.relf2.idesign.components;

import java.awt.Image;
import java.io.IOException;
import java.net.URL;

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
}
