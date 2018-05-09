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
	
	private Image background = Utils.loadImage("/InfoItem.png");
	
	TextComponent mTopBarText;
	ImageComponent mTopBar;
	
	private float mTemperature = 15;
	private float mHumidity = 15;
	
	public DayBreakdown() {
		initialise();
	}
	
	private void initialise() {
		mTopBar = new ImageComponent(background);
		mTopBar.setSize(100, false, 72, true);
		mTopBar.setBackgroundColor(Color.white);
		this.addComponent(mTopBar);

		mTopBarText = new TextComponent();
		mTopBarText.setPosition(50, 50, false);
		mTopBarText.setBackgroundColor(new Color(180, 180, 180));
		mTopBar.addComponent(mTopBarText);
		
		mArrow = new ImageComponent(Utils.loadImage("/up_arrow.png"));
		mArrow.setSize(30, 30);
		mArrow.setPosition(90, 30, false);
		mTopBar.addComponent(mArrow);
		
		mTopBar.setComponentListener(this);
		
		intialiseInfoStack();
	}
	
	@Override
	public void setComponentListener(ComponentListener comp) {
		mTopBar.setComponentListener(comp);
	}
	
	public void setText(String t) {
		mTopBarText.setText(t, 25);
	}
	
	Color mStandardBackground = new Color(249, 249, 249);
	
	private void intialiseInfoStack() {
		mStack = new StackComponent();
		mStack.setY(72, true);
		mStack.setSize(100, false, 72, true);
		
		ImageComponent bar1 = new ImageComponent(background);
		bar1.setSize(100, false, 72, true);
		bar1.setBackgroundColor(mStandardBackground);
		mStack.addComponent(bar1);

		TextComponent text1 = new TextComponent();
		text1.setText("Temperature: " + mTemperature + "*C", 25);
		text1.setPosition(25, true, 50, false);
		text1.setAlign(TextComponent.RIGHT);
		text1.setBackgroundColor(new Color(180, 180, 180));
		bar1.addComponent(text1);
		
		ImageComponent bar2 = new ImageComponent(background);
		bar2.setSize(100, false, 72, true);
		bar2.setBackgroundColor(mStandardBackground);
		mStack.addComponent(bar2);

		TextComponent text2 = new TextComponent();
		text2.setText("Humidity: " + mHumidity + "%", 25);
		text2.setPosition(25, true, 50, false);
		text2.setAlign(TextComponent.RIGHT);
		text2.setBackgroundColor(new Color(180, 180, 180));
		bar2.addComponent(text2);
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
