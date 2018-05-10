package uk.ac.cam.group06.idesign;

import java.awt.Color;
import java.awt.Image;

import uk.ac.cam.relf2.idesign.components.ComponentListener;
import uk.ac.cam.relf2.idesign.components.ImageComponent;
import uk.ac.cam.relf2.idesign.components.StackComponent;
import uk.ac.cam.relf2.idesign.components.TextComponent;
import uk.ac.cam.relf2.idesign.components.Utils;

public class DayBreakdown extends StackComponent implements ComponentListener {
	
	private boolean mOpen;

	private ImageComponent mArrow;
	private StackComponent mStack;
	
	private static Image BACKGROUND = Utils.loadImage("/InfoItem.png");
	private static Image UP_ARROW_IMAGE = Utils.loadImage("/up_arrow.png");
	
	private static Image SMALL_RED_ICON = Utils.loadImageSection("/small_warnings.png", 0, 0, 64, 64),
			SMALL_YELLOW_ICON = Utils.loadImageSection("/small_warnings.png", 64, 0, 64, 64),
			SMALL_GREEN_ICON = Utils.loadImageSection("/small_warnings.png", 128, 0, 64, 64);

	
	private TextComponent mDateText;
	private TextComponent mTempText;
	private ImageComponent mTopBar;
	private WeatherIconSmall mSmallIcon;
	
	private String mDate = "";
	private float mTemperature = 15;
	private String mIcon = null;
	
	public DayBreakdown() {
		initialise();
	}
	
	private void initialise() {
		mTopBar = new ImageComponent(BACKGROUND);
		mTopBar.setSize(100, false, 72, true);
		mTopBar.setBackgroundColor(Color.white);
		this.addComponent(mTopBar);

		mDateText = new TextComponent();
		mDateText.setPosition(25, true, 50, false);
		mDateText.setBackgroundColor(new Color(180, 180, 180));
		mDateText.setAlign(TextComponent.RIGHT);
		mTopBar.addComponent(mDateText);
		
		mTempText = new TextComponent();
		mTempText.setPosition(85, 50, false);
		mTempText.setBackgroundColor(new Color(180, 180, 180));
		mTempText.setAlign(TextComponent.LEFT);
		mTopBar.addComponent(mTempText);
		
		mSmallIcon = new WeatherIconSmall();
		mSmallIcon.setSize(64, 64);
		mSmallIcon.setPosition(4, 4);
		
		mArrow = new ImageComponent(UP_ARROW_IMAGE);
		mArrow.setSize(30, 30);
		mArrow.setPosition(90, 30, false);
		mTopBar.addComponent(mArrow);
		
		mTopBar.setComponentListener(this);
		
		intialiseInfoStack();
	}
	
	public void setTemperature(float temp) {
		mTemperature = temp;
		mTempText.setText(mTemperature + "*C", 25);
	}
	
	@Override
	public void setComponentListener(ComponentListener comp) {
		mTopBar.setComponentListener(comp);
	}
	
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

	public void setDate(String date) {
		mDate = date;
		mDateText.setText(mDate, 25);
	}
	
	Color mStandardBackground = new Color(249, 249, 249);
	
	private void intialiseInfoStack() {
		mStack = new StackComponent();
		mStack.setY(72, true);
		mStack.setSize(100, false, 72, true);
		
		addTimeEntry("00:00", "01n", 15, 0.9f, "fgggg");
		addTimeEntry("03:00", "02n", 15, 0.9f, "fgggg");
		addTimeEntry("06:00", "03d", 15, 0.9f, "fgggg");
		addTimeEntry("09:00", "04d", 15, 0.9f, "fgggg");
		addTimeEntry("12:00", "09d", 15, 0.9f, "fgggg");
		addTimeEntry("15:00", "10d", 15, 0.9f, "fgggg");
		addTimeEntry("18:00", "11d", 15, 0.9f, "fgggg");
		addTimeEntry("21:00", "13n", 15, 0.9f, "fgggg");
	}
	
	public void addTimeEntry(String time, String icon, float temperature, float pollution, String humidity) {
		ImageComponent bar = new ImageComponent(BACKGROUND);
		bar.setSize(100, false, 72, true);
		bar.setBackgroundColor(mStandardBackground);
		mStack.addComponent(bar);

		TextComponent text = new TextComponent();
		text.setText(time + "   --   " + temperature + "*C", 25);
		text.setPosition(25, true, 50, false);
		text.setAlign(TextComponent.RIGHT);
		text.setBackgroundColor(new Color(180, 180, 180));
		bar.addComponent(text);
		
		Image img = SMALL_RED_ICON;
		if(pollution <= 0.667) img = SMALL_YELLOW_ICON;
		if(pollution <= 0.33) img = SMALL_GREEN_ICON;
		
		ImageComponent warning = new ImageComponent(img);
		warning.setSize(40, 40);
		warning.setPosition(89, false, 16, true);
		bar.addComponent(warning);
		
		TextComponent text2 = new TextComponent();
		text2.setText(humidity, 25);
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
	
	public void setOpen(boolean open) {
		mOpen = open;
		if(mOpen) {
			mArrow.setSize(30, -30);
			mArrow.setPosition(90, 70, false);
			addComponent(mStack);
		} else {
			mArrow.setSize(30, 30);
			mArrow.setPosition(90, 30, false);
			removeComponent(mStack);
		}
	}
	
	public boolean getOpen() {
		return mOpen;
	}

	@Override
	public void onClicked(int x, int y) {
		setOpen(!mOpen);
	}
}
