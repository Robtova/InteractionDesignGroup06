package uk.ac.cam.group06.idesign;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;

import uk.ac.cam.relf2.idesign.components.ComponentListener;
import uk.ac.cam.relf2.idesign.components.GraphicComponent;
import uk.ac.cam.relf2.idesign.components.ImageComponent;
import uk.ac.cam.relf2.idesign.components.Utils;

public class MoreOptionsButton extends GraphicComponent implements ComponentListener {
	
	private ImageComponent mDots;
	private boolean mOpen = false;
	private float mExtension = 0;
	
	private Image mSettings, mSearch;
	
	public MoreOptionsButton() {
		mDots = new ImageComponent(Utils.loadImage("/triple_dot.png"));
		mDots.setSize(100, 100, false);
		this.addComponent(mDots);
		
		this.setBackgroundColor(new Color(230, 230, 230));
		this.setComponentListener(this);
		
		mSettings = Utils.loadImage("/settings_button.png");
		mSearch = Utils.loadImage("/search_button.png");
	}
	
	@Override
	public void paint(Graphics2D g) {
		if(!mOpen) {
			mExtension = 0;
			return;
		}
		
		int height = (int) (getHeight() * (106d/128d));
		mExtension = Math.min(mExtension + 40, height * 2);
		int ext = (int) mExtension;
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(this.getBackgroundColor());
		g.fillRect(-(int) (mExtension - getWidth() * 0.5), (getHeight() - height) / 2, ext, height);
		g.fillOval(-ext, (getHeight() - height) / 2, height, height);
		
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		g.drawImage(mSettings, -ext, (getHeight() - height) / 2, height, height, null);
		if(ext >= height) g.drawImage(mSearch, -ext + height, (getHeight() - height) / 2, height, height, null);
	}
	
	@Override
	public void onClicked(int x, int y) {
		mOpen = !mOpen;
	}
}
