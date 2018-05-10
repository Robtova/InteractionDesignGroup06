package uk.ac.cam.group06.idesign;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

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
	
	private String mDate = "";
	private float mTemperature = 15;
	private String mIcon = null;
	
	private static Font mFont = new Font("Ariel", Font.PLAIN, 25);
	
	public DayBreakdown() {
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
		
		intialiseInfoStack();
	}
	
	private void intialiseInfoStack() {
		mStack = new StackComponent();
		mStack.setY(72, true);
		mStack.setSize(100, false, 72, true);
		
		addTimeEntry("00:00", "01n", 15, 0.9f, "20%");
		addTimeEntry("03:00", "02n", 15, 0.6f, "20%");
		addTimeEntry("06:00", "03d", 15, 0.3f, "20%");
		addTimeEntry("09:00", "04d", 15, 0.9f, "20%");
		addTimeEntry("12:00", "09d", 15, 0.6f, "20%");
		addTimeEntry("15:00", "10d", 15, 0.3f, "20%");
		addTimeEntry("18:00", "11d", 15, 0.6f, "20%");
		addTimeEntry("21:00", "13n", 15, 0.9f, "20%");
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
	public void addTimeEntry(String time, String icon, float temperature, float pollution, String humidity) {
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
		
		Image img = SMALL_RED_ICON;
		if(pollution <= 0.667) img = SMALL_YELLOW_ICON;
		if(pollution <= 0.33) img = SMALL_GREEN_ICON;
		
		ImageComponent warning = new ImageComponent(img);
		warning.setSize(40, 40);
		warning.setPosition(92, 50, false);
		warning.setOrigin(GraphicComponent.MIDDLE_CENTRE);
		bar.addComponent(warning);
		
		TextComponent text2 = new TextComponent();
		text2.setText(humidity);
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
	public void setTemperature(float temp) {
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
	public void setDate(String date) {
		mDate = date;
		mDateText.setText(mDate);
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

	@Override
	public void setComponentListener(ComponentListener comp) {
		mTopBar.setComponentListener(comp);
	}
	
	@Override
	public void onClicked(int x, int y) {
		setOpen(!mOpen);
	}
}