package uk.ac.cam.relf2.idesign.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import uk.ac.cam.group06.idesign.ApplicationFrame;

public class DropDownComponent<T> extends GraphicComponent {
	
	private StackComponent mStack;
	private TextComponent mCurrent;
	private ImageComponent mArrow;
	private GraphicComponent mScrollBar;
	private GraphicComponent mScrollPane;
	
	private final List<T> mItems = new ArrayList<T>();
	private int mChosen = 0;
	
	private static final Color BACKGROUND = new Color(245, 245, 245);
	private static final Color CHOSEN = new Color(230, 230, 230);
	private static final Color TEXT = new Color(180, 180, 180);
	
	private static Image UP_ARROW_IMAGE = Utils.loadImage("/up_arrow.png");
	
	private boolean mOpen = false;
	
	private int mMaxHeight = 195;
	
	private int mScroll = 0;
	
	public DropDownComponent(List<T> items) {
		this.mItems.addAll(items);
		
		this.setBackgroundColor(TEXT);
		
		mScrollPane = new GraphicComponent();
		mScrollPane.setPosition(0, 100, false);
		mScrollPane.setSize(100, 100, false);
		this.addComponent(mScrollPane);
		
		mStack = new StackComponent();
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
		mScrollPane.addComponent(mStack);
		
		mScrollBar = new CircleComponent() {
			private boolean mPressed = false;
			private int mouseY = 0;
			@Override
			public void update() {
				Input input = ApplicationFrame.getInput();
				if(input.getMousePressed()) {
					int mx = input.getMouseX();
					int x = getAbsoluteX();
					int my = input.getMouseY();
					int y = getAbsoluteY();
					
					if(mPressed) {
						int dy = my - mouseY;
						mouseY = my;

						mScroll += (int) (dy / (float) (mMaxHeight - 40) * (float) (mMaxHeight-(mStack.getHeight())));
						mScroll = Math.max(mMaxHeight-(mStack.getHeight()), mScroll);
						mScroll = Math.min(0, mScroll);
					}
					if(mx >= x && my >= y && mx < x + getWidth() && my < y + getHeight()) {
						mouseY = my;
						mPressed = true;
					} 
				} else {
					mPressed = false;
				}
				
				int scrollbar = (int) ((float) (mScroll) / (float) (mMaxHeight-(mStack.getHeight())) * (float) (mMaxHeight - 40));
				setPosition(-3, false, 10 + scrollbar, true);
			}
		};
		mScrollBar.setBackgroundColor(new Color(200, 200, 200, 180));
		mScrollBar.setSize(20, 20);
		mScrollBar.setPosition(-3, false, 10, true);
		mScrollBar.setOrigin(TOP_RIGHT);
		mScrollBar.setBorder(SCREEN_RIGHT, SCREEN_TOP);
		mScrollPane.addComponent(mScrollBar);
		
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
	public void setChosenItem(T item) {
		int index = mItems.indexOf(item);
		if(index < 0) return;
		
		mChosen = Math.max(0, Math.min(mItems.size(), index));
		mCurrent.setText(getChosenItem().toString());
	}
	
	/**
	 * Returns the item chosen from the drop down list.
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
	
	/**
	 * Set the maximum height that the drop down box can be.
	 * 
	 * @param maxHeight - the maximum height
	 */
	public void setMaxHeight(int maxHeight) {
		mMaxHeight = maxHeight;
	}
	
	public void setFont(Font font) {
		for(int i = 0; i < mStack.mComponents.size(); i++) {
			if(!(mStack.mComponents.get(i).mComponents.get(0) instanceof TextComponent)) continue;
			TextComponent text = (TextComponent) mStack.mComponents.get(i).mComponents.get(0);
			text.setFont(font);
		}
		mCurrent.setFont(font);
	}
	
	@Override
	public void update() {
		mStack.setY(mScroll, true);

		if(!mOpen) {
			mScroll = 0;
			mScrollPane.setVisible(false);
			return;
		}
		mScrollPane.setVisible(true);
		
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
		
		if(ApplicationFrame.getInput().getClicked()) {
			if(mx < this.getAbsoluteX() || my < this.getAbsoluteY() 
					|| mx > this.getAbsoluteX() + getWidth() || my > this.getAbsoluteY() + getHeight()) {
				this.setOpen(false);
			}
		}
		
		
		if(mStack.getHeight() > mMaxHeight) {
			mScroll -= ApplicationFrame.getInput().getScroll() * 40;
			mScroll = Math.max(mMaxHeight-(mStack.getHeight()), mScroll);
			mScroll = Math.min(0, mScroll);
			mScrollBar.setVisible(true);
		} else {
			mScrollBar.setVisible(false);
			mScroll = 0;
		}
	}
	
	@Override
	public void paint(Graphics2D g) {
		g.setColor(getBackgroundColor());
		if(!mOpen) {
			g.fillRect(-1, -1, getWidth() + 2, getHeight() + 2);
			g.setClip(0, 0, getWidth(), getHeight());
		} else {
			g.fillRect(-1, -1, getWidth() + 2, getHeight() + mMaxHeight + 2);
			g.setClip(0, 0, getWidth(), getHeight() + mMaxHeight);
		}
	}
}
