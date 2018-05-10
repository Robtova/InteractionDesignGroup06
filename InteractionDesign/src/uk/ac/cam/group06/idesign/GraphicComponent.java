package uk.ac.cam.relf2.idesign.components;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class GraphicComponent {
	
	public static final Color CLEAR = new Color(0, 0, 0, 0);
	private Color mBackgroundColor = CLEAR;
	
	protected List<GraphicComponent> mComponents = new CopyOnWriteArrayList<GraphicComponent>();
	
	private boolean mWidthPixels = true, mHeightPixels = true;
	private float mWidth = 0, mHeight = 0;
	private int mWidthAbs = 0, mHeightAbs = 0;
	
	private boolean mXPixels = true, mYPixels = true;
	private float mPosX = 0, mPosY = 0;
	private int mPosXAbs = 0, mPosYAbs = 0;
	private double mRotation = 0;
	
	private GraphicComponent mParent = null;
	
	private ComponentListener mListener;
	
	private boolean mVisible = true;
	private boolean mClickable = true;
	
	public static final int SCREEN_TOP = 0, SCREEN_BOTTOM = 1, SCREEN_LEFT = 0, SCREEN_RIGHT = 1;
	private int mXBorder = SCREEN_LEFT, mYBorder = SCREEN_TOP;
	
	public static final int TOP_LEFT = 0, TOP_CENTRE = 1, TOP_RIGHT = 2,
			MIDDLE_LEFT = 3, MIDDLE_CENTRE = 4, MIDDLE_RIGHT = 5,
			BOTTOM_LEFT = 6, BOTTOM_CENTRE = 7, BOTTOM_RIGHT = 8;

	private int mOrigin = TOP_LEFT;
	
	/**
	 * Set where the origin of the component should be.
	 * Use the static values TOP_LEFT, TOP_CENTRE, TOP_RIGHT, 
	 * MIDDLE_LEFT, MIDDLE_CENTRE, MIDDLE_RIGHT, 
	 * BOTTOM_LEFT, BOTTOM_CENTRE and BOTTOM_RIGHT.
	 * 
	 * @param pos - the origin of the component
	 */
	public void setOrigin(int pos) {
		pos %= 9;
		this.mOrigin = pos;
	}
	
	/**
	 * Set the borders the component is to be drawn against.
	 * Use static values SCREEN_TOP, SCREEN_BOTTOM, SCREEN_LEFT, SCREEN_RIGHT.
	 * 
	 * @param xborder - the x-border (SCREEN_LEFT or SCREEN_RIGHT)
	 * @param yborder - the y-border (SCREEN_TOP or SCREEN_BOTTOM)
	 */
	public void setBorder(int xborder, int yborder) {
		xborder %= 2;
		yborder %= 2;
		
		mXBorder = xborder;
		mYBorder = yborder;
	}

	/**
	 * Set whether a component can be clicked on using the mouse.
	 * 
	 * @param clickable - whether the component can be interacted with using mouse clicks.
	 */
	public void setClickable(boolean clickable) {
		this.mClickable = clickable;
	}
	
	/**
	 * Returns whether the component can be interacted with using mouse clicks.
	 * 
	 * @return true if the component is clickable, false otherwise
	 */
	public boolean isClickable() {
		return this.mClickable;
	}
	
	/**
	 * Set whether a component is to be drawn in subsequent rendering calls.
	 * 
	 * @param visible - whether the component is to be drawn
	 */
	public void setVisible(boolean visible) {
		this.mVisible = visible;
	}
	
	/**
	 * Returns whether the component should be drawn.
	 * 
	 * @return true if the component is visible, false otherwise
	 */
	public boolean isVisible() {
		return this.mVisible;
	}
	
	/**
	 * Used to set the component listener of the component, use when a component is clicked with the mouse.
	 * 
	 * @param listener - the ComponentListener to be used
	 */
	public void setComponentListener(ComponentListener listener) {
		this.mListener = listener;
	}
	
	/**
	 * Called when this component is added to another.
	 * 
	 * @param parent - the parent component to which it is added
	 */
	protected void addedToComponent(GraphicComponent parent) {
		if(this.mParent != null) throw new GraphicComponentException("A component cannot have more than one parent.");
		this.mParent = parent;
		resize();
	}
	
	/**
	 * Called when a component is removed from another component.
	 * 
	 * @param parent - the parent component from which it is removed
	 */
	protected void removedFromComponent(GraphicComponent parent) {
		this.mParent = null;
	}
	
	/**
	 * Make a component the child of this one.
	 * 
	 * @param child - the component to add
	 */
	public void addComponent(GraphicComponent child) {
		if(child == this) throw new GraphicComponentException("A component cannot be a child of itself.");
		if(mComponents.contains(child)) return;
		child.addedToComponent(this);
		this.mComponents.add(child);
	}
	
	/**
	 * Remove a child component from this component's children.
	 * 
	 * @param child - the child to be removed
	 */
	public void removeComponent(GraphicComponent child) {
		boolean in = this.mComponents.remove(child);
		if(in) child.removedFromComponent(this);
	}
	
	/**
	 * Set the position of this component relative to its borders. This can be done as absolute positions or as percentages.
	 * 
	 * @param x - the x position
	 * @param y - the y position
	 * @param inPixels - whether position is in pixels, if false then the position is a percentage of the parent's size
	 */
	public void setPosition(float x, float y, boolean inPixels) {
		setPosition(x, inPixels, y, inPixels);
	}
	
	/**
	 * Set the position of this component relative to its borders. This can be done as absolute positions or as percentages.
	 * 
	 * @param x - the x position
	 * @param y - the y position
	 * @param xPixels - whether x position is in pixels, if false then the position is a percentage of the parent's size
	 * @param yPixels - whether y position is in pixels, if false then the position is a percentage of the parent's size
	 */
	public void setPosition(float x, boolean xPixels, float y, boolean yPixels) {
		setX(x, xPixels);
		setY(y, yPixels);
	}
	
	/**
	 * Set the position of this component relative to its borders. This can be done as an absolute position in pixels.
	 * 
	 * @param x - the x position
	 * @param y - the y position
	 */
	public void setPosition(float x, float y) {
		setPosition(x, y, true);
	}
	
	/**
	 * Set the x position of this component relative to its x-border. This can be done as an absolute position or as a percentage.
	 * 
	 * @param x - the x position
	 * @param inPixels - whether position is in pixels, if false then the position is a percentage of the parent's size
	 */
	public void setX(float x, boolean inPixels) {
		this.mPosX = x;
		this.mXPixels = inPixels;

		if(mParent == null || mXPixels) {
			mPosXAbs = (int) mPosX;
		} else {
			mPosXAbs = (int) ((double) (this.mPosX / 100.0 * mParent.getWidth()));
		}
	}
	
	/**
	 * Set the y position of this component relative to its y-border. This can be done as an absolute position or as a percentage.
	 * 
	 * @param y - the y position
	 * @param inPixels - whether position is in pixels, if false then the position is a percentage of the parent's size
	 */
	public void setY(float y, boolean inPixels) {
		this.mPosY = y;
		this.mYPixels = inPixels;

		if(mParent == null || mYPixels) {
			mPosYAbs = (int) mPosY;
		} else {
			mPosYAbs = (int) ((double) (this.mPosY / 100.0 * mParent.getHeight()));
		}
	}
	
	/**
	 * Set the size (width and height) of the component. This can be done in pixels or percentage of the parent's size.
	 * 
	 * @param width - the new width
	 * @param height - the new height
	 * @param wPixels - whether width is in pixels, if false then it is a percentage of the parent's size
	 * @param hPixels - whether height is in pixels, if false then it is a percentage of the parent's size
	 */
	public void setSize(float width, boolean wPixels, float height, boolean hPixels) {
		this.mWidth = width;
		this.mHeight = height;
		this.mWidthPixels = wPixels;
		this.mHeightPixels = hPixels;
		
		resize();
	}
	
	/**
	 * Set the size (width and height) of the component. This can be done in pixels or percentage of the parent's size.
	 * 
	 * @param width - the new width
	 * @param height - the new height
	 * @param inPixels - whether dimensions are in pixels, if false then they are percentage of the parent's size.
	 */
	public void setSize(float width, float height, boolean inPixels) {
		setSize(width, inPixels, height, inPixels);
	}
	
	/**
	 * Set the size (width and height) of the component. This is done in pixels.
	 * 
	 * @param width - the new width
	 * @param height - the new height
	 */
	public void setSize(float width, float height) {
		setSize(width, height, true);
	}
	
	/**
	 * Set the width of the component. This can be done in pixels or percentage of the parent's size.
	 * 
	 * @param width - the new width
	 * @param wPixels - whether width is in pixels, if false then it is a percentage of the parent's size
	 */
	public void setWidth(float width, boolean wPixels) {
		this.mWidth = width;
		this.mWidthPixels = wPixels;
		
		resize();
	}
	
	/**
	 * Set the height of the component. This can be done in pixels or percentage of the parent's size.
	 * 
	 * @param height - the new height
	 * @param hPixels - whether height is in pixels, if false then it is a percentage of the parent's size
	 */
	public void setHeight(float height, boolean hPixels) {
		this.mHeight = height;
		this.mHeightPixels = hPixels;
		
		resize();
	}
	
	/**
	 * Called when the size of this component or its parent's is changed. 
	 * Computes the absolute sizes and positions of the component and of its children.
	 */
	private final void resize() {
		if(mParent == null || mWidthPixels) {
			mWidthAbs = (int) mWidth;
		} else {
			mWidthAbs = (int) ((double) (this.mWidth / 100.0 * mParent.getWidth()));
		}
		
		if(mParent == null || mHeightPixels) {
			mHeightAbs = (int) mHeight;
		} else {
			mHeightAbs = (int) ((double) (this.mHeight / 100.0 * mParent.getHeight()));
		}
		
		if(mParent == null || mXPixels) {
			mPosXAbs = (int) mPosX;
		} else {
			mPosXAbs = (int) ((double) (this.mPosX / 100.0 * mParent.getWidth()));
		}
		
		if(mParent == null || mYPixels) {
			mPosYAbs = (int) mPosY;
		} else {
			mPosYAbs = (int) ((double) (this.mPosY / 100.0 * mParent.getHeight()));
		}
		
		Iterator<GraphicComponent> iterator = mComponents.iterator();
		GraphicComponent comp;
		while(iterator.hasNext()) {
			comp = iterator.next();
			comp.resize();
		}
	}
	
	/**
	 * Returns the absolute width of the component in pixels.
	 * 
	 * @return the width of the component in pixels
	 */
	public int getWidth() {
		return mWidthAbs;
	}
	
	/**
	 * Returns the absolute height of the component in pixels.
	 * 
	 * @return the height of the component in pixels
	 */
	public int getHeight() {
		return mHeightAbs;
	}
	
	/**
	 * Returns the x position of the component relative to its border, in pixels.
	 * 
	 * @return the x position of the component in pixels
	 */
	public int getX() {
		return this.mPosXAbs;
	}
	
	/**
	 * Returns the y position of the component relative to its border, in pixels.
	 * 
	 * @return the y position of the component in pixels.
	 */
	public int getY() {
		return this.mPosYAbs;
	}
	
	/**
	* Set the rotation of the component when drawn.
	* 
	* @param nRot - set the rotation to this value (in Radians)
	*/
	public void setRotation(double nRot) {
		mRotation = nRot;
	}
	
	/**
	* Returns the rotation of the component when drawn.
	* 
	* @return the rotation of this graphic
	*/
	public double getRotation() {
		return mRotation;
	}

	/**
	 * Set the background color of the component.
	 * 
	 * @param col - the background color
	 */
	public void setBackgroundColor(Color col) {
		this.mBackgroundColor = col;
	}
	
	/**
	 * Returns the background color of the component. 
	 * 
	 * @return the background color
	 */
	public Color getBackgroundColor() {
		return this.mBackgroundColor;
	}
	
	/**
	 * Draws the component and its children, applying the rotation and translate of the object.
	 * 
	 * @param g1 - the graphics to be used
	 */
	public void repaint(Graphics2D g1) {
		if(!isVisible()) return;
		
		Graphics2D g = (Graphics2D) g1.create();
		if(mParent != null) g.translate(mParent.getWidth() * mXBorder, mParent.getHeight() * mYBorder);
		g.translate(this.getX(), this.getY());
		g.translate(-(this.getWidth() / 2 * (mOrigin % 3)), -(this.getHeight() / 2 * (mOrigin / 3)));
		g.rotate(mRotation, this.getWidth()/2, this.getHeight()/2);

		this.paint(g);
		Iterator<GraphicComponent> iterator = mComponents.iterator();
		GraphicComponent comp;
		while(iterator.hasNext()) {
			comp = iterator.next();
			comp.repaint(g);
		}
		g.dispose();
	}
	
	/**
	 * Draws the component.
	 * 
	 * @param g - the graphics to be used
	 */
	public void paint(Graphics2D g) {
		g.setColor(getBackgroundColor());
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
	}
	
	/**
	 * Checks whether a mouse click could have been on the component or its children.
	 * 
	 * @param x - the x coordinate of the mouse
	 * @param y - the y coordinate of the mouse
	 * @param clicked - whether the mouse was clicked
	 */
	protected void checkMouse(int x, int y, boolean clicked) {
		if(!isVisible() || !isClickable()) return;
		
		if(inBounds(x, y)) {

			if(clicked && mListener != null) {
				mListener.onClicked(x, y);
			}

			Iterator<GraphicComponent> iterator = mComponents.iterator();
			GraphicComponent comp;
			while(iterator.hasNext()) {
				comp = iterator.next();
				comp.checkMouse(x-this.mPosXAbs, y-this.mPosYAbs, clicked);
			}
		} 
	}
	
	/**
	 * Returns whether the coordinates are within the bounds of the components.
	 * 
	 * @param x - the x coordinate
	 * @param y - the y coordinate
	 * @return true if the coordinates are within the bounds
	 */
	public boolean inBounds(int x, int y) {
		x = x + (this.getWidth() / 2 * (mOrigin % 3));
		y = y + (this.getHeight() / 2 * (mOrigin / 3));
		
		if(mParent != null) {
			x -= mParent.getWidth() * this.mXBorder;
			y -= mParent.getHeight() * this.mYBorder;
		}
		
		return x >= this.mPosXAbs && x <= this.mPosXAbs+this.mWidthAbs
				&& y >= this.mPosYAbs && y <= this.mPosYAbs+this.mHeightAbs;
	}
}
