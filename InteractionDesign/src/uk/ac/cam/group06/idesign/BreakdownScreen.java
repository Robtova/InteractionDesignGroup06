package uk.ac.cam.group06.idesign;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Calendar;
import java.util.Locale;

import uk.ac.cam.relf2.idesign.components.ComponentListener;
import uk.ac.cam.relf2.idesign.components.GraphicComponent;
import uk.ac.cam.relf2.idesign.components.ImageComponent;
import uk.ac.cam.relf2.idesign.components.StackComponent;
import uk.ac.cam.relf2.idesign.components.TextComponent;
import uk.ac.cam.relf2.idesign.components.Utils;

public class BreakdownScreen extends StackComponent {
	
	private GraphicComponent mHomeScreen;

	private DayBreakdown mTopBar;
	
	private float mScroll = 0;
	
	private Input input;
	
	public BreakdownScreen(GraphicComponent homeScreen, Input input) {
		this.mHomeScreen = homeScreen;
		initialise();
		
		this.input = input;
	}
	
	@Override
	public void paint(Graphics2D g) {
		super.paint(g);
		
		if(mTopBar.getOpen()) {
			if(getHeight() > mHomeScreen.getHeight()) {
				mScroll += input.getScroll() * 40;
				mScroll = Math.max(mHomeScreen.getHeight()-this.getHeight(), mScroll);
				mScroll = Math.min(0, mScroll);
			} else {
				mScroll = 0;
			}
			
			setPosition(0, mScroll);
		}
		
		String date = Utils.getDateTimeString(0);
		mTopBar.setText(date + ", 20 *C");
	}

	private void initialise() {
		this.setBackgroundColor(new Color(250, 250, 250));
		this.setSize(100, 100, false);
		
		mTopBar = new DayBreakdown();
		mTopBar.setSize(100, false, 72, true);
		addComponent(mTopBar);
		
		mTopBar.setComponentListener(new ComponentListener() {
			@Override
			public void onClicked(int x, int y) {
				mTopBar.setOpen(!mTopBar.getOpen());
				
				if(mTopBar.getOpen()) {
					mHomeScreen.setVisible(false);
				} else {
					setPosition(0, 92, false);
					mHomeScreen.setVisible(true);
				}
			}
		});
		
		for(int i = 1; i < 7; i++) {
			DayBreakdown d = new DayBreakdown();
			d.setText(Utils.getDateTimeString(i));
			d.setSize(100, false, 72, true);
			addComponent(d);
		}
	}
}
