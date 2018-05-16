package uk.ac.cam.relf2.idesign.components;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import uk.ac.cam.group06.idesign.ApplicationFrame;

public class DropDownComponent<T> extends GraphicComponent {
	
	private StackComponent mStack;
	private TextComponent mCurrent;
	private ImageComponent mArrow;
	
	private final List<T> mItems = new ArrayList<T>();
	private int mChosen = 0;
	
	private static final Color BACKGROUND = new Color(245, 245, 245);
	private static final Color CHOSEN = new Color(230, 230, 230);
	private static final Color TEXT = new Color(180, 180, 180);
	
	private static Image UP_ARROW_IMAGE = Utils.loadImage("/up_arrow.png");
	
	private boolean mOpen = false;
	
	public DropDownComponent(List<T> items) {
		this.mItems.addAll(items);
		
		this.setBackgroundColor(TEXT);
		
		mStack = new StackComponent();
		mStack.setPosition(0, 100, false);
		mStack.setWidth(100, false);
		
		for(int i = 0; i < mItems.size(); i++) {
			T item = mItems.get(i);
			GraphicComponent comp = new GraphicComponent();
			comp.setSize(100, false, 35, true);
			comp.setBackgroundColor(BACKGROUND);
			mStack.addComponent(comp);
			
			TextComponent text = new TextComponent();
			text.setText(item.toString(), 20);
			text.setSize(100, 100, false);
			text.setPosition(50, 50, false);
			text.setBackgroundColor(TEXT);
			comp.addComponent(text);
		}
		this.addComponent(mStack);
		
		initialiseChosenComponent();
	}
	
	private void initialiseChosenComponent() {
		GraphicComponent chosenComponent = new GraphicComponent();
		chosenComponent.setSize(100, 100, false);
		chosenComponent.setBackgroundColor(new Color(255, 255, 255));
		chosenComponent.setComponentListener(new ComponentListener() {
			@Override
			public void onClicked(int x, int y) {
				setOpen(!mOpen);
			}
		});
		addComponent(chosenComponent);

		mCurrent = new TextComponent();
		mCurrent.setText(getChosenItem().toString(), 20);
		mCurrent.setSize(100, 100, false);
		mCurrent.setPosition(50, 50, false);
		mCurrent.setBackgroundColor(TEXT);
		chosenComponent.addComponent(mCurrent);
		
		mArrow = new ImageComponent(UP_ARROW_IMAGE);
		mArrow.setOrigin(MIDDLE_RIGHT);
		mArrow.setSize(20, 20);
		mArrow.setPosition(-3, 50, false);
		mArrow.setBorder(SCREEN_RIGHT, SCREEN_TOP);
		chosenComponent.addComponent(mArrow);
	}

	/**
	 * Set the index of the chosen item in the drop down list.
	 * 
	 * @param index - index of item in list
	 */
	public void setChosenItem(int index) {
		mChosen = Math.max(0, Math.min(mItems.size(), index));
		mCurrent.setText(getChosenItem().toString());
	}
	
	/**
	 * Set the index of the chosen item in the drop down list.
	 * 
	 * @param index - index of item in list
	 */
	public void setChosenItem(String item) {
		int index = mItems.indexOf(item);
		if(index < 0) return;
		
		mChosen = Math.max(0, Math.min(mItems.size(), index));
		mCurrent.setText(getChosenItem().toString());
	}
	
	/**
	 * Returns the item chsoen from the drop down list.
	 * 
	 * @return the item chosen
	 */
	public T getChosenItem() {
		return mItems.get(mChosen);
	}
	
	/**
	 * Set whether the drop down list is open or closed.
	 * 
	 * @param open - true for open, false for closed
	 */
	public void setOpen(boolean open) {
		mOpen = open;
		if(mOpen) {
			mArrow.setSize(20, -20);
		}
		else {
			mArrow.setSize(20, 20);
		}
	}
	
	@Override
	public void update() {
		if(!mOpen) return;
		
		int mx = ApplicationFrame.getInput().getMouseX();
		int my = ApplicationFrame.getInput().getMouseY();
		for(int i = 0; i < mStack.mComponents.size(); i++) {
			GraphicComponent comp = mStack.mComponents.get(i);
			if(mx >= comp.getAbsoluteX() && my >= comp.getAbsoluteY()
					&& mx < comp.getAbsoluteX() + comp.getWidth() && my < comp.getAbsoluteY() + comp.getHeight()) {
				comp.setBackgroundColor(CHOSEN);
				if(ApplicationFrame.getInput().getClicked()) {
					this.setChosenItem(i);
					this.setOpen(false);
				}
			} else {
				comp.setBackgroundColor(BACKGROUND);
			}
		}
	}
	
	@Override
	public void paint(Graphics2D g) {
		g.setColor(getBackgroundColor());
		if(!mOpen) {
			g.fillRect(-1, -1, getWidth() + 2, getHeight() + 2);
			g.setClip(0, 0, getWidth(), getHeight());
		} else {
			g.fillRect(-1, -1, getWidth() + 2, getHeight() + mStack.getHeight() + 2);
		}
	}
}
