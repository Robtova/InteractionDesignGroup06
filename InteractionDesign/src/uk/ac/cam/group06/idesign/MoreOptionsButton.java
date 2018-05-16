package uk.ac.cam.group06.idesign;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.util.HashMap;
import java.util.Map;

import uk.ac.cam.relf2.idesign.components.ComponentListener;
import uk.ac.cam.relf2.idesign.components.GraphicComponent;
import uk.ac.cam.relf2.idesign.components.ImageComponent;
import uk.ac.cam.relf2.idesign.components.Utils;

public class MoreOptionsButton extends GraphicComponent implements ComponentListener {
	
	private ImageComponent mDots;
	private boolean mOpen = false;
	private float mExtension = 0;
	
	private static Image SETTINGS = Utils.loadImage("/settings_button.png"), 
			SEARCH = Utils.loadImage("/search_button.png"),
			TRIPLE_DOTS = Utils.loadImage("/triple_dot.png");
	
	private Map<ImageComponent, GraphicComponent> mButtons = new HashMap<ImageComponent, GraphicComponent>();
	
	public MoreOptionsButton() {
		mDots = new ImageComponent(TRIPLE_DOTS);
		mDots.setSize(100, 100, false);
		this.addComponent(mDots);
		
		this.setOrigin(TOP_RIGHT);
		
		this.setBackgroundColor(new Color(230, 230, 230));
		this.setComponentListener(this);
		
		addButton(SETTINGS, new SettingsScreen());
		addButton(SEARCH, new SearchScreen());
	}
	
	@Override
	public void paint(Graphics2D g) {
		if(!mOpen) return;
		
		int height = (int) (getHeight() * (106d/128d));
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(this.getBackgroundColor());
		g.fillRect(-(int) (mExtension - getWidth() * 0.5), (getHeight() - height) / 2, (int) mExtension, height);
		g.fillOval((int) -mExtension, (getHeight() - height) / 2, height, height);
	}
	
	@Override
	public void update() {
		if(!mOpen) {
			mExtension = 0;
			mDots.setRotation(0);
            for(ImageComponent img : mButtons.keySet()) 
            	img.setVisible(false);
			return;
		}
		
		int height = (int) (getHeight() * (106d/128d));
		int maxExtent = height * mButtons.size();
		mExtension = Math.min(mExtension + 40, maxExtent);
		mDots.setRotation(mExtension*Math.PI/(2*maxExtent));
		
		int off = 0;
		for(ImageComponent button : mButtons.keySet()) {
			button.setPosition(-mExtension + height * off, (getHeight() - height) / 2);
			if(mExtension >= height *  off) button.setVisible(true);
			off++;
        }
	}

	/**
	 * Add a button to be shown when more options is clicked.
	 * 
	 * @param image - the image to display as the button
	 * @param toOpen - the screen to open when the button is pressed
	 */
	public void addButton(Image image, GraphicComponent toOpen) {
		ImageComponent button = new ImageComponent(image);
		this.mButtons.put(button, toOpen);
		button.setSize(106f/128f * 100f, 106f/128f * 100f, false);
		button.setComponentListener(new ComponentListener() {
            @Override
            public void onClicked(int x, int y) {
            	if(mButtons.get(button) != null) {
            		ApplicationFrame.addComponent(mButtons.get(button));
            	}
            }
        });
		this.addComponent(button);
	}

	@Override
	public void onClicked(int x, int y) {
		mOpen = !mOpen;
	}
}
