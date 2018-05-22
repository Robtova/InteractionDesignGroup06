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
import uk.ac.cam.group06.api.LocationInformation;
import uk.ac.cam.group06.api.store.DataStore;
import uk.ac.cam.relf2.idesign.components.ComponentListener;
import uk.ac.cam.relf2.idesign.components.GraphicComponent;
import uk.ac.cam.relf2.idesign.components.ImageComponent;
import uk.ac.cam.relf2.idesign.components.StackComponent;
import uk.ac.cam.relf2.idesign.components.TextComponent;
import uk.ac.cam.relf2.idesign.components.Utils;

public class DetailedBreakdown extends StackComponent implements ComponentListener {
	
	private boolean mOpen;

	/*The graphical elements needed in the breakdown view*/
	private ImageComponent mArrow;
	private StackComponent mStack;
	
	private static Image UP_ARROW_IMAGE = Utils.loadImage("/up_arrow.png");
	private static Image LOW_POLLUTION = Utils.loadImageSection("/pollution_levels.png", 0, 0, 64, 64),
			MEDIUM_POLLUTION = Utils.loadImageSection("/pollution_levels.png", 64, 0, 64, 64),
			HIGH_POLLUTION = Utils.loadImageSection("/pollution_levels.png", 128, 0, 64, 64);

	private static Color mStandardBackground = new Color(249, 249, 249);
	private static Color mDivide = new Color(0xCCCCCC);
	
	private static Font mFont = new Font("Ariel", Font.PLAIN, 25);
	private static Font mDetailFont = new Font("Ariel", Font.PLAIN, 20);
	/**/
	
	/*For top bar*/
	private Date mDate;
	private TextComponent mDateText;
	private GraphicComponent mTopBar;
	/**/
	
	/*Stores the current local information*/
	private LocationInformation li;
	/**/
	
	/*Holds the current pollution and weather data*/
	private double mCO; 
	private double mSO2; 
	private double mNO2; 
	private double mHumidity;
	private double mTemperature;
	/**/
	
	public DetailedBreakdown(Date date) {
		mDate = date;
		initialise();
	}
	
	/*Initialises the DetailedBreakDown with all its elements in their 
	 *start positions. Does not initialise the data values (see initialiseCurrentInfo).*/
	private void initialise() {
		// Creating the top bar (includes date only)
		mTopBar = new GraphicComponent();
		mTopBar.setSize(100, false, 72, true);
		mTopBar.setBackgroundColor(Color.white);
		this.addComponent(mTopBar);

		// Creating the line that divides stack elements
		GraphicComponent divide = new GraphicComponent();
		divide.setSize(100, false, 1, true);
		divide.setPosition(0, -1);
		divide.setBorder(GraphicComponent.SCREEN_LEFT, GraphicComponent.SCREEN_BOTTOM);
		divide.setBackgroundColor(mDivide);
		mTopBar.addComponent(divide);

		//Creating the date text in the top bar
		mDateText = new TextComponent();
		mDateText.setPosition(50, false, 50, false);
		mDateText.setBackgroundColor(new Color(180, 180, 180));
		mDateText.setAlign(TextComponent.CENTRE);
		mDateText.setFont(mFont);
		mDateText.setText(DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.SHORT).format(mDate));
		mTopBar.addComponent(mDateText);
		
		mArrow = new ImageComponent(UP_ARROW_IMAGE);
		mArrow.setBorder(GraphicComponent.SCREEN_RIGHT, GraphicComponent.SCREEN_TOP);
		mArrow.setSize(30, 30);
		mArrow.setPosition(-60, true, 50, false);
		mArrow.setOrigin(GraphicComponent.MIDDLE_LEFT);
		mTopBar.addComponent(mArrow);
		
		mTopBar.setComponentListener(this);
		
