package uk.ac.cam.group06.idesign;

import java.awt.Color;

import uk.ac.cam.relf2.idesign.components.ComponentListener;
import uk.ac.cam.relf2.idesign.components.GraphicComponent;
import uk.ac.cam.relf2.idesign.components.ImageComponent;
import uk.ac.cam.relf2.idesign.components.TextComponent;
import uk.ac.cam.relf2.idesign.components.Utils;

public class BreakdownScreen extends GraphicComponent {
	
	private GraphicComponent mHomeScreen;
	private boolean mOpen = false;
	
	public BreakdownScreen(GraphicComponent homeScreen) {
		this.mHomeScreen = homeScreen;
		initialise();
	}

	private void initialise() {
		this.setBackgroundColor(new Color(250, 250, 250));
		this.setSize(100, 100, false);

		GraphicComponent topBar = new GraphicComponent();
		topBar.setSize(100, 8, false);
		topBar.setBackgroundColor(Color.white);
		this.addComponent(topBar);
		
		ImageComponent shade = new ImageComponent(Utils.loadImage("/shade.png"));
		shade.setSize(100, 10, false);
		shade.setPosition(0, -10, false);
		topBar.addComponent(shade);
		
		TextComponent text = new TextComponent();
		text.setText("Tuesday 8th May, 13:45, 20 *C", 25);
		text.setPosition(50, 50, false);
		text.setBackgroundColor(new Color(180, 180, 180));
		topBar.addComponent(text);
		
		ImageComponent arrow = new ImageComponent(Utils.loadImage("/up_arrow.png"));
		arrow.setSize(30, 30);
		arrow.setPosition(90, 30, false);
		topBar.addComponent(arrow);
		
		topBar.setComponentListener(new ComponentListener() {
			@Override
			public void onClicked(int x, int y) {
				mOpen = !mOpen;
				
				if(mOpen) {
					setPosition(0, 0);
					arrow.setPosition(90, 70, false);
					arrow.setSize(30, -30);
					
					mHomeScreen.setVisible(false);
				} else {
					setPosition(0, 92, false);
					arrow.setPosition(90, 30, false);
					arrow.setSize(30, 30);
					
					mHomeScreen.setVisible(true);
				}
			}
		});
	}
}
