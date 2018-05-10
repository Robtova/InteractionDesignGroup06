package uk.ac.cam.relf2.idesign.components;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JPanel;

public class Input implements MouseListener, MouseMotionListener, MouseWheelListener {
	
	private int mMouseX, mMouseY;
	private boolean mPressed, mPressedShow;
	private int mScroll, mScrollShow;
	
	private boolean mClicked, mClickedShow;
	
	/**
	 * Monitors the mouse inputs of a JPanel.
	 * 
	 * @param panel - the JPanel to monitor
	 */
	public Input(JPanel panel) {
		panel.addMouseListener(this);
		panel.addMouseMotionListener(this);
		panel.addMouseWheelListener(this);
	}
	
	/**
	 * Returns whether the JPanel has been clicked.
	 * @return true if the JPanel has been clicked
	 */
	public boolean getClicked() {
		return mClickedShow;
	}
	
	/**
	 * The x coordinate of the mouse cursor.
	 * 
	 * @return x coordinate of mouse
	 */
	public int getMouseX() {
		return mMouseX;
	}
	
	/**
	 * The y coordinate of the mouse cursor.
	 * 
	 * @return y coordinate of mouse
	 */
	public int getMouseY() {
		return mMouseY;
	}
	
	/**
	 * Returns whether the mouse has been pressed down.
	 * 
	 * @return true if the mouse is pressed down
	 */
	public boolean getMousePressed() {
		return mPressedShow;
	}
	
	/**
	 * Returns the number of clicks the mouse wheel has been scrolled.
	 * @return
	 */
	public int getScroll() {
		return mScrollShow;
	}
	
	protected void update() {
		mScrollShow = mScroll;
		mScroll = 0;
		
		mClickedShow = mClicked;
		mClicked = false;
		
		mPressedShow = mPressed;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		mPressed = true;
		mMouseX = e.getX();
		mMouseY = e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mMouseX = e.getX();
		mMouseY = e.getY();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		mClicked = true;
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		mPressed = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mPressed = false;
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		mScroll = e.getWheelRotation();
	}
}
