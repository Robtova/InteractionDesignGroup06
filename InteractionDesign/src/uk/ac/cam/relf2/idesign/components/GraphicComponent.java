package uk.ac.cam.relf2.idesign.components;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GraphicComponent {
	
	public static final Color CLEAR = new Color(0, 0, 0, 0);
	private Color mBackgroundColor = CLEAR;
	protected List<GraphicComponent> mComponents = new ArrayList<GraphicComponent>();
	
	private boolean mSizeInPixels = true;
	private float mWidth = 0, mHeight = 0;
	private int mWidthAbs = 0, mHeightAbs = 0;
	
	private boolean mPosInPixels = true;
	private float mPosX = 0, mPosY = 0;
	private int mPosXAbs = 0, mPosYAbs = 0;
	
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
		child.addedToComponent(this);
		this.mComponents.add(child);
	}
	
	public void removeComponent(GraphicComponent child) {
		boolean in = this.mComponents.remove(child);
		if(in) child.removedFromComponent();
	}
	
	public void update() {
	}
	
	/**
	 * 
	 * @param x - the width to set the component to.
	 * @param y - the height to set the component to.
	 * @param inPixels - whether position is in pixels, if false then it is a percentage of the parents size.
	 */
	public final void setPosition(float x, float y, boolean inPixels) {
		this.mPosX = x;
		this.mPosY = y;
		this.mPosInPixels = inPixels;
		
		if(mParent == null || mPosInPixels) {
			mPosXAbs = (int) mPosX;
			mPosYAbs = (int) mPosY;
		} else if(!mPosInPixels) {
			mPosXAbs = (int) ((double) (this.mPosX / 100.0 * mParent.getWidth()));
			mPosYAbs = (int) ((double) (this.mPosY / 100.0 * mParent.getHeight()));
		}
	}
	
	/**
	 * 
	 * @param x - the width to set the component to, in pixels.
	 * @param y - the height to set the component to, in pixels.
	 */
	public final void setPosition(float x, float y) {
		mPosXAbs = (int) (mPosX = x);
		mPosYAbs = (int) (mPosY = y);
		this.mPosInPixels = true;
	}
	
	/**
	 * 
	 * @param width - the width to set the component to.
	 * @param height - the height to set the component to.
	 * @param inPixels - whether dimensions are in pixels, if false then they are percentage of the parents size.
	 */
	public final void setSize(float width, float height, boolean inPixels) {
		this.mWidth = width;
		this.mHeight = height;
		this.mSizeInPixels = inPixels;
		
		resize();
	}
	
	/**
	 * 
	 * @param width - the width to set the component to, in pixels.
	 * @param height - the height to set the component to, in pixels.
	 */
	public final void setSize(float width, float height) {
		this.mWidth = width;
		this.mHeight = height;
		this.mSizeInPixels = true;
		
		resize();
	}
	
	private final void resize() {
		if(!mSizeInPixels && mParent != null) {
			mWidthAbs = (int) ((double) (this.mWidth / 100.0 * mParent.getWidth()));
			mHeightAbs = (int) ((double) (this.mHeight / 100.0 * mParent.getHeight()));
		} else {
			mWidthAbs = (int) mWidth;
			mHeightAbs = (int) mHeight;
		}
		
		if(!mPosInPixels && mParent != null) {
			mPosXAbs = (int) ((double) (this.mPosX / 100.0 * mParent.getWidth()));
			mPosYAbs = (int) ((double) (this.mPosY / 100.0 * mParent.getHeight()));
		} else {
			mPosXAbs = (int) mPosX;
			mPosYAbs = (int) mPosY;
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
	
	public final void repaint(Graphics2D g1) {
		if(!getVisible()) return;
		
		Graphics2D g = (Graphics2D) g1.create();
		g.translate(this.mPosXAbs, this.mPosYAbs);
		//g.setClip(0, 0, this.mWidthAbs, this.mHeightAbs);
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
