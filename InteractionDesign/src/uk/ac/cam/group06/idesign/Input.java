package uk.ac.cam.group06.idesign;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JPanel;

public class Input implements MouseListener, MouseMotionListener, MouseWheelListener {
	
	private int mMouseX, mMouseY;
	private boolean mMousePressed;
	private int mScroll, mScrollShow;
	
	public Input(JPanel panel) {
		panel.addMouseListener(this);
		panel.addMouseMotionListener(this);
		panel.addMouseWheelListener(this);
	}
	
	public int getMouseX() {
		return mMouseX;
	}
	
	public int getMouseY() {
		return mMouseY;
	}
	
	public boolean getMousePressed() {
		return mMousePressed;
	}
	
	public int getScroll() {
		return mScrollShow;
	}
	
	public void update() {
		mScrollShow = mScroll;
		mScroll = 0;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		mMousePressed = true;
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
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		mMousePressed = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mMousePressed = false;
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		mScroll = e.getWheelRotation();
	}
}