		initialiseCurrentInfo();
		intialiseInfoStack();
	}
	
	/*Initialises the stack, where the individual values will be shown.*/
	private void intialiseInfoStack() {
		mStack = new StackComponent();
		mStack.setY(72, true);
		mStack.setSize(100, false, 72, true);

		// This is the format we want, to be able to associate an entry with an information box: 
		// addEntry("CO", mCO, COInfoEntry);
		
		addEntry("CO", mCO, "PPB");
		addEntry("SO2", mSO2, "PPB");
		addEntry("NO2", mNO2, "PPB");
		addEntry("Humidity", mHumidity, "%");
		addEntry("Temperature", mTemperature, "*C");
	}

	
	/**
	 * Add an entry to the drop down stack of the detailed breakdown.
	 * 
	 * @param type - type to be displayed
	 * @param value - value of pollution type
	 * @param measureType - eg. PPB, % or *C
	 */
	private void addEntry(String type, double value, String measureType) {
		/*Creates the stack bar and draws dividing lines*/
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
		/**/
		
		/*Framework to set type*/
		TextComponent text = new TextComponent();
		text.setText(type);
		text.setFont(mFont);
		text.setPosition(25, true, 50, false);
		text.setAlign(TextComponent.RIGHT);
		text.setBackgroundColor(new Color(180, 180, 180));
		bar.addComponent(text);
		/**/
		
		
		/*Decides which pollution icon to use*/
		// Pollution bands
		double ndMed = 97.435e-6;
		double ndHigh = 194.870e-6;
				
		double cmMed = 10.000e-6;
		double cmHigh = 35.000e-6;
				
		double sdMed = 93.492e-6; 
		double sdHigh =  186.633e-6;
				 
		Image img = LOW_POLLUTION;
		
		if(value >= ndMed && type == "NO2") 
			img = MEDIUM_POLLUTION;
		if(value >= cmMed && type == "CO") 
			img = MEDIUM_POLLUTION;
		if(value >= sdMed && type == "SO2") 
			img = MEDIUM_POLLUTION;
		
		if(value >= ndHigh && type == "NO2") 
			img = HIGH_POLLUTION;
		if(value >= cmHigh && type == "CO")  
			img = HIGH_POLLUTION;
		if(value >= sdHigh && type == "SO2") 
			img = HIGH_POLLUTION;
		/**/
		
		
		/*Framework to add icon*/
		ImageComponent warning = new ImageComponent(img);
		warning.setSize(40, 40);
		warning.setPosition(92, 50, false);
		warning.setOrigin(GraphicComponent.MIDDLE_CENTRE);
		bar.addComponent(warning);
		/**/
		
		/*Framework to add value text - values converted to integers for better readability.*/
		TextComponent text2 = new TextComponent();
		if (type == "CO" || type == "NO2" || type == "SO2") {
			if (Double.isNaN(value)) {
				text2.setFont(mDetailFont);
				text2.setText("Not available for " + ApplicationFrame.getCity() + " yet.");
			} else {
				text2.setFont(mFont);
				text2.setText((int)(value*1000000000.0) + measureType);
			}
		} else {
			text2.setText((int)value + measureType);
		}
		text2.setPosition(85, 50, false);
		text2.setAlign(TextComponent.LEFT);
		text2.setBackgroundColor(new Color(180, 180, 180));
		bar.addComponent(text2);
		/**/
	}
	
	/*Initialises the info to be displayed using the DataStore, 
	 *based on the location given in the ApplicationFrame.*/
	public void initialiseCurrentInfo() {
		li = DataStore.getCurrentInformation(ApplicationFrame.getCity(), ApplicationFrame.getCountryCode());

		mCO = li.getCarbonMonoxide(); 
		System.out.println(mCO);
		mSO2 = li.getSulphurDioxide(); 
		System.out.println(mSO2);
		mNO2 =  li.getNitrogenDioxide(); 
		System.out.println(mNO2);
		mHumidity = Integer.parseInt(li.getHumidity()); 
		mTemperature = Integer.parseInt(li.getTemperature());
	}
	
	public void reloadData() {
		initialiseCurrentInfo();
		mStack.clearComponents();
		
		addEntry("CO", mCO, "PPB");
		addEntry("SO2", mSO2, "PPB");
		addEntry("NO2", mNO2, "PPB");
		addEntry("Humidity", mHumidity, "%");
		addEntry("Temperature", mTemperature, "*C");
	}
	

	/**
	 * The date to be displayed in the top bar.
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
	
	@Override
	public void setComponentListener(ComponentListener comp) {
		mTopBar.setComponentListener(comp);
	}
	
	@Override
	public void onClicked(int x, int y) {
		setOpen(!mOpen);
	}
}
