package uk.ac.cam.relf2.idesign.components;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GraphicPanel extends GraphicComponent implements MouseListener, ComponentListener {

	private JPanel mPanel;
	
	public final static double FRAME_LENGTH = 1000000000.0 / 60.0;
	
	private BufferedImage image;
	private boolean resized = false;
	
	PanelListener mListener = null;
	
	public GraphicPanel(PanelListener list) {
		mListener = list;
	}
	
	public void initialisePanel() {
		mPanel = new JPanel();
		mPanel.addMouseListener(this);
		mPanel.addComponentListener(this);
	}
	
	@Override
	protected void addedToComponent(GraphicComponent parent) {
		throw new GraphicComponentException("A graphic panel cannot be added to another component.");
	}
	
	public void addToFrame(JFrame frame) {
		initialisePanel();
		
		Dimension d = frame.getSize();
		
		frame.add(mPanel, BorderLayout.CENTER);
		this.setPosition(0, 0, true);
		this.setSize(d.width, d.height, true);
		resized = true;
		frame.pack();
		frame.setSize(d);
		
		run();
	}
	
	public void run() {
		mListener.initialise(this);
		
		/* Loop */
		
		long lastFrame = System.nanoTime();
		long lastPrint = lastFrame;
		long lag = 0;
		long lastUpdate = lastFrame;
		int ticks = 0;
		int frames = 0;
		
		while(true) {
			long currentFrame = System.nanoTime();
			long elapsed = currentFrame - lastFrame;
			lastFrame = currentFrame;
			lag += elapsed;
			
			boolean shouldRender = false;

			while (lag >= FRAME_LENGTH) {
				update();
				lastUpdate = currentFrame;
				ticks++;
			    lag -= FRAME_LENGTH;
			    shouldRender = true;
			}
			
			if(shouldRender) {
				render();
				frames++;
			}
			
			if(System.nanoTime() - lastPrint >= 1000000000) {
				//System.out.println("ticks: " + ticks + ", frame: " + frames);
				ticks = 0;
				frames = 0;
				lastPrint = System.nanoTime();
			}
		}
	}
	
	public void render() {
		if(image == null || resized) {
			image = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
		}
		
		Graphics2D g = (Graphics2D) image.getGraphics();
		
		repaint(g);

		Graphics g2 = mPanel.getGraphics();
		g2.drawImage(image, 0, 0, mPanel.getWidth(), mPanel.getHeight(), null);
		g2.dispose();
		g.dispose();
	}
	
	@Override
	public void paint(Graphics2D g) {
		g.setColor(this.getBackgroundColor());
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		Iterator<GraphicComponent> iterator = mComponents.iterator();
		GraphicComponent comp;

		while(iterator.hasNext()) {
			comp = iterator.next();
			comp.checkMouse(e.getX(), e.getY(), true);
		}
	}

	@Override
	public void componentResized(ComponentEvent e) {
		this.setPosition(0, 0, true);
		this.setSize(e.getComponent().getWidth(), e.getComponent().getHeight(), true);
		
		resized = true;
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}

	@Override
	public void componentHidden(ComponentEvent e) {
	}

	@Override
	public void componentMoved(ComponentEvent e) {
	}

	@Override
	public void componentShown(ComponentEvent e) {
	}
}
