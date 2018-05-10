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
	
	public Input(JPanel panel) {
		panel.addMouseListener(this);
		panel.addMouseMotionListener(this);
		panel.addMouseWheelListener(this);
	}
	
	public boolean getClicked() {
		return mClickedShow;
	}
	
	public int getMouseX() {
		return mMouseX;
	}
	
	public int getMouseY() {
		return mMouseY;
	}
	
	public boolean getMousePressed() {
		return mPressed;
	}
	
	public int getScroll() {
		return mScrollShow;
	}
	
	public void update() {
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
