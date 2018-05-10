package uk.ac.cam.group06.idesign;

import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import uk.ac.cam.relf2.idesign.components.CircleComponent;
import uk.ac.cam.relf2.idesign.components.ImageComponent;

public class WeatherIconSmall extends CircleComponent {
	
	private static Map<String, Image> mIcons = new HashMap<String, Image>();

	private ImageComponent mCurrentIcon;
	
	public WeatherIconSmall() {
		super();
		
		mCurrentIcon = new ImageComponent(null);
		mCurrentIcon.setSize(90, 90, false);
		mCurrentIcon.setPosition(5, 5, false);
		this.addComponent(mCurrentIcon);
		
		setIcon("error");
	}

	public void setIcon(String ico) {
		if(!mIcons.containsKey(ico)) {
			URL addr = WeatherIconSmall.class.getResource("/weather_icons_small/"+ico+".png");
			if(addr == null) {
				mCurrentIcon.setImage(mIcons.get("error"));
				return;
			}
			Image im = null;
			try {
				im = ImageIO.read(addr);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			mIcons.put(ico, im);
		}
		mCurrentIcon.setImage(mIcons.get(ico));
	}
}
