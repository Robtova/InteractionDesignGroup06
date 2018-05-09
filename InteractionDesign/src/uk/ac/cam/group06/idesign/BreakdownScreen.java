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

public class BreakdownScreen extends GraphicComponent {
	
	private GraphicComponent mHomeScreen;
	private boolean mOpen = false;

	TextComponent text;
	
	public BreakdownScreen(GraphicComponent homeScreen) {
		this.mHomeScreen = homeScreen;
		initialise();
	}
	
	@Override
	public void paint(Graphics2D g) {
		super.paint(g);
		
		if(mOpen) setPosition(0, Math.max(0, getY()-60));
		
		Calendar cal = Calendar.getInstance();
		String date = cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.UK);
				
		date += " " + cal.get(Calendar.DAY_OF_MONTH);
		cal.getDisplayName(Calendar.DAY_OF_MONTH, Calendar.LONG, Locale.UK);
		if(date.endsWith("1")) date+="st";
		else if(date.endsWith("2")) date+="nd";
		else if(date.endsWith("3")) date+="rd";
		else date+="th";
		
		date += " " + cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.UK);
		
		date += ", " + Utils.leftPad("" + (cal.get(Calendar.AM_PM)*12 + cal.get(Calendar.HOUR)), 2, "0") + ":" + Utils.leftPad("" + cal.get(Calendar.MINUTE), 2, "0");
		
		text.setText(date + ", 20 *C", 25);
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
		
		String date = getDateTimeString();
		
		text = new TextComponent();
		text.setText(date + ", 20 *C", 25);
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
		
		StackComponent stack = new StackComponent();
		stack.setPosition(0, 8, false);
		stack.setSize(600, 300);
		this.addComponent(stack);
		
		GraphicComponent gc1 = new GraphicComponent();
		gc1.setSize(600, 72);
		stack.addComponent(gc1);
		
		GraphicComponent gc2 = new GraphicComponent();
		gc2.setSize(600, 72);
		stack.addComponent(gc2);
		
		GraphicComponent gc3 = new GraphicComponent();
		gc3.setSize(600, 72);
		stack.addComponent(gc3);
		
		GraphicComponent gc4 = new GraphicComponent();
		gc4.setBackgroundColor(Color.WHITE);
		gc4.setSize(600, 72);
		stack.addComponent(gc4);
	}
	
	private String getDateTimeString() {
		Calendar cal = Calendar.getInstance();
		String date = cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.UK);
				
		date += " " + cal.get(Calendar.DAY_OF_MONTH);
		cal.getDisplayName(Calendar.DAY_OF_MONTH, Calendar.LONG, Locale.UK);
		if(date.endsWith("1")) date+="st";
		else if(date.endsWith("2")) date+="nd";
		else if(date.endsWith("3")) date+="rd";
		else date+="th";
		
		date += " " + cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.UK);
		
		return date += ", " + Utils.leftPad("" + (cal.get(Calendar.AM_PM)*12 + cal.get(Calendar.HOUR)), 2, "0") + ":" + Utils.leftPad("" + cal.get(Calendar.MINUTE), 2, "0");
	}
}
