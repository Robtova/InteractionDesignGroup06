package uk.ac.cam.group06.idesign;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import uk.ac.cam.group06.api.LocationInformation;
import uk.ac.cam.group06.api.store.DataStore;
import uk.ac.cam.relf2.idesign.components.ComponentListener;
import uk.ac.cam.relf2.idesign.components.GraphicComponent;
import uk.ac.cam.relf2.idesign.components.StackComponent;

public class BreakdownScreen extends StackComponent {

	private DayBreakdown mTopBar;
	
	private float mScroll = 0;
	private float mPercentageY = 0;

	private int mCoverHeight = 400;
	
	private List<DayBreakdown> mBreakdowns = new ArrayList<DayBreakdown>();

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
	
	public void reloadData() {
		LocationInformation li = DataStore.getCurrentInformation(ApplicationFrame.getCity(), ApplicationFrame.getCountryCode());

		mTopBar.reloadData();
		mTopBar.setTemperature(Integer.parseInt(li.getTemperature()));
		
		for(DayBreakdown db : mBreakdowns) {
			db.reloadData();
		}
	}

	private void initialise() {
		LocationInformation li = DataStore.getCurrentInformation(ApplicationFrame.getCity(), ApplicationFrame.getCountryCode());
		
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
			
			mBreakdowns.add(d);
		}
		
		GraphicComponent cover = new GraphicComponent();
		cover.setSize(100, false, mCoverHeight, true);
		cover.setBackgroundColor(new Color(249, 249, 249));
		addComponent(cover);
	}
}
