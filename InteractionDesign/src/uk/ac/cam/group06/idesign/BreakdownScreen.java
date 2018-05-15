package uk.ac.cam.group06.idesign;

import java.awt.Color;
import java.awt.Graphics2D;
import java.net.MalformedURLException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import uk.ac.cam.group06.api.API;
import uk.ac.cam.group06.api.LocationInformation;
import uk.ac.cam.group06.api.store.DataStore;
import uk.ac.cam.relf2.idesign.components.ComponentListener;
import uk.ac.cam.relf2.idesign.components.GraphicComponent;
import uk.ac.cam.relf2.idesign.components.ImageComponent;
import uk.ac.cam.relf2.idesign.components.Input;
import uk.ac.cam.relf2.idesign.components.StackComponent;
import uk.ac.cam.relf2.idesign.components.TextComponent;
import uk.ac.cam.relf2.idesign.components.Utils;

public class BreakdownScreen extends StackComponent {

	private DayBreakdown mTopBar;
	
	private float mScroll = 0;
	private float mPercentageY = 0;

	private int mCoverHeight = 400;

	public BreakdownScreen() {
		initialise();
		
		setPosition(0, 92, false);
		mPercentageY = 92;
	}
	
	@Override
	public void update() {
		if(mTopBar.getOpen()) {
			GraphicComponent homeScreen = ApplicationFrame.getHomeScreen();
			
			if(getHeight()-mCoverHeight > homeScreen.getHeight()) {
				mScroll -= ApplicationFrame.getInput().getScroll() * 40;
				mScroll = Math.max(homeScreen.getHeight()-(getHeight()-mCoverHeight), mScroll);
				mScroll = Math.min(0, mScroll);
			} else {
				mScroll = 0;
			}
			
			setPosition(0, (float) Math.max(mScroll, getY() - 40));
			if(this.getY() <= 30) ApplicationFrame.getHomeScreen().setVisible(false);
		} else if(mPercentageY < 92) {
			mScroll = 0;
			mPercentageY = Math.min(92, mPercentageY+6);
			setPosition(0, mPercentageY, false);
		}
	}

	private void initialise() {
		LocationInformation li = DataStore.getCurrentInformation("cambridge", "uk");
		
		this.setBackgroundColor(new Color(249, 249, 249));
		this.setSize(100, 100, false);
		
		mTopBar = new DayBreakdown(Calendar.getInstance().getTime());
		mTopBar.setSize(100, false, 72, true);
		mTopBar.setTemperature(Integer.parseInt(li.getTemperature()));
		addComponent(mTopBar);
		
		GraphicComponent homeScreen = ApplicationFrame.getHomeScreen();
		
		mTopBar.setComponentListener(new ComponentListener() {
			@Override
			public void onClicked(int x, int y) {
				mTopBar.setOpen(!mTopBar.getOpen());
				
				if(mTopBar.getOpen()) {
					homeScreen.setClickable(false);
					mPercentageY = 0;
				} else {
					homeScreen.setClickable(true);
					homeScreen.setVisible(true);
				}
			}
		});
		
		for(int i = 1; i < 5; i++) {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DAY_OF_MONTH, i);
			DayBreakdown d = new DayBreakdown(cal.getTime());
			d.setAveragedData(true, true);
			d.setSize(100, false, 72, true);
			addComponent(d);
		}
		
		GraphicComponent cover = new GraphicComponent();
		cover.setSize(100, false, mCoverHeight, true);
		cover.setBackgroundColor(new Color(249, 249, 249));
		addComponent(cover);
	}
}
