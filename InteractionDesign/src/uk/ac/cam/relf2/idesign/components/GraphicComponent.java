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
	
	public GraphicComponent() {
	}
	
	public void setVisible(boolean visible) {
		this.mVisible = visible;
	}
	
	public boolean getVisible() {
		return this.mVisible;
	}
	
	public void setComponentListener(ComponentListener list) {
		this.mListener = list;
	}
	
	protected void addedToComponent(GraphicComponent parent) {
		if(this.mParent != null) throw new GraphicComponentException("A component cannot have more than one parent.");
		this.mParent = parent;
		resize();
	}
	
	protected void removedFromComponent() {
		this.mParent = null;
	}
	
	public void addComponent(GraphicComponent child) {
		if(child == this) throw new GraphicComponentException("A component cannot be a child of itself.");
		if(mComponents.contains(child)) return;
		child.addedToComponent(this);
		this.mComponents.add(child);
	}
	
	public void removeComponent(GraphicComponent child) {
		boolean in = this.mComponents.remove(child);
		if(in) child.removedFromComponent();
	}
	
	/**
	 * 
	 * @param x - the width to set the component to.
	 * @param y - the height to set the component to.
	 * @param inPixels - whether position is in pixels, if false then it is a percentage of the parents size.
	 */
	public void setPosition(float x, float y, boolean inPixels) {
		setPosition(x, inPixels, y, inPixels);
	}
	
	public void setX(float x, boolean inPixels) {
		this.mPosX = x;
		this.mXPixels = inPixels;

		if(mParent == null || mXPixels) {
			mPosXAbs = (int) mPosX;
		} else {
			mPosXAbs = (int) ((double) (this.mPosX / 100.0 * mParent.getWidth()));
		}
	}
	
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
	 * 
	 * @param x - the width to set the component to.
	 * @param y - the height to set the component to.
	 * @param xPixels - whether x position is in pixels, if false then it is a percentage of the parents size.
	 * @param yPixels - whether y position is in pixels, if false then it is a percentage of the parents size.
	 */
	public void setPosition(float x, boolean xPixels, float y, boolean yPixels) {
		setX(x, xPixels);
		setY(y, yPixels);
	}
	
	/**
	 * 
	 * @param x - the width to set the component to, in pixels.
	 * @param y - the height to set the component to, in pixels.
	 */
	public void setPosition(float x, float y) {
		setPosition(x, y, true);
	}
	
	/**
	 * 
	 * @param width - the width to set the component to.
	 * @param height - the height to set the component to.
	 * @param wPixels - whether width is in pixels, if false then they are percentage of the parents size.
	 * @param hPixels - whether height is in pixels, if false then they are percentage of the parents size.
	 */
	public void setSize(float width, boolean wPixels, float height, boolean hPixels) {
		this.mWidth = width;
		this.mHeight = height;
		this.mWidthPixels = wPixels;
		this.mHeightPixels = hPixels;
		
		resize();
	}
	
	public void setWidth(float width, boolean wPixels) {
		this.mWidth = width;
		this.mWidthPixels = wPixels;
		
		resize();
	}
	
	public void setHeight(float height, boolean hPixels) {
		this.mHeight = height;
		this.mHeightPixels = hPixels;
		
		resize();
	}
	
	/**
	 * 
	 * @param width - the width to set the component to.
	 * @param height - the height to set the component to.
	 * @param inPixels - whether dimensions are in pixels, if false then they are percentage of the parents size.
	 */
	public void setSize(float width, float height, boolean inPixels) {
		setSize(width, inPixels, height, inPixels);
	}
	
	/**
	 * 
	 * @param width - the width to set the component to, in pixels.
	 * @param height - the height to set the component to, in pixels.
	 */
	public void setSize(float width, float height) {
		setSize(width, height, true);
	}
	
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
	 * 
	 * @param nRot - set the rotation to this value (in Radians)
	 */
	public void setRotation(double nRot) {
		mRotation = nRot;
	}
	
	/**
	 * 
	 * @return the rotation of this graphic.
	 */
	public double getRotation() {
		return mRotation;
	}
	
	/**
	 * 
	 * @return the width of the component in pixels.
	 */
	public int getWidth() {
		return mWidthAbs;
	}
	
	/**
	 * 
	 * @return the height of the component in pixels.
	 */
	public int getHeight() {
		return mHeightAbs;
	}
	
	/**
	 * 
	 * @return the x position of the component in pixels.
	 */
	public int getX() {
		return this.mPosXAbs;
	}
	
	/**
	 * 
	 * @return the y position of the component in pixels.
	 */
	public int getY() {
		return this.mPosYAbs;
	}

	public void setBackgroundColor(Color col) {
		this.mBackgroundColor = col;
	}
	
	public Color getBackgroundColor() {
		return this.mBackgroundColor;
	}
	
	public void repaint(Graphics2D g1) {
		if(!getVisible()) return;
		
		Graphics2D g = (Graphics2D) g1.create();
		g.translate(this.getX(), this.getY());
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
	
	public void paint(Graphics2D g) {
		g.setColor(getBackgroundColor());
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
	}
	
	protected void checkMouse(int x, int y, boolean clicked) {
		if(!getVisible()) return;
		
		if(x >= this.mPosXAbs && x <= this.mPosXAbs+this.mWidthAbs
				&& y >= this.mPosYAbs && y <= this.mPosYAbs+this.mHeightAbs) {
//			if(!mMouseHover) onMouseEnter(x, y);
//			
//			mMouseHover = true;
			
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
//		else if(mMouseHover) {
//			mMouseHover = false;
//			onMouseExit();
//			
//			Iterator<GraphicComponent> iterator = mComponents.iterator();
//			GraphicComponent comp;
//			while(iterator.hasNext()) {
//				comp = iterator.next();
//				comp.checkMouse(x-this.mPosXAbs, y-this.mPosYAbs, false);
//			}
//		}
	}
}
