package uk.ac.cam.relf2.idesign.components;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GraphicPanel extends GraphicComponent implements ComponentListener {

	private JPanel mPanel;
	
	public final static double FRAME_LENGTH = 1000000000.0 / 60.0;
	
	private BufferedImage image;
	private boolean resized = false;
	
	PanelListener mListener = null;
	
	private Input mInput;
	
	/**
	 * The hybrid JPanel and GraphicComponent so graphics can be used with swing.
	 * @param list
	 */
	public GraphicPanel(PanelListener list) {
		mListener = list;
		
		initialisePanel();
	}
	
	/**
	 * Returns the mouse input of this panel.
	 * 
	 * @return the Input object
	 */
	public Input getInput() {
		return mInput;
	}
	
	public void initialisePanel() {
		mPanel = new JPanel();
		mPanel.addComponentListener(this);
		
		this.mInput = new Input(mPanel);
	}
	
	@Override
	protected void addedToComponent(GraphicComponent parent) {
		throw new GraphicComponentException("A graphic panel cannot be added to another component.");
	}
	
	/**
	 * Add this panel to the JFrame and start rendering.
	 * 
	 * @param frame - the JFrame it is added to
	 */
	public void addToFrame(JFrame frame) {
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
		int ticks = 0;
		int frames = 0;
		
		while(true) {
			long currentFrame = System.nanoTime();
			long elapsed = currentFrame - lastFrame;
			lastFrame = currentFrame;
			lag += elapsed;
			
			boolean render = false;

			while (lag >= FRAME_LENGTH) {
				mInput.update();
				initiateUpdate();
				
				ticks++;
			    lag -= FRAME_LENGTH;
			    render = true;
			}
			
			if(render) {
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
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
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
	public void update() {
		if(!mInput.getClicked()) return; 
		
		Iterator<GraphicComponent> iterator = mComponents.iterator();
		GraphicComponent comp;

		while(iterator.hasNext()) {
			comp = iterator.next();
			comp.checkMouse(mInput.getMouseX(), mInput.getMouseY(), true);
		}
	}

	@Override
	public void componentResized(ComponentEvent e) {
		this.setPosition(0, 0, true);
		this.setSize(e.getComponent().getWidth(), e.getComponent().getHeight(), true);
		
		resized = true;
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
