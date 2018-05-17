package uk.ac.cam.group06.idesign;

import java.awt.Color;
import java.util.Arrays;
import java.util.Collections;

import uk.ac.cam.group06.api.LocationInformation;
import uk.ac.cam.group06.api.store.DataStore;
import uk.ac.cam.relf2.idesign.components.DropDownComponent;
import uk.ac.cam.relf2.idesign.components.GraphicComponent;
import uk.ac.cam.relf2.idesign.components.ImageComponent;

public class HomeScreen extends GraphicComponent {

	private WeatherIcon mIcon;
	private int mLvl;
	
	public HomeScreen() {
		initialiseHomeScreen();
	}
	
	private void initialiseHomeScreen() {
		setBackgroundColor(new Color(250, 250, 250));
		setSize(100, 100, false);
		
		WarningRing ring = new WarningRing();
		ring.setWarningLevel(mLvl);
		ring.setSize(60, 60, false);
		ring.setPosition(50, 50, false);
		ring.keepAspect(true);
		ring.setOrigin(ImageComponent.MIDDLE_CENTRE);
		addComponent(ring);
		
		mIcon = new WeatherIcon();
		mIcon.setSize(55, 55, false);
		mIcon.setPosition(22.5f, 22.5f, false);
		ring.addComponent(mIcon);

		MoreOptionsButton dots = new MoreOptionsButton();
		dots.setBorder(GraphicComponent.SCREEN_RIGHT, GraphicComponent.SCREEN_TOP);
		dots.setPosition(-20, 20);
		dots.setSize(100, 100);
		addComponent(dots);
		
		reloadData();
	}
	
	public void reloadData() {
		LocationInformation li = DataStore.getCurrentInformation(ApplicationFrame.getCity(), ApplicationFrame.getCountryCode());
		mIcon.setIcon(li.getIcon());
		
		PollutionLevel pi = new PollutionLevel(li);
		mLvl = pi.getLevel();
		
	}
}
