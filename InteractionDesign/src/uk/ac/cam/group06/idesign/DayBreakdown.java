package uk.ac.cam.group06.idesign;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import uk.ac.cam.group06.api.API;
import uk.ac.cam.group06.api.HourlyData;
import uk.ac.cam.group06.api.HourlyLocationInformation;
import uk.ac.cam.group06.api.store.DataStore;
import uk.ac.cam.relf2.idesign.components.ComponentListener;
import uk.ac.cam.relf2.idesign.components.GraphicComponent;
import uk.ac.cam.relf2.idesign.components.ImageComponent;
import uk.ac.cam.relf2.idesign.components.StackComponent;
import uk.ac.cam.relf2.idesign.components.TextComponent;
import uk.ac.cam.relf2.idesign.components.Utils;

public class DayBreakdown extends StackComponent implements ComponentListener {
	
	private boolean mOpen;

	private ImageComponent mArrow;
	private StackComponent mStack;
	
	private static Image UP_ARROW_IMAGE = Utils.loadImage("/up_arrow.png");
	
	private static Image SMALL_RED_ICON = Utils.loadImageSection("/small_warnings.png", 0, 0, 64, 64),
			SMALL_YELLOW_ICON = Utils.loadImageSection("/small_warnings.png", 64, 0, 64, 64),
			SMALL_GREEN_ICON = Utils.loadImageSection("/small_warnings.png", 128, 0, 64, 64);

	
	private static Color mStandardBackground = new Color(249, 249, 249);
	private static Color mDivide = new Color(0xCCCCCC);
	
	private TextComponent mDateText;
	private TextComponent mTempText;
	private GraphicComponent mTopBar;
	private WeatherIconSmall mSmallIcon;

	private Date mDate;
	private int mTemperature = 15;
	private String mIcon = null;
	
	private static Font mFont = new Font("Ariel", Font.PLAIN, 25);

	private List<HourlyData> mTodaysData;
	
	public DayBreakdown(Date date) {
		mDate = date;
		initialise();
	}
	
	private void initialise() {
		mTopBar = new GraphicComponent();
		mTopBar.setSize(100, false, 72, true);
		mTopBar.setBackgroundColor(Color.white);
		this.addComponent(mTopBar);

		GraphicComponent divide = new GraphicComponent();
		divide.setSize(100, false, 1, true);
		divide.setPosition(0, -1);
		divide.setBorder(GraphicComponent.SCREEN_LEFT, GraphicComponent.SCREEN_BOTTOM);
		divide.setBackgroundColor(mDivide);
		mTopBar.addComponent(divide);

		mDateText = new TextComponent();
		mDateText.setPosition(25, true, 50, false);
		mDateText.setBackgroundColor(new Color(180, 180, 180));
		mDateText.setAlign(TextComponent.RIGHT);
		mDateText.setFont(mFont);
		mDateText.setText(DateFormat.getDateInstance(DateFormat.LONG).format(mDate));
		mTopBar.addComponent(mDateText);
		
		mTempText = new TextComponent();
		mTempText.setPosition(85, 50, false);
		mTempText.setBackgroundColor(new Color(180, 180, 180));
		mTempText.setAlign(TextComponent.LEFT);
		mTempText.setFont(mFont);
		mTopBar.addComponent(mTempText);
		
		mSmallIcon = new WeatherIconSmall();
		mSmallIcon.setSize(64, 64);
		mSmallIcon.setPosition(4, 4);
		
		mArrow = new ImageComponent(UP_ARROW_IMAGE);
		mArrow.setBorder(GraphicComponent.SCREEN_RIGHT, GraphicComponent.SCREEN_TOP);
		mArrow.setSize(30, 30);
		mArrow.setPosition(-60, true, 50, false);
		mArrow.setOrigin(GraphicComponent.MIDDLE_LEFT);
		mTopBar.addComponent(mArrow);
		
		mTopBar.setComponentListener(this);
		
		initialiseDailyInfo();
		intialiseInfoStack();
	}
	
	private void intialiseInfoStack() {
		mStack = new StackComponent();
		mStack.setY(72, true);
		mStack.setSize(100, false, 72, true);

		for(int i = 0; i < mTodaysData.size(); i++) {
			HourlyData hd = mTodaysData.get(i);
			Date date = hd.getDate();

			String time = Utils.leftPad(date.getHours()+"", 2, "0") + ":" + Utils.leftPad(date.getMinutes()+"", 2, "0");
			addTimeEntry(time, hd.getIcon(), hd.getTemperature(), hd.getHumidity());
		}
	}

