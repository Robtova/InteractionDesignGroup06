package uk.ac.cam.relf2.idesign.components;

import java.awt.Color;
import java.awt.Graphics2D;

import uk.ac.cam.group06.idesign.ApplicationFrame;

public class TextFieldComponent extends GraphicComponent implements ComponentListener {

	private TextComponent mTextComponent;

	private String mText = "";
	
	private boolean mFocused = false;
	
	private int loop = 0;
	
	public TextFieldComponent() {
		mTextComponent = new TextComponent();
		mTextComponent.setSize(100, 100, false);
		mTextComponent.setPosition(10, true, 50, false);
		mTextComponent.setBackgroundColor(new Color(180, 180, 180));
		mTextComponent.setText("", 20);
		mTextComponent.setAlign(TextComponent.RIGHT);
		addComponent(mTextComponent);
		
		this.setBackgroundColor(new Color(180, 180, 180));
		this.setComponentListener(this);
	}
	
	public String getText() {
		return mText;
	}
	
	public void setFocused(boolean focus) {
		this.mFocused = focus;
	}
	
	public boolean getFocused() {
		return mFocused;
	}
	
	public void setText(String text) {
		this.mText = text;
	}
	
	public void onEnter(String text) {
	}
	
	@Override
	public void update() {
		if(!mFocused) {
			this.mTextComponent.setText(mText);
			return;
		}
		
		if(ApplicationFrame.getInput().getClicked()) {
			int mx = ApplicationFrame.getInput().getMouseX();
			int my = ApplicationFrame.getInput().getMouseY();
			
			if(mx < this.getAbsoluteX() || my < this.getAbsoluteY() 
					|| mx > this.getAbsoluteX() + getWidth() || my > this.getAbsoluteY() + getHeight()) {
				setFocused(false);
			}
		}
		
		int key = ApplicationFrame.getInput().getTypedKeyCode();
		if(key == 8 && mText.length() >= 1) setText(mText.substring(0, mText.length()-1));
		else if(key == 10) onEnter(getText());
		else if(key >= 0 && key != 8 && key != 10) setText(mText + (char) key);
		
		loop = (loop + 1) % 60;
		if(loop < 30) this.mTextComponent.setText(mText + "|");
		else this.mTextComponent.setText(mText);
	}
	
	@Override
	public void paint(Graphics2D g) {
		g.setColor(getBackgroundColor());
		g.fillRect(-1, -1, getWidth() + 2, getHeight() + 2);
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
	}

	@Override
	public void onClicked(int x, int y) {
		this.setFocused(true);
	}
}
