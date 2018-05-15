package uk.ac.cam.group06.idesign;

import java.awt.Color;

import uk.ac.cam.group06.api.LocationInformation;
import uk.ac.cam.group06.api.store.DataStore;
import uk.ac.cam.relf2.idesign.components.GraphicComponent;
import uk.ac.cam.relf2.idesign.components.ImageComponent;

public class HomeScreen extends GraphicComponent {

	public HomeScreen() {
		initialiseHomeScreen();
	}
	
	private void initialiseHomeScreen() {
		LocationInformation li = DataStore.getCurrentInformation("cambridge", "uk");
		
		setBackgroundColor(new Color(250, 250, 250));
		setSize(100, 100, false);
		
		WarningRing ring = new WarningRing();
		ring.setSize(60, 60, false);
		ring.setPosition(50, 50, false);
		ring.keepAspect(true);
		ring.setOrigin(ImageComponent.MIDDLE_CENTRE);
		addComponent(ring);
		
		WeatherIcon icon = new WeatherIcon();
		icon.setSize(55, 55, false);
		icon.setPosition(22.5f, 22.5f, false);
		ring.addComponent(icon);

		MoreOptionsButton dots = new MoreOptionsButton();
		dots.setBorder(GraphicComponent.SCREEN_RIGHT, GraphicComponent.SCREEN_TOP);
		dots.setPosition(-20, 20);
		dots.setSize(100, 100);
		addComponent(dots);
		
		icon.setIcon(li.getIcon());
	}
}
