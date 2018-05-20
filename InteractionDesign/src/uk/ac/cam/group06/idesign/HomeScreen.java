package uk.ac.cam.group06.idesign;

import java.awt.Color;

import uk.ac.cam.group06.api.LocationInformation;
import uk.ac.cam.group06.api.store.DataStore;
import uk.ac.cam.relf2.idesign.components.GraphicComponent;
import uk.ac.cam.relf2.idesign.components.ImageComponent;

public class HomeScreen extends GraphicComponent {

	private WeatherIcon mIcon;
	private int mLvl;
	
	WarningRing mRing;
	
	public HomeScreen() {
		initialiseHomeScreen();
	}

	private void initialiseHomeScreen() {
		setBackgroundColor(new Color(250, 250, 250));
		setSize(100, 100, false);
		
		mRing = new WarningRing();
		mRing.setWarningLevel(mLvl);
		mRing.setSize(60, 60, false);
		mRing.setPosition(50, 50, false);
		mRing.keepAspect(true);
		mRing.setOrigin(ImageComponent.MIDDLE_CENTRE);
		addComponent(mRing);
		
		mIcon = new WeatherIcon();
		mIcon.setSize(55, 55, false);
		mIcon.setPosition(22.5f, 22.5f, false);
		mRing.addComponent(mIcon);

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
		
		mRing.setWarningLevel(mLvl);
	}
	
	public WeatherIcon getWeatherIcon() {
		return mIcon; 
	}
	
}
