package uk.ac.cam.relf2.idesign.components;

import java.awt.Color;
import java.awt.Graphics2D;

public class ToggleButton extends GraphicComponent implements ComponentListener {
	
	private GraphicComponent mCircle;
	
	private static Color OFF = new Color(180, 180, 180), ON = new Color(112, 255, 127);
	
	private boolean mON = true;

	public ToggleButton() {
		mCircle = new CircleComponent();
		mCircle.setBackgroundColor(Color.WHITE);
		mCircle.setPosition(2, 2, false);
		this.addComponent(mCircle);
		
		this.setComponentListener(this);
		
		this.setOn(false);
	}
	
	public boolean getOn() {
		return mON;
	}

	public void toggle() {
		this.setOn(!mON);
	}
	
	public void setOn(boolean on) {
		this.mON = on;
		setBackgroundColor(mON ? ON : OFF);
		
		if(mON) {
			mCircle.setPosition(-5, 5);
			mCircle.setOrigin(TOP_RIGHT);
			mCircle.setBorder(SCREEN_RIGHT, SCREEN_TOP);
		} else {
			mCircle.setPosition(5, 5);
			mCircle.setOrigin(TOP_LEFT);
			mCircle.setBorder(SCREEN_LEFT, SCREEN_TOP);
		}
	}
	
	@Override
	public void update() {
		float height = this.getHeight() - 10;
		mCircle.setSize(height, height);
	}
	
	@Override 
	public void paint(Graphics2D g) {
		g.setColor(getBackgroundColor());
		g.fillOval(0, 0, getHeight(), getHeight());
		g.fillOval(getWidth()-getHeight(), 0, getHeight(), getHeight());
		g.fillRect(getHeight() / 2, 0, getWidth() - getHeight(), getHeight());
	}

	@Override
	public void onClicked(int x, int y) {
		this.toggle();
	}
}
