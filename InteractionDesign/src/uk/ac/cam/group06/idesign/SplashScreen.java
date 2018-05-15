package uk.ac.cam.group06.idesign;

import java.awt.Color;

import uk.ac.cam.relf2.idesign.components.GraphicComponent;
import uk.ac.cam.relf2.idesign.components.StackComponent;
import uk.ac.cam.relf2.idesign.components.TextComponent;

public class SplashScreen extends GraphicComponent {
	
	private StackComponent mStack;

	public SplashScreen() {
		setSize(100, 100, false);
		setBackgroundColor(new Color(249, 249, 249, 255));
		
		mStack = new StackComponent();
		mStack.setOrigin(GraphicComponent.MIDDLE_CENTRE);
		mStack.setPosition(50, 50, false);
		mStack.setSize(100, 100, false);
		addComponent(mStack);
		
		addName("Ane Espeseth");
		addName("Patrick Ferris");
		addName("Jonas Fiala");
		addName("Rob Fraser");
		addName("FM Gardiner");
	}

	private void addName(String name) {
		TextComponent text = new TextComponent();
		text.setText(name);
		text.setPosition(50, 0, false);
		text.setHeight(36, true);
		text.setBackgroundColor(new Color(180, 180, 180));
		text.setAlign(TextComponent.CENTRE);
		mStack.addComponent(text);
	}
}
