package uk.ac.cam.group06.idesign;

import java.awt.Color;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import uk.ac.cam.group06.api.LocationInformation;
import uk.ac.cam.group06.api.store.DataStore;
import uk.ac.cam.relf2.idesign.components.ComponentListener;
import uk.ac.cam.relf2.idesign.components.GraphicComponent;
import uk.ac.cam.relf2.idesign.components.ImageComponent;
import uk.ac.cam.relf2.idesign.components.StackComponent;

public class DetailedBreakdownScreen extends StackComponent {

	private DetailedBreakdown mTopBar;
	private HomeScreen homeScreen = (HomeScreen) ApplicationFrame.getHomeScreen();
	private WeatherIcon mWeatherIcon = homeScreen.getWeatherIcon(); 
	
	private float mScroll = 0;
	private float mPercentageY = 0;
	
	// Background-coloured square that covers the home screen, size in pixels
	private int mCoverHeight = 400;

	public DetailedBreakdownScreen() {
		initialise();
		
		setPosition(0, 100, false); // Initial position will be right below visible screen.
		mPercentageY = 100;
	}
	
	/*Update function for the detailed breakdown screen*/
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
		} else if(mPercentageY < 100) {
			mScroll = 0;
			mPercentageY = Math.min(100, mPercentageY+6);
			setPosition(0, mPercentageY, false);
		}
	}

	/*Initialises the detailed breakdown screen with current information, 
	 *location initially set to Cambridge, UK.*/
	private void initialise() {
		LocationInformation li = DataStore.getCurrentInformation("cambridge", "uk");
		this.setBackgroundColor(new Color(249, 249, 249));
		this.setSize(100, 100, false);

		/*Creates a click-able top bar, initially placed below the visible screen, 
		 *that will close the detailed view when clicked.*/
		mTopBar = new DetailedBreakdown(Calendar.getInstance().getTime());
		mTopBar.setSize(100, false, 72, true);
		addComponent(mTopBar);

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
		/**/
		
		/*Makes the Weather Icon on the main screen open the detailed view on click.*/
		mWeatherIcon.setComponentListener(new ComponentListener() {
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
		/**/

		/* Hides the main screen (otherwise seen through the information table). */
		GraphicComponent cover = new GraphicComponent();
		cover.setSize(100, false, mCoverHeight, true);
		cover.setBackgroundColor(new Color(249, 249, 249));
		addComponent(cover);
		/**/
	}
}