	private void initialiseDailyInfo() {
		HourlyLocationInformation hli = DataStore.getFiveDayForecast("cambridge", "uk");

		this.mTodaysData = new ArrayList<HourlyData>();
		
		Date today;
		Date tomorrow;
		{
			Calendar todayCal = Calendar.getInstance();
			todayCal.setTime(mDate);
			todayCal.set(Calendar.HOUR_OF_DAY, 0);
			Calendar tomorrowCal = Calendar.getInstance();
			tomorrowCal.setTime(mDate);
			tomorrowCal.add(Calendar.DAY_OF_MONTH, 1);
			tomorrowCal.set(Calendar.HOUR_OF_DAY, 0);
			
			today = todayCal.getTime();
			tomorrow = tomorrowCal.getTime();
		}
		
		for(int i = 0; i < hli.getForecast().size(); i++) {
			HourlyData hd = hli.getForecast().get(i);
			Date date = hd.getDate();
			if(today.after(date) || tomorrow.before(date)) continue;

			mTodaysData.add(hd);
		}
	}
	
	
	/**
	 * Add a time entry to the drop down stack of the day breakdown.
	 * 
	 * @param time - time to be displayed
	 * @param icon - icon to be displayed (none if null)
	 * @param temperature - temperature to be displayed
	 * @param pollution - pollution level to be displayed
	 * @param humidity - humidity to be displayed
	 */
	public void addTimeEntry(String time, String icon, String temperature, String humidity) {
		GraphicComponent bar = new GraphicComponent();
		bar.setSize(100, false, 72, true);
		bar.setBackgroundColor(mStandardBackground);
		mStack.addComponent(bar);
		
		GraphicComponent divide = new GraphicComponent();
		divide.setSize(100, false, 1, true);
		divide.setBackgroundColor(mDivide);
		divide.setPosition(0, -1);
		divide.setBorder(GraphicComponent.SCREEN_LEFT, GraphicComponent.SCREEN_BOTTOM);
		bar.addComponent(divide);

		TextComponent text = new TextComponent();
		text.setText(time + "   --   " + temperature + "*C");
		text.setFont(mFont);
		text.setPosition(25, true, 50, false);
		text.setAlign(TextComponent.RIGHT);
		text.setBackgroundColor(new Color(180, 180, 180));
		bar.addComponent(text);
		
		float level = 0.3f;
		
		Image img = SMALL_RED_ICON;
		if(level <= 0.667) img = SMALL_YELLOW_ICON;
		if(level <= 0.33) img = SMALL_GREEN_ICON;
		
		ImageComponent warning = new ImageComponent(img);
		warning.setSize(40, 40);
		warning.setPosition(92, 50, false);
		warning.setOrigin(GraphicComponent.MIDDLE_CENTRE);
		bar.addComponent(warning);
		
		TextComponent text2 = new TextComponent();
		text2.setText(humidity+"%");
		text2.setFont(mFont);
		text2.setPosition(85, 50, false);
		text2.setAlign(TextComponent.LEFT);
		text2.setBackgroundColor(new Color(180, 180, 180));
		bar.addComponent(text2);
		
		if(icon != null) {
			WeatherIconSmall iconImg = new WeatherIconSmall();
			iconImg.setSize(64, 64);
			iconImg.setPosition(240, 4);
			iconImg.setIcon(icon);
			bar.addComponent(iconImg);
		}
	}
	
	/**
	 * Set the temperature to be displayed on the top bar.
	 * 
	 * @param temp - temperature to be displayed
	 */
	public void setTemperature(int temp) {
		mTemperature = temp;
		mTempText.setText(mTemperature + "*C");
	}
	
	/**
	 * Set the icon to be displayed on the top bar.
	 * @param ico - the icon to be displayed (none is null)
	 */
	public void setIcon(String ico) {
		mIcon = ico;
		if(mIcon == null) {
			mSmallIcon.setIcon("error");
			mTopBar.removeComponent(mSmallIcon);
			mDateText.setX(25, true);
		} else {
			mSmallIcon.setIcon(mIcon);
			mTopBar.addComponent(mSmallIcon);
			mDateText.setX(64+4+8, true);
		}
	}

	/**
	 * The date to be displayed.
	 * @param date - date to be displayed
	 */
	public void setDate(Date date) {
		mDate = date;
		mDateText.setText(DateFormat.getDateInstance(DateFormat.LONG).format(date));
	}

	/**
	 * Set whether the breakdown has its time entry stack showing.
	 * @param open - whether the stack is showing
	 */
	public void setOpen(boolean open) {
		mOpen = open;
		if(mOpen) {
			mArrow.setSize(30, -30);
			addComponent(mStack);
		} else {
			mArrow.setSize(30, 30);
			removeComponent(mStack);
		}
	}
	
	/**
	 * Returns whether the time entry stack is showing.
	 * 
	 * @return true if stack is showing.
	 */
	public boolean getOpen() {
		return mOpen;
	}
	
	public void setAveragedData(boolean showWeather, boolean showTemp) {
		if(showTemp || showWeather) {
			int temp = 0;

			for(int i = 0; i < mTodaysData.size(); i++) {
				HourlyData hd = mTodaysData.get(i);
				Date date = hd.getDate();

				temp = Math.max(Integer.parseInt(hd.getTemperature()), temp);
				
				if(showWeather && date.getHours() > 10 && date.getHours() <= 13) this.setIcon(hd.getIcon());
			}
			if(showTemp) setTemperature(temp);
		}
	}

	@Override
	public void setComponentListener(ComponentListener comp) {
		mTopBar.setComponentListener(comp);
	}
	
	@Override
	public void onClicked(int x, int y) {
		setOpen(!mOpen);
	}
}
