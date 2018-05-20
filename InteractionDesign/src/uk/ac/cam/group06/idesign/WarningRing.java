package uk.ac.cam.group06.idesign;

import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

import uk.ac.cam.relf2.idesign.components.ComponentListener;
import uk.ac.cam.relf2.idesign.components.ImageComponent;
import uk.ac.cam.relf2.idesign.components.Utils;

public class WarningRing extends ImageComponent implements ComponentListener {
	
	private static Image red_ring = Utils.loadImage("/Red warning.png"), 
			yellow_ring = Utils.loadImage("/Yellow warning.png"), 
			green_ring = Utils.loadImage("/Green warning.png");
	private int mLevel = 0;

	public WarningRing() {
		super(null);

		setImage(green_ring);
		
		this.setComponentListener(this);
	}

	public void setWarningLevel(int lvl) {
		mLevel = lvl;
		if(mLevel == 0)
			setImage(green_ring);
		else if(mLevel == 1)
			setImage(yellow_ring);
		else 
			setImage(red_ring);
	}

	@Override
	public void onClicked(int x, int y) {
		// TODO Auto-generated method stub
		
	}
}
